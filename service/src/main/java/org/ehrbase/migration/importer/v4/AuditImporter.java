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
package org.ehrbase.migration.importer.v4;

import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.AUDIT_DETAILS;

import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.generic.AuditDetails;
import com.nedap.archie.rm.generic.PartyProxy;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.importer.v4.jooq.pg.enums.ContributionChangeType;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.openehr.sdk.serialisation.jsonencoding.CanonicalJson;
import org.jooq.DSLContext;
import org.jooq.JSONB;

public class AuditImporter {

    private final DSLContext dslContext;

    public AuditImporter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public <T> void addAudit(
            Function<T, UUID> getUuid,
            BiConsumer<T, UUID> setAudit,
            Collection<Pair<T, AuditDetails>> rootRecords,
            AuditDetailsTargetType targetType,
            EhrSchemaData ehrSchemaData) {

        Map<UUID, Pair<T, AuditDetails>> collect =
                rootRecords.stream().collect(Collectors.toMap(p -> getUuid.apply(p.getLeft()), Function.identity()));

        Map<UUID, UUID> written = write(
                collect.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                        .getValue())),
                targetType,
                ehrSchemaData);

        written.forEach((k, v) -> setAudit.accept(collect.get(k).getLeft(), v));
    }

    protected <V> Map<V, UUID> write(
            Map<V, AuditDetails> auditDetailsMap, AuditDetailsTargetType targetType, EhrSchemaData ehrSchemaData) {

        Map<V, AuditDetailsRecord> collect = auditDetailsMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> toRecord(e.getValue(), targetType, ehrSchemaData)));

        ImportService.executeBulkInsert(dslContext, collect.values().stream(), AUDIT_DETAILS);

        return collect.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getId()));
    }

    private AuditDetailsRecord toRecord(
            AuditDetails auditDetails, AuditDetailsTargetType targetType, EhrSchemaData ehrSchemaData) {

        AuditDetailsRecord auditDetailsRecord = dslContext.newRecord(AUDIT_DETAILS);

        auditDetailsRecord.setDescription(Optional.of(auditDetails)
                .map(AuditDetails::getDescription)
                .map(DvText::getValue)
                .orElse(null));
        auditDetailsRecord.setChangeType(ContributionChangeType.valueOf(
                auditDetails.getChangeType().getValue().toLowerCase()));

        auditDetailsRecord.setTimeCommitted(
                OffsetDateTime.from(auditDetails.getTimeCommitted().getValue()));
        auditDetailsRecord.setTargetType(targetType.getAlias());
        auditDetailsRecord.setId(UUID.randomUUID());

        Pair<UUID, PartyProxy> user = ehrSchemaData.findCommitterUser(auditDetails.getCommitter(), dslContext);

        if (user.getRight() != null) {
            auditDetailsRecord.setCommitter(JSONB.valueOf(new CanonicalJson().marshal(user.getRight())));
        }
        auditDetailsRecord.setUserId(user.getLeft());

        return auditDetailsRecord;
    }
}
