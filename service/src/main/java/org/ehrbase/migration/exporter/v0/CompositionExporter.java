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

import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.AUDIT_DETAILS;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.ENTRY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.EVENT_CONTEXT;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.PARTICIPATION_HISTORY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.TERRITORY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.Composition.COMPOSITION;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompositionHistory.COMPOSITION_HISTORY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.EntryHistory.ENTRY_HISTORY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.EventContextHistory.EVENT_CONTEXT_HISTORY;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.Participation.PARTICIPATION;

import com.nedap.archie.rm.archetyped.Archetyped;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.archetyped.TemplateId;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datastructures.ItemStructure;
import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.quantity.DvInterval;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.support.identification.ArchetypeID;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import com.nedap.archie.rm.support.identification.TerminologyId;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.jooq.dbencoding.RawJson;
import org.ehrbase.migration.exporter.v0.jooq.dbencoding.rmobject.FeederAuditEncoding;
import org.ehrbase.migration.exporter.v0.jooq.dbencoding.rmobject.LinksEncoding;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.CompositionRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EntryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EventContextRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ParticipationRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.Result;

public class CompositionExporter {

    private final DSLContext dslContext;
    private final EhrSchemaDataExporter ehrSchemaDataExporter;
    private final PartyExporter partyExporter;

    private final Map<Integer, String> territoryMap;

    /**
     *  Since old ehrbase has only an implicit version, it is derived from ordering by commit time
     */
    public static final Comparator<OriginalVersion<?>> ORIGINAL_VERSION_COMPARATOR = Comparator.comparing(
            v -> (OffsetDateTime) v.getCommitAudit().getTimeCommitted().getValue());

    public CompositionExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.ehrSchemaDataExporter = new EhrSchemaDataExporter(dslContext);
        this.partyExporter = new PartyExporter(dslContext);

