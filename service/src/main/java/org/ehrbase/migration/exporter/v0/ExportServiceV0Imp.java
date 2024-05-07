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

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.changecontrol.Version;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ehrbase.migration.config.ExportDatasourceConfiguration;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.ExportService;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TenantRecord;
import org.jooq.DSLContext;
import org.jooq.JSON;

public class ExportServiceV0Imp implements ExportService {

    private final DSLContext dslContext;
    private final ExportDatasourceConfiguration.TenantHandlingExportConnectionProvider connectionProvider;

    private final EhrSchemaDataExporter ehrSchemaDataExporter;
    private final EhrExporter ehrExporter;
    private final CompositionExporter compositionExporter;
    private final EhrStatusExporter ehrStatusExporter;

    private final ContributionExporter contributionExporter;

    private final FolderExporter folderExporter;

    public ExportServiceV0Imp(
            DSLContext dslContext,
            ExportDatasourceConfiguration.TenantHandlingExportConnectionProvider connectionProvider) {
        this.dslContext = dslContext;
        this.ehrSchemaDataExporter = new EhrSchemaDataExporter(dslContext);
        this.ehrExporter = new EhrExporter(dslContext);
        this.compositionExporter = new CompositionExporter(dslContext);
        this.ehrStatusExporter = new EhrStatusExporter(dslContext);
        this.folderExporter = new FolderExporter(dslContext);

        this.contributionExporter = new ContributionExporter(dslContext);
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<EhrSchema> getTenants() {
        return dslContext
                .selectFrom(org.ehrbase.migration.exporter.v0.jooq.pg.tables.Tenant.TENANT)
                .fetch()
                .map(ExportServiceV0Imp::to);
    }

    private static EhrSchema to(TenantRecord record) {

        return new EhrSchema(
                (int) record.getId(),
                Optional.ofNullable(record.getTenantProperties())
                        .map(JSON::data)
                        .orElse(null),
                record.getTenantName(),
                record.getTenantId());
    }

    @Override
    public void setCurrentTenant(EhrSchema ehrSchema) {
        connectionProvider.setTenant(ehrSchema);
    }

    @Override
    public int getEhrCount() {
        return dslContext.fetchCount(Ehr.EHR_);
    }

    @Override
    public List<ExtendedEhr> findEhrs(int start, int count) {
        return ehrExporter.findEhrs(start, count);
    }

    @Override
    public Map<UUID, List<VersionedObjectData<Composition>>> findCompositions(
            Collection<UUID> ehrIds, EhrSchema tenant) {
        return compositionExporter.findCompositions(ehrIds, tenant);
    }

    @Override
    public Map<UUID, List<VersionedObjectData<EhrStatus>>> findStatus(Collection<UUID> ehrIds, EhrSchema tenant) {
        return ehrStatusExporter.findStatus(ehrIds, tenant);
    }

    @Override
    public Map<UUID, List<VersionedObjectData<Folder>>> findFolder(Collection<UUID> uuids, EhrSchema tenant) {
        return folderExporter.findFolders(uuids, tenant);
    }

    @Override
    public Map<UUID, List<Contribution>> findContributions(Collection<UUID> ehrIds) {
        return contributionExporter.findContributions(ehrIds);
    }

    @Override
    public EhrSchemaData findEhrSchemaData() {
        return ehrSchemaDataExporter.getEhrSchemaData();
    }

    @Override
    public void postProcess(ExtendedEhr ehr) {
        // we need to add the contribution version by reverse mapping
        Map<HierObjectId, Contribution> idToContribution =
                ehr.getContributions().stream().collect(Collectors.toMap(Contribution::getUid, Function.identity()));
        Function<Version<?>, Contribution> contributionByVersion = v -> {
            ObjectId contributionId = v.getContribution().getId();
            if (contributionId instanceof HierObjectId hid) {
                Contribution contribution = idToContribution.get(hid);
                if (contribution == null) {
                    throw new IllegalArgumentException("Missing composition %s".formatted(hid));
                } else {
                    return contribution;
                }
            } else {
                throw new IllegalArgumentException("Unexpected contribution id type: %s".formatted(contributionId));
            }
        };

        Optional.ofNullable(ehr.getCompositions()).orElse(List.of()).stream()
                .map(VersionedObjectData::originalVersions)
                .flatMap(Collection::stream)
                .forEach(v -> ContributionExporter.addVersionRef(contributionByVersion.apply(v), v, "COMPOSITION"));

        ehr.getEhrStatus()
                .originalVersions()
                .forEach(v -> ContributionExporter.addVersionRef(contributionByVersion.apply(v), v, "EHR_STATUS"));

        Optional.ofNullable(ehr.getFolders()).orElse(List.of()).stream()
                .map(VersionedObjectData::originalVersions)
                .flatMap(Collection::stream)
                .forEach(v -> ContributionExporter.addVersionRef(contributionByVersion.apply(v), v, "FOLDER"));
    }
}
