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

import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.*;
import static org.ehrbase.migration.exporter.v0.jooq.pg.Tables.STATUS_HISTORY;

import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.StatusRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.DSLContext;

class EhrStatusExporter {

    private final DSLContext dslContext;
    private final EhrSchemaDataExporter ehrSchemaDataExporter;
    private final PartyExporter partyExporter;

    public EhrStatusExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.ehrSchemaDataExporter = new EhrSchemaDataExporter(dslContext);
        this.partyExporter = new PartyExporter(dslContext);
    }

    protected Map<UUID, List<VersionedObjectData<EhrStatus>>> findStatus(Collection<UUID> ehrIds, EhrSchema tenant) {

        // Create a Map ehrId -> statusId ->  Set of versions order by version number
        Map<UUID, List<VersionedObjectData<EhrStatus>>> collect = dslContext
                .select(STATUS.fields())
                .select(AUDIT_DETAILS.fields())
                .from(STATUS)
                .join(AUDIT_DETAILS)
                .on(STATUS.HAS_AUDIT.eq(AUDIT_DETAILS.ID))
                .where(STATUS.EHR_ID.in(ehrIds))
                .unionAll(dslContext
                        .select(STATUS_HISTORY.fields(STATUS.fields()))
                        .select(AUDIT_DETAILS.fields())
                        .from(STATUS_HISTORY)
                        .join(AUDIT_DETAILS)
                        .on(STATUS_HISTORY.HAS_AUDIT.eq(AUDIT_DETAILS.ID))
                        .where(STATUS_HISTORY.EHR_ID.in(ehrIds)))
                .fetch()
                .stream()
                .map(r -> to(r.into(StatusRecord.class), r.into(AuditDetailsRecord.class)))
                .collect(CompositionExporter.groupingVersionedObjectsByEhrIdCollector());

        // since old ehrbase has only a implicit version we need to create the version by ordering by time.
        ehrSchemaDataExporter.addVersion(collect, tenant);
        // we fetch and add parties as batch
        partyExporter.addParty(collect);

        return collect;
    }

    private static Pair<UUID, OriginalVersion<EhrStatus>> to(
            StatusRecord ehrStatusRecord, AuditDetailsRecord auditDetailsRecord) {

        OriginalVersion<com.nedap.archie.rm.ehr.EhrStatus> originalVersion = new OriginalVersion<>();

        originalVersion.setUid(new ObjectVersionId(ehrStatusRecord.getId().toString()));
        originalVersion.setCommitAudit(AuditExporter.to(auditDetailsRecord));
        originalVersion.setContribution(new ObjectRef<>(
                new HierObjectId(ehrStatusRecord.getInContribution().toString()), "local", "CONTRIBUTION"));

        EhrStatus status = new EhrStatus();
        status.setUid(originalVersion.getUid());

        status.setModifiable(ehrStatusRecord.getIsModifiable());
        status.setQueryable(ehrStatusRecord.getIsQueryable());
        // set otherDetails if available
        if (ehrStatusRecord.getOtherDetails() != null) {
            status.setOtherDetails(ehrStatusRecord.getOtherDetails());
        }

        // Locatable attribute
        status.setArchetypeNodeId(ehrStatusRecord.getArchetypeNodeId());
        DvCodedTextRecord dvCodedTextRecord = ehrStatusRecord.getName();

        status.setName(EhrSchemaDataExporter.to(dvCodedTextRecord));

        UUID composer = ehrStatusRecord.getParty();

        status.setSubject(PartyExporter.buildDummySelf(composer));

        originalVersion.setData(status);

        return Pair.of(ehrStatusRecord.getEhrId(), originalVersion);
    }
}