        territoryMap = dslContext
                .select(TERRITORY.CODE, TERRITORY.TWOLETTER)
                .from(TERRITORY)
                .fetchMap(TERRITORY.CODE, TERRITORY.TWOLETTER);
    }

    protected Map<UUID, List<VersionedObjectData<Composition>>> findCompositions(
            Collection<UUID> ehrIds, EhrSchema tenant) {
        Result<Record> fetch = dslContext
                .select(COMPOSITION.fields())
                .select(ENTRY.fields())
                .select(PARTICIPATION.fields())
                .select(EVENT_CONTEXT.fields())
                .select(AUDIT_DETAILS.fields())
                .from(COMPOSITION)
                // optional in case of delete
                .leftJoin(ENTRY)
                .on(COMPOSITION.ID.eq(ENTRY.COMPOSITION_ID))
                .join(AUDIT_DETAILS)
                .on(COMPOSITION.HAS_AUDIT.eq(AUDIT_DETAILS.ID))
                // optional in RM modell
                .leftJoin(EVENT_CONTEXT)
                .on(EVENT_CONTEXT
                        .COMPOSITION_ID
                        .eq(COMPOSITION.ID)
                        .and(EVENT_CONTEXT.SYS_TRANSACTION.eq(COMPOSITION.SYS_TRANSACTION)))
                // optional in RM modell
                .leftJoin(PARTICIPATION)
                .on(EVENT_CONTEXT
                        .ID
                        .eq(PARTICIPATION.EVENT_CONTEXT)
                        .and(PARTICIPATION.SYS_TRANSACTION.eq(COMPOSITION.SYS_TRANSACTION)))
                .where(COMPOSITION.EHR_ID.in(ehrIds))
                .unionAll(dslContext
                        .select(COMPOSITION_HISTORY.fields(COMPOSITION.fields()))
                        .select(ENTRY_HISTORY.fields(ENTRY.fields()))
                        .select(PARTICIPATION_HISTORY.fields(PARTICIPATION.fields()))
                        .select(EVENT_CONTEXT_HISTORY.fields(EVENT_CONTEXT.fields()))
                        .select(AUDIT_DETAILS.fields())
                        .from(COMPOSITION_HISTORY)
                        .leftJoin(ENTRY_HISTORY)
                        .on(COMPOSITION_HISTORY
                                .ID
                                .eq(ENTRY_HISTORY.COMPOSITION_ID)
                                .and(COMPOSITION_HISTORY.SYS_TRANSACTION.eq(ENTRY_HISTORY.SYS_TRANSACTION)))
                        .join(AUDIT_DETAILS)
                        .on(COMPOSITION_HISTORY.HAS_AUDIT.eq(AUDIT_DETAILS.ID))
                        .leftJoin(EVENT_CONTEXT_HISTORY)
                        .on(EVENT_CONTEXT_HISTORY
                                .COMPOSITION_ID
                                .eq(COMPOSITION_HISTORY.ID)
                                .and(EVENT_CONTEXT_HISTORY.SYS_TRANSACTION.eq(COMPOSITION_HISTORY.SYS_TRANSACTION)))
                        .leftJoin(PARTICIPATION_HISTORY)
                        .on(EVENT_CONTEXT_HISTORY
                                .ID
                                .eq(PARTICIPATION_HISTORY.EVENT_CONTEXT)
                                .and(PARTICIPATION_HISTORY.SYS_TRANSACTION.eq(COMPOSITION_HISTORY.SYS_TRANSACTION)))
                        .where(COMPOSITION_HISTORY.EHR_ID.in(ehrIds)))
                .fetch();

        // Reduce the result so we get for each Composition an Array of ParticipationRecord
        Map<CompositionRecord, Optional<Triple<CompositionRecord, Record, ArrayList<ParticipationRecord>>>>
                compositionRecordOptionalMap = fetch.stream()
                        .map(r -> Triple.of(
                                r.into(CompositionRecord.class),
                                r,
                                new ArrayList<>(Collections.singletonList(r.into(ParticipationRecord.class)))))
                        .collect(Collectors.groupingBy(Triple::getLeft, Collectors.reducing((t1, t2) -> {
                            t1.getRight().addAll(t2.getRight());
                            return Triple.of(t1.getLeft(), t1.getMiddle(), t1.getRight());
                        })));

        // Create a Map ehrId -> compositionId ->  Set of versions order by commit_audit.time_committed.value
        // (OffsetDateTime)
        Map<UUID, List<VersionedObjectData<Composition>>> collect = compositionRecordOptionalMap.values().stream()
                .map(o -> this.toOriginalVersion(o.get().getMiddle(), o.get().getRight()))
                .collect(groupingVersionedObjectsByEhrIdCollector());

        // since old ehrbase has only a implicit version we need to create the version by ordering by time.
        ehrSchemaDataExporter.addVersion(collect, tenant);
        // we fetch and add parties as batch
        partyExporter.addParty(collect);

        return collect;
    }

    private Pair<UUID, OriginalVersion<Composition>> toOriginalVersion(
            Record triple, ArrayList<ParticipationRecord> participations) {

        OriginalVersion<Composition> originalVersion = new OriginalVersion<>();
        CompositionRecord compositionRecord = triple.into(CompositionRecord.class);
        EntryRecord entryRecord = triple.into(EntryRecord.class);
        AuditDetailsRecord auditDetailsRecord = triple.into(AuditDetailsRecord.class);
        EventContextRecord eventContextRecord = triple.into(EventContextRecord.class);

        // Version will be added later
        originalVersion.setUid(new ObjectVersionId(compositionRecord.getId().toString()));
        originalVersion.setContribution(new ObjectRef<>(
                new HierObjectId(compositionRecord.getInContribution().toString()), "local", "CONTRIBUTION"));
        originalVersion.setCommitAudit(AuditExporter.to(auditDetailsRecord));

        if (!originalVersion
                .getCommitAudit()
                .getChangeType()
                .getDefiningCode()
                .getCodeString()
                .equals("" + AuditExporter.ContributionChangeType.DELETED.code)) {
            originalVersion.setData(
                    new RawJson().unmarshal(entryRecord.getEntry().data()));
            Composition data = originalVersion.getData();
            data.setUid(originalVersion.getUid());
            data.setLanguage(new CodePhrase(new TerminologyId("ISO_639-1"), compositionRecord.getLanguage()));
            data.setTerritory(new CodePhrase(
                    new TerminologyId("ISO_3166-1"), territoryMap.get(compositionRecord.getTerritory())));
            data.setCategory((DvCodedText) EhrSchemaDataExporter.to(entryRecord.getCategory()));
            data.setFeederAudit(Optional.ofNullable(compositionRecord.getFeederAudit())
                    .map(JSONB::data)
                    .map(Object::toString)
                    .map(s -> new FeederAuditEncoding().fromDB(s))
                    .orElse(null));
            data.setLinks(Optional.ofNullable(compositionRecord.getLinks())
                    .map(JSONB::data)
                    .map(Object::toString)
                    .map(s -> new LinksEncoding().fromDB(s))
                    .orElse(null));

            Archetyped archetypeDetails = new Archetyped();
            TemplateId templateId = new TemplateId();
            templateId.setValue(entryRecord.getTemplateId());
            archetypeDetails.setTemplateId(templateId);
            archetypeDetails.setArchetypeId(new ArchetypeID(entryRecord.getArchetypeId()));
            archetypeDetails.setRmVersion(entryRecord.getRmVersion());
            data.setArchetypeDetails(archetypeDetails);

            // Will be replaced later
            data.setComposer(PartyExporter.buildDummy(compositionRecord.getComposer()));

            if (eventContextRecord.getId() != null) {
                data.setContext(to(eventContextRecord));
                data.getContext()
                        .setParticipations(participations.stream()
                                .filter(p -> p.getId() != null)
                                .map(CompositionExporter::to)
                                .toList());
            }
        }

        return Pair.of(compositionRecord.getEhrId(), originalVersion);
    }

    private static com.nedap.archie.rm.composition.EventContext to(EventContextRecord eventContextRecord) {

        ItemStructure otherContext = null;

        if (eventContextRecord.getOtherContext() != null) {
            otherContext = new RawJson()
                    .unmarshal((eventContextRecord.getOtherContext().data()), ItemStructure.class);
        }

        DvCodedTextRecord dvCodedTextRecord = eventContextRecord.getSetting();

        UUID composer = eventContextRecord.getFacility();

        Timestamp timestamp = eventContextRecord.getEndTime();
        String timezone = eventContextRecord.getEndTimeTzid();

        Timestamp timestamp1 = eventContextRecord.getStartTime();
        String timezone1 = eventContextRecord.getStartTimeTzid();

        return new com.nedap.archie.rm.composition.EventContext(
                PartyExporter.buildDummy(composer),
                EhrSchemaDataExporter.decodeDvDateTime(timestamp1, timezone1),
                EhrSchemaDataExporter.decodeDvDateTime(timestamp, timezone),
                null,
                eventContextRecord.getLocation(),
                (DvCodedText) EhrSchemaDataExporter.to(dvCodedTextRecord),
                otherContext);
    }

    private static com.nedap.archie.rm.generic.Participation to(ParticipationRecord participationRecord) {

        DvInterval<DvDateTime> interval;
        if (participationRecord.getTimeLower() != null) {
            //  time null value is allowed for participation
            Timestamp timestamp = participationRecord.getTimeUpper();
            String timezone = participationRecord.getTimeUpperTz();

            Timestamp timestamp1 = participationRecord.getTimeLower();
            String timezone1 = participationRecord.getTimeLowerTz();

            interval = new DvInterval<>(
                    EhrSchemaDataExporter.decodeDvDateTime(timestamp1, timezone1),
                    EhrSchemaDataExporter.decodeDvDateTime(timestamp, timezone));
        } else {
            interval = null;
        }
        DvCodedText mode;
        if (participationRecord.getMode() != null) {
            DvCodedTextRecord dvCodedTextRecord = participationRecord.getMode();

            mode = (DvCodedText) EhrSchemaDataExporter.to(dvCodedTextRecord);
        } else {
            mode = null;
        }

        DvCodedTextRecord dvCodedTextRecord = participationRecord.getFunction();

        UUID composer = participationRecord.getPerformer();

        return new com.nedap.archie.rm.generic.Participation(
                PartyExporter.buildDummy(composer), EhrSchemaDataExporter.to(dvCodedTextRecord), mode, interval);
    }

    protected static <T extends Locatable>
            Collector<Pair<UUID, OriginalVersion<T>>, ?, Map<UUID, List<VersionedObjectData<T>>>>
                    groupingVersionedObjectsByEhrIdCollector() {

        Collector<Pair<UUID, OriginalVersion<T>>, ?, Map<UUID, List<OriginalVersion<T>>>> c =
                Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toList()));
        return Collectors.collectingAndThen(c, a -> a.entrySet().stream()
                .collect(Collectors.toMap(Entry::getKey, e -> toVersionedObjectDataList(e.getValue()))));
    }

    private static <T extends Locatable> List<VersionedObjectData<T>> toVersionedObjectDataList(
            final List<OriginalVersion<T>> versions) {
        return versions.stream()
                .collect(Collectors.groupingBy(
                        p -> UUID.fromString(p.getUid().getRoot().getValue())))
                .entrySet()
                .stream()
                .map(e -> new VersionedObjectData<>(
                        e.getKey(),
                        e.getValue().stream()
                                .collect(Collectors.toCollection(() -> new TreeSet<>(ORIGINAL_VERSION_COMPARATOR)))))
                .toList();
    }
}
