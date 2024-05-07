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
package org.ehrbase.migration.service;

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.ExportService;
import org.ehrbase.migration.exporter.postprocessor.ExportPostprocessor;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.importer.v4.EhrSchemaImporter;
import org.ehrbase.migration.importer.v4.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExportService exportService;

    private final ImportService importService;

    private final FileService fileService;

    @Value("${export.batch-size}")
    private Integer batchSize;

    private final List<ExportPostprocessor> exportPostprocessors;

    private final List<ExportPostprocessor.MigrationError> allErrors = new ArrayList<>();

    public ProcessService(
            @Autowired(required = false) ExportService exportService,
            ImportService importService,
            FileService fileService,
            List<ExportPostprocessor> exportPostprocessors) {
        this.exportService = exportService;
        this.importService = importService;
        this.fileService = fileService;
        this.exportPostprocessors = exportPostprocessors;
    }

    public void exportToFile() {
        if (exportService == null) {
            throw new IllegalStateException("Export Service is not available");
        }

        List<EhrSchema> tenants = exportService.getTenants();
        tenants.forEach(t -> {
            logger.info("Start export for tenant {} ", t.tenantId());
            exportService.setCurrentTenant(t);

            int ehrCount = exportService.getEhrCount();

            EhrSchemaData ehrSchemaData = exportService.findEhrSchemaData();
            fileService.write(ehrSchemaData, t);

            streamEhrBatches(t, ehrSchemaData, ehrCount).forEach(p -> {
                fileService.write(p, t);

                logger.info("Finished ehrs {} of {} ", p.batch(), ehrCount);
            });
        });

        fileService.write(tenants);

        logErrors();
    }

    private void logErrors() {

        allErrors.forEach(e -> {
            logger.warn(
                    "Error: {}; on tenantId: {}; on ehrId {} ; on voId {}",
                    e.message(),
                    e.tenantId(),
                    e.ehrId(),
                    e.voId());
        });
    }

    record EhrBatch(Batch batch, List<ExtendedEhr> ehrs) {}

    public void db2db() {
        if (exportService == null) {
            throw new IllegalStateException("Export Service is not available");
        }

        List<EhrSchema> tenants = exportService.getTenants();
        tenants.forEach(t -> {
            logger.info("Start migration of tenant {} ", t.tenantId());
            exportService.setCurrentTenant(t);

            int ehrCount = exportService.getEhrCount();

            EhrSchemaData ehrSchemaData = exportService.findEhrSchemaData();

            EhrSchemaImporter importer = importService.createImporter(t, Ehr.EHR.getName());
            importer.writeEhrSchemaData(ehrSchemaData);

            streamEhrBatches(t, ehrSchemaData, ehrCount).forEach(p -> {
                logger.info("Start import of ehrs {} of {} ", p.batch(), ehrCount);
                writeData(importer, p.ehrs(), ehrSchemaData);
                logger.info("Finished import of ehrs {} of {} ", p.batch(), ehrCount);
            });
            logger.info("Finished migration of tenant {} ", t.tenantId());
        });

        logErrors();
    }

    private Stream<EhrBatch> streamEhrBatches(EhrSchema t, EhrSchemaData ehrSchemaData, int ehrCount) {
        return Batch.stream(batchSize, ehrCount)
                .map(p1 -> {
                    logger.info("Processing ehrs {} of {} ", p1, ehrCount);
                    return p1;
                })
                .map(b -> new EhrBatch(b, exportService.findEhrs(b.start(), b.size())))
                .map(eb -> findData(t, eb))
                .map(eb -> {
                    exportPostprocessors.forEach(pp -> eb.ehrs().forEach(ehr -> {
                        List<ExportPostprocessor.MigrationError> migrationErrors = pp.postExport(ehr, ehrSchemaData, t);

                        migrationErrors.forEach(e -> allErrors.add(new ExportPostprocessor.MigrationError(
                                t.tenantId(), e.ehrId(), e.voId(), e.message())));
                    }));
                    return eb;
                });
    }

    public void importFromFile() {

        fileService.readTenants().forEach(t -> {
            logger.info("Start import for tenant {} ", t.tenantId());
            EhrSchemaData ehrSchemaData = fileService.readEhrSchemaData(t);
            EhrSchemaImporter importer = importService.createImporter(t, Ehr.EHR.getName());
            fileService.readEhrFileNames(t).parallelStream()
                    .map(f -> {
                        logger.info("Import %s".formatted(f));
                        return f;
                    })
                    .map(f -> fileService.readEhrs(f, t))
                    .forEach(extendedEhrs -> writeData(importer, extendedEhrs, ehrSchemaData));
        });
        logErrors();
    }

    private void writeData(
            EhrSchemaImporter ehrSchemaDataImporter, List<ExtendedEhr> extendedEhrs, EhrSchemaData ehrSchemaData) {

        ehrSchemaDataImporter.writeEhr(extendedEhrs);

        List<ExtendedEhr> subEhrs = extendedEhrs;

        ehrSchemaDataImporter.writeContribution(
                subEhrs.stream().collect(Collectors.toMap(ExtendedEhr::getEhrId, ExtendedEhr::getContributions)),
                ehrSchemaData);
        ehrSchemaDataImporter.writeStatus(
                new ImportService.VersionedObjectByEhr<>(subEhrs, ee -> List.of(ee.getEhrStatus())), ehrSchemaData);
        ehrSchemaDataImporter.writeEhrFolder(
                new ImportService.VersionedObjectByEhr<>(subEhrs, ExtendedEhr::getFolders), ehrSchemaData);
        ehrSchemaDataImporter.writeComposition(
                new ImportService.VersionedObjectByEhr<>(subEhrs, ExtendedEhr::getCompositions),
                ehrSchemaData,
                id -> ehrSchemaData.getTemplateUuid(ehrSchemaData.getTemplateByTemplateId(id)));
    }

    private EhrBatch findData(EhrSchema t, EhrBatch eb) {

        Map<UUID, ExtendedEhr> byEhrId =
                eb.ehrs().stream().collect(Collectors.toMap(ExtendedEhr::getEhrId, Function.identity()));

        Map<UUID, List<VersionedObjectData<Composition>>> compositions =
                exportService.findCompositions(byEhrId.keySet(), t);

        compositions.forEach((key, value) -> byEhrId.get(key).setCompositions(value));

        Map<UUID, List<VersionedObjectData<EhrStatus>>> status = exportService.findStatus(byEhrId.keySet(), t);
        status.forEach((k, v) -> byEhrId.get(k).setEhrStatus(v.getFirst()));

        Map<UUID, List<VersionedObjectData<Folder>>> foldersByEhr = exportService.findFolder(byEhrId.keySet(), t);

        foldersByEhr.forEach((eid, fs) -> {
            byEhrId.get(eid).setFolders(fs);
            // There is one folder, which is the directory
            byEhrId.get(eid).setDirectory(fs.getFirst().uuid());
        });

        Map<UUID, List<Contribution>> contributions = exportService.findContributions(byEhrId.keySet());

        contributions.forEach((k, v) -> byEhrId.get(k).setContributions(v));

        byEhrId.values().forEach(exportService::postProcess);

        return eb;
    }
}
