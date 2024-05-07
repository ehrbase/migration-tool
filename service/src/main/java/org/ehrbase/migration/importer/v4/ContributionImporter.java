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

import static org.ehrbase.migration.importer.v4.jooq.pg.tables.Contribution.CONTRIBUTION;

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.generic.AuditDetails;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.ContributionRecord;
import org.jooq.DSLContext;

public class ContributionImporter {

    private final DSLContext dslContext;

    private final AuditImporter auditImporter;

    public ContributionImporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        auditImporter = new AuditImporter(dslContext);
    }

    protected void write(Map<UUID, List<Contribution>> contributionMap, EhrSchemaData ehrSchemaData) {

        List<Pair<ContributionRecord, AuditDetails>> collect = contributionMap.entrySet().stream()
                .flatMap(e -> e.getValue().stream().map(v -> Pair.of(e.getKey(), v)))
                .map(p -> Pair.of(to(p.getValue(), p.getKey()), p.getValue().getAudit()))
                .toList();

        auditImporter.addAudit(
                ContributionRecord::getId,
                ContributionRecord::setHasAudit,
                collect,
                AuditDetailsTargetType.CONTRIBUTION,
                ehrSchemaData);

        ImportService.executeBulkInsert(dslContext, collect.stream().map(Pair::getKey), CONTRIBUTION);
    }

    private ContributionRecord to(Contribution contribution, UUID ehrId) {

        ContributionRecord contributionRecord = dslContext.newRecord(CONTRIBUTION);
        contributionRecord.setId(UUID.fromString(contribution.getUid().getValue()));
        contributionRecord.setEhrId(ehrId);

        return contributionRecord;
    }
}
