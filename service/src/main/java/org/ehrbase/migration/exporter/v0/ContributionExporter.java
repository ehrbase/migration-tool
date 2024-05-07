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
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.AuditDetails.AUDIT_DETAILS;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.Contribution.CONTRIBUTION;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.generic.PartyProxy;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ContributionRecord;
import org.jooq.DSLContext;

class ContributionExporter {

    private final DSLContext dslContext;

    private final PartyExporter partyExporter;

    public ContributionExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        this.partyExporter = new PartyExporter(dslContext);
    }

    protected Map<UUID, List<Contribution>> findContributions(Collection<UUID> ehrIds) {

        Map<UUID, List<Contribution>> collect = dslContext
                .select(CONTRIBUTION.fields())
                .select(AUDIT_DETAILS.fields())
                .from(CONTRIBUTION)
                .join(AUDIT_DETAILS)
                .on(CONTRIBUTION.HAS_AUDIT.eq(AUDIT_DETAILS.ID))
                .where(CONTRIBUTION.EHR_ID.in(ehrIds))
                .stream()
                .map(r -> to(r.into(ContributionRecord.class), r.into(AuditDetailsRecord.class)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        Map<UUID, List<Consumer<PartyProxy>>> consumers = collect.values().stream()
                .flatMap(List::stream)
                .map(c -> {
                    Consumer<PartyProxy> committerConsumer = c.getAudit()::setCommitter;
                    return Pair.of(getPartyProxyUuid(c.getAudit().getCommitter()), committerConsumer);
                })
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getRight, Collectors.toList())));

        Map<UUID, PartyProxy> partyById = partyExporter.findParties(consumers.keySet());

        consumers.forEach((k, v) -> {
            v.forEach(c -> {
                if (partyById.containsKey(k)) {
                    c.accept(partyById.get(k));
                }
            });
        });

        return collect;
    }

    private static Pair<UUID, Contribution> to(
            ContributionRecord contributionRecord, AuditDetailsRecord auditDetailsRecord) {
        Contribution contribution = new Contribution();

        contribution.setAudit(AuditExporter.to(auditDetailsRecord));
        contribution.setUid(new HierObjectId(contributionRecord.getId().toString()));

        return Pair.of(contributionRecord.getEhrId(), contribution);
    }

    static void addVersionRef(Contribution contribution, OriginalVersion<? extends Locatable> version, String type) {
        contribution.getVersions().add(new ObjectRef<>(version.getUid(), "local", type));
    }
}
