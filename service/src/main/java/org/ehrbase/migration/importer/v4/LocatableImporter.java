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

import static org.ehrbase.api.service.DirectoryService.EHR_DIRECTORY_FOLDER_IDX;
import static org.ehrbase.openehr.dbformat.jooq.prototypes.ObjectVersionHistoryTablePrototype.INSTANCE;

import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.changecontrol.Version;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.AuditExporter;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrFolderDataHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusData;
import org.ehrbase.openehr.aqlengine.asl.model.AslRmTypeAndConcept;
import org.ehrbase.openehr.dbformat.jooq.prototypes.ObjectDataRecordPrototype;
import org.ehrbase.repository.VersionDataDbRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;

public class LocatableImporter {

    private final DSLContext dslContext;
    private final AuditImporter auditImporter;

    public LocatableImporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        auditImporter = new AuditImporter(dslContext);
    }

    protected <L extends Locatable> void write(
            ImportService.VersionedObjectByEhr<L> versionedObjects,
            AuditDetailsTargetType targetType,
            Table versionHead,
            Table dataHead,
            Table versionHistory,
            Table dataHistory,
            EhrSchemaData ehrSchemaData,
            Function<String, UUID> templateIdToUuid) {

        var auditDetailsMap = versionedObjects
                .streamOriginalVersions()
                .map(Pair::getRight)
                .map(VersionedObjectData::originalVersions)
                .flatMap(Set::stream)
                .collect(Collectors.toMap(
                        OriginalVersion::getUid,
                        Version::getCommitAudit,
                        (a, b) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new));

        Map<ObjectVersionId, UUID> auditUuids = auditImporter.write(auditDetailsMap, targetType, ehrSchemaData);

        List<Record> versionList = new ArrayList<>();
        List<Record> versionHistoryList = new ArrayList<>();
        List<Record> dataList = new ArrayList<>();
        List<Record> dataHistoryList = new ArrayList<>();

        versionedObjects.streamOriginalVersions().forEach(ev -> {
            VersionedObjectRecords records = to(
                    ev.getRight().originalVersions(),
                    ev.getLeft(),
                    auditUuids,
                    versionHead,
                    dataHead,
                    versionHistory,
                    dataHistory,
                    templateIdToUuid);

            versionList.addAll(records.versionList);
            versionHistoryList.addAll(records.versionHistoryList);
            dataList.addAll(records.dataList);
            dataHistoryList.addAll(records.dataHistoryList);
        });

        ImportService.executeBulkInsert(dslContext, versionList.stream(), versionHead);
        ImportService.executeBulkInsert(dslContext, versionHistoryList.stream(), versionHistory);
        ImportService.executeBulkInsert(dslContext, dataList.stream(), dataHead);
        ImportService.executeBulkInsert(dslContext, dataHistoryList.stream(), dataHistory);
    }

    private record VersionedObjectRecords(
            List<Record> versionList,
            List<Record> versionHistoryList,
            List<Record> dataList,
            List<Record> dataHistoryList) {}

    protected <L extends Locatable> VersionedObjectRecords to(
            SortedSet<OriginalVersion<L>> statusVersions,
            UUID ehrId,
            Map<ObjectVersionId, UUID> auditUuids,
            Table versionHead,
            Table dataHead,
            Table versionHistory,
            Table dataHistory,
            Function<String, UUID> templateIdToUuid) {

        List<Record> versionList = new ArrayList<>();
        List<Record> versionHistoryList = new ArrayList<>();
        List<Record> dataList = new ArrayList<>();
        List<Record> dataHistoryList = new ArrayList<>();

        L initialData = statusVersions.getFirst().getData();

        Record lastroot = null;
        for (OriginalVersion<L> current : statusVersions.reversed()) {
            List<Record> nextData = new ArrayList<>();

            Record nextroot;

            String codeString =
                    current.getCommitAudit().getChangeType().getDefiningCode().getCodeString();
            if (codeString.equals("" + AuditExporter.ContributionChangeType.DELETED.getCode())) {

                var root = dslContext.newRecord(versionHistory);

                root.setValue(INSTANCE.EHR_ID, ehrId);
                root.setValue(
                        INSTANCE.VO_ID,
                        UUID.fromString(initialData.getUid().getRoot().getValue()));

                root.set(INSTANCE.SYS_DELETED, true);

                nextroot = root;

            } else {

                VersionDataDbRecord records = VersionDataDbRecord.toRecords(
                        ehrId,
                        current.getData(),
                        UUID.fromString(current.getContribution().getId().getValue()),
                        auditUuids.get(current.getUid()),
                        null,
                        dslContext);

                Stream<ObjectDataRecordPrototype> versionedObjectRecordPrototypes =
                        records.dataRecords().get();

                Table dataTable;
                Table versionTable;
                if (lastroot == null) {
                    dataTable = dataHead;
                    versionTable = versionHead;
                } else {
                    dataTable = dataHistory;
                    versionTable = versionHistory;
                }
                versionedObjectRecordPrototypes.forEach(r -> nextData.add(r.into(dataTable)));
                nextroot = records.versionRecord().into(versionTable);
            }

            OffsetDateTime from = OffsetDateTime.from(
                    current.getCommitAudit().getTimeCommitted().getValue());
            final OffsetDateTime to;

            if (lastroot != null) {

                to = lastroot.get(INSTANCE.SYS_PERIOD_LOWER);
            } else {
                to = null;
            }

            nextData.forEach(r -> {
                if (dataHistory.getRecordType().isInstance(r)) {
                    r.set(
                            INSTANCE.SYS_VERSION,
                            Integer.parseInt(current.getUid().getVersionTreeId().getValue()));
                }

                if (current.getData() instanceof Folder) {
                    r.setValue(EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_FOLDERS_IDX, EHR_DIRECTORY_FOLDER_IDX);
                    r.setValue(EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_ID, ehrId);
                }
                if (current.getData() instanceof EhrStatus) {
                    r.setValue(EhrStatusData.EHR_STATUS_DATA.EHR_ID, ehrId);
                }
            });

            nextroot.set(
                    INSTANCE.SYS_VERSION,
                    Integer.parseInt(current.getUid().getVersionTreeId().getValue()));
            nextroot.set(INSTANCE.AUDIT_ID, auditUuids.get(current.getUid()));
            nextroot.set(
                    INSTANCE.CONTRIBUTION_ID,
                    UUID.fromString(current.getContribution().getId().getValue()));
            nextroot.set(INSTANCE.SYS_PERIOD_LOWER, from);

            if (to != null) {
                nextroot.set(INSTANCE.SYS_PERIOD_UPPER, to);
                if (nextroot.getValue(INSTANCE.SYS_DELETED) == null) {
                    nextroot.setValue(INSTANCE.SYS_DELETED, false);
                }
            }

            if (initialData instanceof Composition composition) {
                String rootConcept = AslRmTypeAndConcept.toEntityConcept(composition.getArchetypeNodeId());
                nextroot.setValue(CompVersion.COMP_VERSION.ROOT_CONCEPT, rootConcept);
                nextroot.setValue(
                        CompVersion.COMP_VERSION.TEMPLATE_ID,
                        templateIdToUuid.apply(composition
                                .getArchetypeDetails()
                                .getTemplateId()
                                .getValue()));
            }

            if (current.getData() instanceof Folder) {
                nextroot.setValue(
                        EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_FOLDERS_IDX, EHR_DIRECTORY_FOLDER_IDX);
            }

            lastroot = nextroot;

            if (versionHistory.getRecordType().isInstance(nextroot)) {

                dataHistoryList.addAll(nextData);
                versionHistoryList.add(nextroot);
            } else {
                dataList.addAll(nextData);
                versionList.add(nextroot);
            }
        }

        return new VersionedObjectRecords(versionList, versionHistoryList, dataList, dataHistoryList);
    }
}
