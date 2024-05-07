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
package org.ehrbase.migration.importer.v4;

import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.COMP_DATA;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.COMP_DATA_HISTORY;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.COMP_VERSION;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.COMP_VERSION_HISTORY;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_FOLDER_DATA;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_FOLDER_DATA_HISTORY;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_FOLDER_VERSION;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_FOLDER_VERSION_HISTORY;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_STATUS_DATA;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_STATUS_DATA_HISTORY;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_STATUS_VERSION;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.EHR_STATUS_VERSION_HISTORY;

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.jooq.DSLContext;

public class EhrSchemaImporter {

    private final EhrSchemaDataImporter ehrSchemaDataImporter;

    private final EhrImporter ehrImporter;
    private final ContributionImporter contributionImporter;
    private final LocatableImporter locatableImporter;

    public EhrSchemaImporter(DSLContext dslContext) {
        this.ehrSchemaDataImporter = new EhrSchemaDataImporter(dslContext);
        this.ehrImporter = new EhrImporter(dslContext);
        this.contributionImporter = new ContributionImporter(dslContext);
        this.locatableImporter = new LocatableImporter(dslContext);
    }

    public void writeEhrSchemaData(EhrSchemaData ehrSchemaData) {
        ehrSchemaDataImporter.write(ehrSchemaData);
    }

    public void writeEhr(List<ExtendedEhr> ehrs) {
        ehrImporter.writeEhr(ehrs);
    }

    public void writeContribution(Map<UUID, List<Contribution>> contributions, EhrSchemaData ehrSchemaData) {
        contributionImporter.write(contributions, ehrSchemaData);
    }

    public void writeStatus(ImportService.VersionedObjectByEhr<EhrStatus> statusMap, EhrSchemaData ehrSchemaData) {

        locatableImporter.write(
                statusMap,
                AuditDetailsTargetType.EHR_STATUS,
                EHR_STATUS_VERSION,
                EHR_STATUS_DATA,
                EHR_STATUS_VERSION_HISTORY,
                EHR_STATUS_DATA_HISTORY,
                ehrSchemaData,
                null);
    }

    public void writeEhrFolder(ImportService.VersionedObjectByEhr<Folder> statusMap, EhrSchemaData ehrSchemaData) {
        locatableImporter.write(
                statusMap,
                AuditDetailsTargetType.EHR_FOLDER,
                EHR_FOLDER_VERSION,
                EHR_FOLDER_DATA,
                EHR_FOLDER_VERSION_HISTORY,
                EHR_FOLDER_DATA_HISTORY,
                ehrSchemaData,
                null);
    }

    public void writeComposition(
            ImportService.VersionedObjectByEhr<Composition> statusMap,
            EhrSchemaData ehrSchemaData,
            Function<String, UUID> templateIdToUuid) {
        locatableImporter.write(
                statusMap,
                AuditDetailsTargetType.COMPOSITION,
                COMP_VERSION,
                COMP_DATA,
                COMP_VERSION_HISTORY,
                COMP_DATA_HISTORY,
                ehrSchemaData,
                templateIdToUuid);
    }
}
