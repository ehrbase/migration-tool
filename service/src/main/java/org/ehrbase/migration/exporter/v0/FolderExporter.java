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

import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.AuditDetails.AUDIT_DETAILS;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.EhrFolder.EHR_FOLDER;
import static org.ehrbase.migration.exporter.v0.jooq.pg.tables.EhrFolderHistory.EHR_FOLDER_HISTORY;

import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import com.nedap.archie.rm.support.identification.ObjectVersionId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EhrFolderHistoryRecord;
import org.ehrbase.openehr.sdk.serialisation.jsonencoding.CanonicalJson;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

class FolderExporter {

    private static final int EHR_DIRECTORY_FOLDER_IDX = 1;

    private final DSLContext dslContext;
    private final EhrSchemaDataExporter ehrSchemaDataExporter;

    private final PartyExporter partyExporter;

    public FolderExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
        ehrSchemaDataExporter = new EhrSchemaDataExporter(dslContext);
        this.partyExporter = new PartyExporter(dslContext);
    }

    protected Map<UUID, List<VersionedObjectData<Folder>>> findFolders(Collection<UUID> ehrIds, EhrSchema tenant) {

        var result = dslContext
                .select(EHR_FOLDER.fields())
                .select(
                        DSL.field("null").as(EHR_FOLDER_HISTORY.SYS_PERIOD_UPPER.getName()),
                        DSL.field("false").as(EHR_FOLDER_HISTORY.SYS_DELETED.getName()))
                .select(AUDIT_DETAILS.fields())
                .from(EHR_FOLDER)
                .join(AUDIT_DETAILS)
                .on(EHR_FOLDER.AUDIT_ID.eq(AUDIT_DETAILS.ID))
                .where(EHR_FOLDER.EHR_ID.in(ehrIds).and(EHR_FOLDER.EHR_FOLDERS_IDX.eq(EHR_DIRECTORY_FOLDER_IDX)))
                .unionAll(dslContext
                        .select(EHR_FOLDER_HISTORY.fields(ArrayUtils.addAll(
                                EHR_FOLDER.fields(),
                                EHR_FOLDER_HISTORY.SYS_PERIOD_UPPER,
                                EHR_FOLDER_HISTORY.SYS_DELETED)))
                        .select(AUDIT_DETAILS.fields())
                        .from(EHR_FOLDER_HISTORY)
                        .join(AUDIT_DETAILS)
                        .on(EHR_FOLDER_HISTORY.AUDIT_ID.eq(AUDIT_DETAILS.ID))
                        .where(EHR_FOLDER_HISTORY
                                .EHR_ID
                                .in(ehrIds)
                                .and(EHR_FOLDER_HISTORY.EHR_FOLDERS_IDX.eq(EHR_DIRECTORY_FOLDER_IDX))))
                .stream()
                .map(r -> Pair.of(
                        new ArrayList<>(List.of(r.into(EhrFolderHistoryRecord.class))),
                        r.into(AuditDetailsRecord.class)))
                // Folder is stored in multiple rows we need to reduce here
                .collect(Collectors.groupingBy(
                        p -> {
                            var f = p.getLeft().getFirst();
                            return Triple.of(f.getEhrId(), f.getEhrFoldersIdx(), f.getSysVersion());
                        },
                        Collectors.reducing((p1, p2) -> {
                            p1.getLeft().addAll(p2.getLeft());
                            return p1;
                        })));

        Map<UUID, List<VersionedObjectData<Folder>>> collect = result.values().stream()
                .map(Optional::orElseThrow)
                .map(p -> to(p.getLeft(), p.getRight(), tenant))
                .collect(CompositionExporter.groupingVersionedObjectsByEhrIdCollector());

        // we fetch and add parties as batch
        partyExporter.addParty(collect);
        return collect;
    }

    private Pair<UUID, OriginalVersion<Folder>> to(
            List<EhrFolderHistoryRecord> ehrFolderRecords, AuditDetailsRecord auditDetailsRecord, EhrSchema tenant) {

        OriginalVersion<Folder> originalVersion = new OriginalVersion<>();
        EhrFolderHistoryRecord root = ehrFolderRecords.stream()
                .filter(r -> r.getRowNum().equals(0))
                .findFirst()
                .orElseThrow();

        originalVersion.setUid(new ObjectVersionId(
                root.getId() + "::" + ehrSchemaDataExporter.getSystemId(tenant) + "::" + root.getSysVersion()));
        originalVersion.setCommitAudit(AuditExporter.to(auditDetailsRecord));

        // fix a bug where folder was audit was always set to creation
        if (BooleanUtils.isTrue(root.getSysDeleted())
                && originalVersion
                        .getCommitAudit()
                        .getChangeType()
                        .getDefiningCode()
                        .getCodeString()
                        .equals("" + AuditExporter.ContributionChangeType.CREATION.code)) {
            originalVersion
                    .getCommitAudit()
                    .setChangeType(AuditExporter.convert(AuditExporter.ContributionChangeType.DELETED));
        }
        // fix a bug where folder was audit was always set to creation
        if (root.getSysVersion() > 1
                && originalVersion
                        .getCommitAudit()
                        .getChangeType()
                        .getDefiningCode()
                        .getCodeString()
                        .equals("" + AuditExporter.ContributionChangeType.CREATION.code)) {
            originalVersion
                    .getCommitAudit()
                    .setChangeType(AuditExporter.convert(AuditExporter.ContributionChangeType.MODIFICATION));
        }

        if (!originalVersion
                .getCommitAudit()
                .getChangeType()
                .getDefiningCode()
                .getCodeString()
                .equals("" + AuditExporter.ContributionChangeType.DELETED.code)) {
            originalVersion.setData(from(ehrFolderRecords));
        }
        originalVersion.setContribution(
                new ObjectRef<>(new HierObjectId(root.getContributionId().toString()), "local", "CONTRIBUTION"));

        return Pair.of(root.getEhrId(), originalVersion);
    }

    private static Folder from(List<EhrFolderHistoryRecord> ehrFolderRecords) {

        Map<List<String>, EhrFolderHistoryRecord> byPathMap = ehrFolderRecords.stream()
                .collect(Collectors.toMap(
                        ehrFolderRecord ->
                                Arrays.stream(ehrFolderRecord.getPath()).toList(),
                        Function.identity()));

        return from(
                byPathMap.keySet().stream().filter(l -> l.size() == 1).findAny().orElseThrow(), byPathMap);
    }

    private static Folder from(List<String> path, Map<List<String>, EhrFolderHistoryRecord> byPathMap) {

        Folder folder =
                new CanonicalJson().unmarshal(byPathMap.get(path).getFields().data(), Folder.class);

        byPathMap.keySet().stream().filter(l -> l.size() == path.size() + 1).forEach(nextPath -> {
            Folder subFolder = from(
                    nextPath,
                    byPathMap.entrySet().stream()
                            .filter(e -> e.getKey().size() >= nextPath.size()
                                    && e.getKey().subList(0, nextPath.size()).equals(nextPath))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            folder.addFolder(subFolder);
        });

        return folder;
    }
}
