/*
 * Copyright (c) 2024 vitasystems GmbH
 *
 * This file is part of project EHRbase Migration Tool
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ehrbase.migration.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.generic.AuditDetails;
import com.nedap.archie.rm.generic.PartyIdentified;
import com.nedap.archie.rm.generic.RevisionHistoryItem;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import com.nedap.archie.rm.support.identification.TerminologyId;
import com.nimbusds.jose.JOSEObjectType;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.xmlbeans.XmlException;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.groups.Tuple;
import org.ehrbase.migration.service.ProcessService;
import org.ehrbase.openehr.sdk.client.openehrclient.OpenEhrClientConfig;
import org.ehrbase.openehr.sdk.client.openehrclient.builder.ContributionBuilder;
import org.ehrbase.openehr.sdk.client.openehrclient.defaultrestclient.DefaultRestClient;
import org.ehrbase.openehr.sdk.response.dto.ContributionCreateDto;
import org.ehrbase.openehr.sdk.serialisation.RMDataFormat;
import org.ehrbase.openehr.sdk.serialisation.flatencoding.FlatFormat;
import org.ehrbase.openehr.sdk.serialisation.flatencoding.FlatJasonProvider;
import org.ehrbase.openehr.sdk.serialisation.jsonencoding.ArchieObjectMapperProvider;
import org.ehrbase.openehr.sdk.serialisation.jsonencoding.CanonicalJson;
import org.ehrbase.openehr.sdk.webtemplate.templateprovider.TemplateProvider;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openehr.schemas.v1.OPERATIONALTEMPLATE;
import org.openehr.schemas.v1.TemplateDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@ContextConfiguration(initializers = MigrationToolTestIT.Initializer.class)
class MigrationToolTestIT {

    protected static GenericContainer postgresOld;

    protected static GenericContainer postgresNew;

    protected static GenericContainer ehrbaseOld;

    protected static GenericContainer ehrbaseNew;

    protected static GenericContainer keycloakMock;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            var values = TestPropertyValues.of(
                    "spring.datasource.export.url=jdbc:postgresql://%s:%d/ehrbase"
                            .formatted(
                                    postgresOld.getHost(), postgresOld.getMappedPort(EHRbasePostgresOldContainer.PORT)),
                    "spring.datasource.import.url=jdbc:postgresql://%s:%d/ehrbase"
                            .formatted(
                                    postgresNew.getHost(), postgresNew.getMappedPort(EHRbasePostgresNewContainer.PORT)),
                    "terminate=false",
                    "export.batch-size=10");

            values.applyTo(configurableApplicationContext);
        }
    }

    private static final TemplateProvider TEMPLATE_PROVIDER = new TemplateProvider() {
        @Override
        public Optional<OPERATIONALTEMPLATE> find(String templateId) {
            try {
                return Optional.of(TemplateDocument.Factory.parse(
                                getClass().getResourceAsStream("/%s.opt".formatted(templateId.replace("-", "_"))))
                        .getTemplate());
            } catch (XmlException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    };

    @Autowired
    private ProcessService processService;

    static {
        try (Network network = Network.newNetwork()) {

            keycloakMock = new KeycloakMockContainer(network, Collections.emptyList());
            keycloakMock.start();

            postgresOld = new EHRbasePostgresOldContainer(network);
            postgresOld.start();

            ehrbaseOld = new EHRbaseContainerOld(network, List.of(postgresOld));
            ehrbaseOld.start();

            postgresNew = new EHRbasePostgresNewContainer(network);
            postgresNew.start();

            ehrbaseNew = new EHRbaseContainerNew(network, List.of(postgresNew));
            ehrbaseNew.start();
        }
    }

    @Test
    void test() throws IOException {
        // TODO proper system id, separate test aspects

        String committer1Subject = "efb90305-f546-40aa-9c78-ba199a089cf6";
        DefaultRestClient restClient1 = setupRestClientWithInternalTemplateProvider(ehrbaseOld, committer1Subject);

        String committer2Subject = "03364ab8-0c38-47b1-9ee7-8fb90d60ce68";
        DefaultRestClient restClient2 = setupRestClientWithInternalTemplateProvider(ehrbaseOld, committer2Subject);

        // Load example Composition
        restClient1.templateEndpoint().ensureExistence("conformance_ehrbase.de.v0");
        String exampleCompositionName = "conformance_ehrbase.de.v0_composition.json";

        // Create EHR
        EhrExampleData ehr1data = createExampleEhr(
                exampleCompositionName, restClient1, committer1Subject, restClient2, committer2Subject);

        EhrExampleData ehr2data = createExampleEhr(
                exampleCompositionName, restClient2, committer2Subject, restClient1, committer1Subject);

        IntStream.range(0, 25)
                //                .parallel()
                .forEach(i -> createExampleEhr(
                        exampleCompositionName, restClient1, committer1Subject, restClient2, committer2Subject));

        // Perform migration
        processService.db2db();

        DefaultRestClient restClientNew = setupRestClientWithDefaultTemplateProvider(ehrbaseNew, committer1Subject);

        Optional<OPERATIONALTEMPLATE> template =
                restClientNew.templateEndpoint().findTemplate("conformance-ehrbase.de.v0");

        assertThat(template).isNotEmpty();

        assertEhrMigration(restClientNew, ehr1data);
        assertEhrMigration(restClientNew, ehr2data);
    }

    private static void assertEhrMigration(DefaultRestClient restClientNew, EhrExampleData ehr1data)
            throws IOException {
        Optional<EhrStatus> ehrStatus = restClientNew.ehrEndpoint().getEhrStatus(ehr1data.ehr());
        assertThat(ehrStatus)
                .isNotEmpty()
                .get()
                .extracting(EhrStatus::isQueryable)
                .isEqualTo(false);

        Optional<Composition> raw = restClientNew
                .compositionEndpoint(ehr1data.ehr())
                .findRaw(getVersionedObjectUid(ehr1data.composition1Id()));

        assertThat(raw).isNotEmpty();

        assertRoundTrip(ehr1data.composition1(), raw.orElseThrow(), new String[] {}, new String[] {});

        assertRevisionHistory(
                restClientNew,
                ehr1data.ehr(),
                ehr1data.composition1Id(),
                Tuple.tuple("1", "creation", ehr1data.committer1ExternalRef(), ehr1data.committer1Name()),
                Tuple.tuple("2", "modification", ehr1data.committer1ExternalRef(), ehr1data.committer1Name()),
                Tuple.tuple("3", "modification", ehr1data.committer1ExternalRef(), ehr1data.committer1Name()));

        assertRevisionHistory(
                restClientNew,
                ehr1data.ehr(),
                ehr1data.composition2Id(),
                Tuple.tuple("1", "creation", ehr1data.committer2ExternalRef(), ehr1data.committer2Name()),
                Tuple.tuple("2", "deleted", ehr1data.committer2ExternalRef(), ehr1data.committer2Name()));

        Folder directory = restClientNew
                .directoryCrudEndpoint(ehr1data.ehr())
                .getDirectory()
                .orElse(null);

        assertThat(directory).isNotNull();
        assertThat(directory.getFolders()).hasSize(1);
        assertThat(directory.getFolders().getFirst().getNameAsString()).isEqualTo("SubFolder");
        assertThat(directory.getFolders().getFirst().getItems())
                .extracting(o -> o.getId().getValue())
                .containsExactly(ehr1data.composition1Id().getRoot().getValue());

        Optional<Contribution> contribution =
                restClientNew.contributionEndpoint(ehr1data.ehr()).find(ehr1data.saveContribution());

        assertThat(contribution)
                .isNotEmpty()
                .get()
                .extracting(c -> ((PartyIdentified) c.getAudit().getCommitter()).getName())
                .isEqualTo("test");
    }

    @NotNull
    private MigrationToolTestIT.EhrExampleData createExampleEhr(
            String exampleCompositionName,
            DefaultRestClient restClient1,
            String committer1Subject,
            DefaultRestClient restClient2,
            String committer2Subject) {
        UUID ehr = restClient1.ehrEndpoint().createEhr();

        EhrStatus status = restClient1.ehrEndpoint().getEhrStatus(ehr).orElseThrow();

        status.setQueryable(false);

        restClient1.ehrEndpoint().updateEhrStatus(ehr, status);

        // Store Composition Version 1
        Composition composition1 = loadComposition(exampleCompositionName);
        ObjectVersionId composition1Id = restClient1.compositionEndpoint(ehr).mergeRaw(composition1);

        // update 1->2
        composition1.setUid(composition1Id);
        composition1Id = restClient1.compositionEndpoint(ehr).mergeRaw(composition1);

        // update 2->3
        composition1.setUid(composition1Id);
        composition1Id = restClient1.compositionEndpoint(ehr).mergeRaw(composition1);

        List<RevisionHistoryItem> revisionHistory1 = restClient1
                .versionedCompositionEndpoint(ehr)
                .findRevisionHistory(getVersionedObjectUid(composition1Id));

        // Check source db

        String committer1Name = "EHRbase Internal " + committer1Subject;
        assertThat(revisionHistory1)
                .extracting(r -> ((PartyIdentified) r.getAudits().getFirst().getCommitter()).getName())
                .containsExactly(committer1Name, committer1Name, committer1Name);

        String committer1ExternalRef = getAuditCommitterUuid(revisionHistory1.getFirst());

        String committer2Name = "EHRbase Internal " + committer2Subject;
        Composition composition2 = loadComposition(exampleCompositionName);
        ObjectVersionId composition2Id = restClient2.compositionEndpoint(ehr).mergeRaw(composition2);

        List<RevisionHistoryItem> revisionHistory2 = restClient2
                .versionedCompositionEndpoint(ehr)
                .findRevisionHistory(getVersionedObjectUid(composition2Id));

        String committer2ExternalRef = getAuditCommitterUuid(revisionHistory2.getFirst());

        restClient2.compositionEndpoint(ehr).delete(composition2Id);

        // Folder with composition 1

        restClient2.directoryCrudEndpoint(ehr).createDirectory(new Folder());
        Folder folder = restClient2.directoryCrudEndpoint(ehr).getDirectory().orElseThrow();

        Folder subFolder = new Folder();
        subFolder.setNameAsString("SubFolder");
        folder.addFolder(subFolder);

        subFolder.addItem(
                new ObjectRef<>(new HierObjectId(composition1Id.getRoot().getValue()), "local", "Composition"));
        restClient2.directoryCrudEndpoint(ehr).updateDirectory(folder);

        // save via contribution
        Composition composition3 = loadComposition(exampleCompositionName);
        ContributionCreateDto contributionCreateDto = ContributionBuilder.builder(new AuditDetails(
                        "local.ehrbase.org",
                        new PartyIdentified(null, "test", null),
                        null,
                        new DvCodedText("creation", new CodePhrase(new TerminologyId("openehr"), "249")),
                        null))
                .addCompositionCreation(composition3)
                .build();
        UUID saveContribution;
        try {
            saveContribution = restClient2.contributionEndpoint(ehr).saveContribution(contributionCreateDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new EhrExampleData(
                ehr,
                composition1,
                composition1Id,
                committer1Name,
                committer1ExternalRef,
                committer2Name,
                composition2Id,
                committer2ExternalRef,
                saveContribution);
    }

    private record EhrExampleData(
            UUID ehr,
            Composition composition1,
            ObjectVersionId composition1Id,
            String committer1Name,
            String committer1ExternalRef,
            String committer2Name,
            ObjectVersionId composition2Id,
            String committer2ExternalRef,
            UUID saveContribution) {}

    private static void assertRevisionHistory(
            DefaultRestClient restClientNew, UUID ehr, ObjectVersionId composition1Id, Tuple... expectedAudits) {
        List<RevisionHistoryItem> revisionHistory1New = restClientNew
                .versionedCompositionEndpoint(ehr)
                .findRevisionHistory(getVersionedObjectUid(composition1Id));
        assertThat(revisionHistory1New)
                .extracting(
                        r -> r.getVersionId().getVersionTreeId().getValue(),
                        r -> r.getAudits().getFirst().getChangeType().getValue(),
                        MigrationToolTestIT::getAuditCommitterUuid,
                        r -> ((PartyIdentified) r.getAudits().getFirst().getCommitter()).getName())
                .containsExactly(expectedAudits);
    }

    @NotNull
    private Composition loadComposition(String exampleCompositionName) {
        Composition composition;
        try {
            composition = CanonicalJson.MARSHAL_OM.readValue(
                    getClass().getResourceAsStream("/" + exampleCompositionName), Composition.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        composition.setUid(null);
        return composition;
    }

    private static UUID getVersionedObjectUid(ObjectVersionId objectVersionId) {
        return UUID.fromString(objectVersionId.getObjectId().getValue());
    }

    private static String getAuditCommitterUuid(RevisionHistoryItem r) {
        return r.getAudits().getFirst().getCommitter().getExternalRef().getId().getValue();
    }

    private static void assertRoundTrip(
            Composition original, Composition imported, String[] expectedMissing, String[] expectedExtra)
            throws IOException {

        // uid is missing
        original.setUid(imported.getUid());

        String templateId = original.getArchetypeDetails().getTemplateId().getValue();

        RMDataFormat cut = new FlatJasonProvider(TEMPLATE_PROVIDER).buildFlatJson(FlatFormat.SIM_SDT, templateId);

        SoftAssertions softAssertions = new SoftAssertions();

        String actual = cut.marshal(imported);

        String expected = cut.marshal(original);

        List<String> errors = compareJsonFlat(actual, expected);

        softAssertions
                .assertThat(errors)
                .filteredOn(s -> s.startsWith("Missing"))
                .containsExactlyInAnyOrder(expectedMissing);

        softAssertions
                .assertThat(errors)
                .filteredOn(s -> s.startsWith("Extra"))
                .containsExactlyInAnyOrder(expectedExtra);

        softAssertions.assertAll();
    }

    private static List<String> compareJsonFlat(String actualJson, String expectedJson) throws JsonProcessingException {
        List<String> errors = new ArrayList<>();
        ObjectMapper objectMapper = ArchieObjectMapperProvider.getObjectMapper();

        Map<String, Object> actual = objectMapper.readValue(actualJson, Map.class);
        Map<String, Object> expected = objectMapper.readValue(expectedJson, Map.class);

        actual.forEach((key, value) -> {
            if (!expected.containsKey(key) || !expected.get(key).equals(value)) {
                errors.add(String.format("Missing path: %s, value: %s", key, value));
            }
        });

        expected.forEach((key, value) -> {
            if (!actual.containsKey(key) || !actual.get(key).equals(value)) {
                errors.add(String.format("Extra path: %s, value: %s", key, value));
            }
        });

        return errors;
    }

    public static String fixtureToken(String committerSub) {
        final Date now = new Date();
        JwtBuilder jwtBuilder = Jwts.builder()
                .issuedAt(now)
                .expiration(new Date(now.getTime() + Duration.ofSeconds(300).toMillis()))
                .claim("sub", committerSub)
                .claim(
                        "iss",
                        "http://%s:%d/auth/realms/master"
                                .formatted(KeycloakMockContainer.CONTAINER_NAME, KeycloakMockContainer.PORT))
                .signWith(KeycloakMockContainer.KEY_PAIR.getPrivate())
                .header()
                .add("kid", KeycloakMockContainer.KEY_ID)
                .add("typ", JOSEObjectType.JWT.getType())
                .and();

        jwtBuilder = jwtBuilder.claim("realm_access", Map.of("roles", List.of("hcp")));

        return jwtBuilder.compact();
    }

    private static DefaultRestClient setupRestClientWithDefaultTemplateProvider(
            GenericContainer container, String committerSub) {

        CloseableHttpClient client = HttpClientBuilder.create()
                .addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                    request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fixtureToken(committerSub));
                })
                .build();

        return new DefaultRestClient(new OpenEhrClientConfig(ehrBaseAPIEndpoint(container)), null, client);
    }

    private static DefaultRestClient setupRestClientWithInternalTemplateProvider(
            GenericContainer container, String committerSub) {

        CloseableHttpClient client = HttpClientBuilder.create()
                .addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                    request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + fixtureToken(committerSub));
                })
                .build();

        return new DefaultRestClient(new OpenEhrClientConfig(ehrBaseAPIEndpoint(container)), TEMPLATE_PROVIDER, client);
    }

    protected static URI ehrBaseAPIEndpoint(GenericContainer container) {
        return URI.create("http://%s:%d/ehrbase/"
                .formatted(container.getHost(), container.getMappedPort(EHRbaseContainerOld.PORT)));
    }
}
