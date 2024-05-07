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
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.IDENTIFIER;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.PARTY_IDENTIFIED;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvIdentifier;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.generic.PartyIdentified;
import com.nedap.archie.rm.generic.PartyProxy;
import com.nedap.archie.rm.generic.PartyRelated;
import com.nedap.archie.rm.generic.PartySelf;
import com.nedap.archie.rm.support.identification.GenericId;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectId;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import com.nedap.archie.rm.support.identification.PartyRef;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.IdentifierRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.PartyIdentifiedRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.DSLContext;

class PartyExporter {

    private final DSLContext dslContext;

    public PartyExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    protected Map<UUID, PartyProxy> findParties(Collection<UUID> ids) {

        return dslContext
                .select(PARTY_IDENTIFIED.fields())
                .select(IDENTIFIER.fields())
                .from(PARTY_IDENTIFIED)
                .leftJoin(IDENTIFIER)
                .on(IDENTIFIER.PARTY.eq(PARTY_IDENTIFIED.ID))
                .where(PARTY_IDENTIFIED.ID.in(ids))
                .fetchGroups(r -> r.into(PartyIdentifiedRecord.class), r -> r.into(IdentifierRecord.class))
                .entrySet()
                .stream()
                .map(e -> to(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private static Pair<UUID, PartyProxy> to(PartyIdentifiedRecord record, List<IdentifierRecord> identifiers) {

        PartyProxy partyProxy = buildPartyProxy(record);

        if (partyProxy instanceof com.nedap.archie.rm.generic.PartyIdentified partyIdentified) {
            partyIdentified.setIdentifiers(identifiers.stream()
                    .filter(r -> r.getIdValue() != null)
                    .map(PartyExporter::to)
                    .toList());
            ((com.nedap.archie.rm.generic.PartyIdentified) partyProxy).setName(record.getName());
        }
        if (partyProxy instanceof PartyRelated partyRelated) {

            DvCodedTextRecord relationship = record.getRelationship();

            partyRelated.setRelationship((DvCodedText) EhrSchemaDataExporter.to(relationship));
        }

        return Pair.of(record.getId(), partyProxy);
    }

    protected static PartyProxy buildPartyProxy(PartyIdentifiedRecord record) {
        PartyProxy partyProxy =
                switch (record.getPartyType()) {
                    case party_identified -> new com.nedap.archie.rm.generic.PartyIdentified();
                    case party_self -> new PartySelf();
                    case party_related -> new PartyRelated();
                };

        ObjectId objectId =
                switch (record.getObjectIdType()) {
                    case generic_id -> new GenericId(record.getPartyRefValue(), record.getPartyRefScheme());
                    case hier_object_id -> new HierObjectId(record.getPartyRefValue());
                    case object_version_id -> new ObjectVersionId(record.getPartyRefValue());
                    default -> null;
                };

        if (objectId != null) {
            partyProxy.setExternalRef(new PartyRef(objectId, record.getPartyRefNamespace(), record.getPartyRefType()));
        }
        return partyProxy;
    }

    private static DvIdentifier to(IdentifierRecord record) {

        DvIdentifier identifier = new DvIdentifier();
        identifier.setIssuer(record.getIssuer());
        identifier.setAssigner(record.getAssigner());
        identifier.setId(record.getIdValue());
        identifier.setType(record.getTypeName());
        return identifier;
    }

    protected <T extends Locatable> void addParty(Map<UUID, List<VersionedObjectData<T>>> collect) {

        Map<UUID, List<Consumer<PartyProxy>>> byParty = Stream.concat(
                        collect.values().stream()
                                .flatMap(Collection::stream)
                                .map(VersionedObjectData::originalVersions)
                                .flatMap(Set::stream)
                                .flatMap(v1 -> getStream(v1.getData())),
                        collect.values().stream()
                                .flatMap(Collection::stream)
                                .map(VersionedObjectData::originalVersions)
                                .flatMap(Set::stream)
                                .map(v -> {
                                    Consumer<PartyProxy> consumer = v.getCommitAudit()::setCommitter;
                                    return Pair.of(
                                            getPartyProxyUuid(v.getCommitAudit().getCommitter()), consumer);
                                }))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        findParties(byParty.keySet()).forEach((k, v) -> byParty.get(k).forEach(c -> c.accept(v)));
    }

    private static <T> Stream<Pair<UUID, Consumer<PartyProxy>>> getStream(T data) {
        List<Pair<Supplier<PartyProxy>, Consumer<PartyProxy>>> list = new ArrayList<>();
        if (data instanceof Composition composition) {

            list.add(Pair.of(composition::getComposer, composition::setComposer));
            if (composition.getContext() != null) {
                list.add(Pair.of(composition.getContext()::getHealthCareFacility, healthCareFacility -> composition
                        .getContext()
                        .setHealthCareFacility((PartyIdentified) healthCareFacility)));
                composition
                        .getContext()
                        .getParticipations()
                        .forEach(p -> list.add(Pair.of(p::getPerformer, p::setPerformer)));
            }

        } else if (data instanceof EhrStatus status) {

            list.add(Pair.of(status::getSubject, subject -> status.setSubject((PartySelf) subject)));
        }

        return list.stream()
                .filter(p -> p.getLeft().get() != null)
                .map(p -> Pair.of(getPartyProxyUuid(p.getLeft().get()), p.getRight()));
    }

    protected static PartyIdentified buildDummy(UUID composer) {
        if (composer == null) {
            return null;
        }
        return new PartyIdentified(new PartyRef(new HierObjectId(composer.toString()), null, null), null, null);
    }

    protected static PartySelf buildDummySelf(UUID composer) {
        if (composer == null) {
            return null;
        }
        return new PartySelf(new PartyRef(new HierObjectId(composer.toString()), null, null));
    }
}
