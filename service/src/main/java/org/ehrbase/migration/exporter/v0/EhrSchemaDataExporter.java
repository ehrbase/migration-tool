/*
 * Copyright (c) 2023 vitasystems GmbH
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
package org.ehrbase.migration.exporter.v0;

import static org.ehrbase.migration.MigrationUtils.getPartyProxyUuid;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.SYSTEM;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.TEMPLATE_STORE;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.USERS;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.TermMapping;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.generic.PartyIdentified;
import com.nedap.archie.rm.generic.PartyProxy;
import com.nedap.archie.rm.support.identification.TerminologyId;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.System;
import org.ehrbase.migration.dto.User;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.SystemRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.UsersRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.CodePhraseRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.DSLContext;

class EhrSchemaDataExporter {

    private final DSLContext dslContext;
    private final PartyExporter partyExporter;

    private final Map<EhrSchema, String> systemIdMap = new ConcurrentHashMap<>();

    EhrSchemaDataExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        partyExporter = new PartyExporter(dslContext);
    }

    protected <T extends Locatable> void addVersion(
            Map<UUID, List<VersionedObjectData<T>>> versionedObjects, EhrSchema tenant) {
        String systemId = getSystemId(tenant);
        versionedObjects
                .values()
                .forEach(e -> e.forEach(s -> {
                    int i = 1;
                    for (OriginalVersion<T> v : s.originalVersions()) {
                        v.getUid().setValue(v.getUid().getValue() + "::" + systemId + "::" + i);
                        i++;
                    }
                }));
    }

    protected String getSystemId(EhrSchema ehrSchema) {
        return systemIdMap.computeIfAbsent(ehrSchema, t -> {
            return dslContext
                    .select(SYSTEM.SETTINGS)
                    .from(SYSTEM)
                    // other systems could be saved do to missing validation but all data should only have the default
                    // system id
                    .where(SYSTEM.DESCRIPTION.eq("DEFAULT RUNNING SYSTEM"))
                    .fetchSingle()
                    .value1();
        });
    }

    protected EhrSchemaData getEhrSchemaData() {
        EhrSchemaData ehrSchemaData = new EhrSchemaData();

        ehrSchemaData.setSystem(getSystem());
        ehrSchemaData.setTemplates(getTemplates());
        ehrSchemaData.setUsers(getUsers());

        return ehrSchemaData;
    }

    private List<User> getUsers() {

        Map<UUID, User> byParty = dslContext.select(USERS.fields()).from(USERS).fetch().into(UsersRecord.class).stream()
                .map(r -> new User(r.getPartyId(), r.getUsername(), PartyExporter.buildDummy(r.getPartyId())))
                .collect(Collectors.toMap(u -> getPartyProxyUuid(u.getPartyIdentified()), Function.identity()));

        Map<UUID, PartyProxy> party = partyExporter.findParties(byParty.keySet());

        byParty.forEach((k, v) -> v.setPartyIdentified((PartyIdentified) party.get(k)));

        return new ArrayList<>(byParty.values());
    }

    private List<EhrSchemaData.TemplateData> getTemplates() {
        return dslContext
                .select(TEMPLATE_STORE.TEMPLATE_ID, TEMPLATE_STORE.CONTENT, TEMPLATE_STORE.SYS_TRANSACTION)
                .from(TEMPLATE_STORE)
                .fetch()
                .stream()
                .map(r -> new EhrSchemaData.TemplateData(r.component1(), r.component2(), r.component3()))
                .toList();
    }

    private System getSystem() {
        SystemRecord defaultRunningSystem = dslContext
                .selectFrom(SYSTEM)
                // other systems could be saved do to missing validation but all data should only have the default
                // system id
                .where(SYSTEM.DESCRIPTION.eq("DEFAULT RUNNING SYSTEM"))
                .fetchSingle();

        return new System(
                defaultRunningSystem.getId(),
                defaultRunningSystem.getDescription(),
                defaultRunningSystem.getSettings());
    }

    protected static DvText to(DvCodedTextRecord dvCodedTextRecord) {

        final DvText retObject;

        CodePhraseRecord codePhraseDefiningCode = dvCodedTextRecord.getDefiningCode();
        CodePhraseRecord codePhraseLanguage = dvCodedTextRecord.getLanguage();
        CodePhraseRecord codePhraseEncoding = dvCodedTextRecord.getEncoding();

        if (codePhraseDefiningCode != null) {
            retObject = new DvCodedText(
                    dvCodedTextRecord.getValue(),
                    codePhraseLanguage == null
                            ? null
                            : new CodePhrase(
                                    new TerminologyId(codePhraseLanguage.getTerminologyIdValue()),
                                    codePhraseLanguage.getCodeString()),
                    codePhraseEncoding == null
                            ? null
                            : new CodePhrase(
                                    new TerminologyId(codePhraseEncoding.getTerminologyIdValue()),
                                    codePhraseEncoding.getCodeString()),
                    new CodePhrase(
                            new TerminologyId(codePhraseDefiningCode.getTerminologyIdValue()),
                            codePhraseDefiningCode.getCodeString()));
        } else { // assume DvText
            retObject = new DvText(
                    dvCodedTextRecord.getValue(),
                    codePhraseLanguage == null
                            ? null
                            : new CodePhrase(
                                    new TerminologyId(codePhraseLanguage.getTerminologyIdValue()),
                                    codePhraseLanguage.getCodeString()),
                    codePhraseEncoding == null
                            ? null
                            : new CodePhrase(
                                    new TerminologyId(codePhraseEncoding.getTerminologyIdValue()),
                                    codePhraseEncoding.getCodeString()));
        }

        if (dvCodedTextRecord.getTermMapping() != null) {
            for (String dvCodedTextTermMappingRecord : dvCodedTextRecord.getTermMapping()) {

                String[] attributes = dvCodedTextTermMappingRecord.split("\\|");

                (retObject)
                        .addMapping(new TermMapping(
                                new CodePhrase(new TerminologyId(attributes[4]), attributes[5]),
                                attributes[0].charAt(0),
                                new DvCodedText(
                                        attributes[1],
                                        new CodePhrase(new TerminologyId(attributes[2]), attributes[3]))));
            }
        }

        return retObject;
    }

    public static DvDateTime decodeDvDateTime(Timestamp timestamp, String timezone) {
        if (timestamp == null) {
            return null;
        }

        TemporalAccessor temporal;
        if (timezone != null) {
            temporal = timestamp.toLocalDateTime().atOffset(ZoneOffset.of(timezone));
        } else {
            temporal = timestamp.toLocalDateTime();
        }
        return new DvDateTime(temporal);
    }
}
