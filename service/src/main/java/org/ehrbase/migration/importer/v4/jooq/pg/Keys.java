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
package org.ehrbase.migration.importer.v4.jooq.pg;

import org.ehrbase.migration.importer.v4.jooq.pg.tables.AuditDetails;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompData;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompDataHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompVersionHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Contribution;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrFolderData;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrFolderDataHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrFolderVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrFolderVersionHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusData;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusDataHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusVersionHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.FlywaySchemaHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Plugin;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.StoredQuery;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.TemplateStore;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Users;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.CompDataHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.CompDataRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.CompVersionHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.CompVersionRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.ContributionRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrFolderDataHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrFolderDataRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrFolderVersionHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrFolderVersionRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrStatusDataHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrStatusDataRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrStatusVersionHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrStatusVersionRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.FlywaySchemaHistoryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.PluginRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.StoredQueryRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.TemplateStoreRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.UsersRecord;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AuditDetailsRecord> AUDIT_DETAILS_PKEY = Internal.createUniqueKey(
            AuditDetails.AUDIT_DETAILS,
            DSL.name("audit_details_pkey"),
            new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
            true);
    public static final UniqueKey<CompDataRecord> COMP_PKEY = Internal.createUniqueKey(
            CompData.COMP_DATA,
            DSL.name("comp_pkey"),
            new TableField[] {CompData.COMP_DATA.VO_ID, CompData.COMP_DATA.NUM},
            true);
    public static final UniqueKey<CompDataHistoryRecord> COMP_HISTORY_PKEY = Internal.createUniqueKey(
            CompDataHistory.COMP_DATA_HISTORY,
            DSL.name("comp_history_pkey"),
            new TableField[] {
                CompDataHistory.COMP_DATA_HISTORY.VO_ID,
                CompDataHistory.COMP_DATA_HISTORY.NUM,
                CompDataHistory.COMP_DATA_HISTORY.SYS_VERSION
            },
            true);
    public static final UniqueKey<CompVersionRecord> COMP_VERSION_PKEY = Internal.createUniqueKey(
            CompVersion.COMP_VERSION,
            DSL.name("comp_version_pkey"),
            new TableField[] {CompVersion.COMP_VERSION.VO_ID},
            true);
    public static final UniqueKey<CompVersionHistoryRecord> COMP_VERSION_HISTORY_PKEY = Internal.createUniqueKey(
            CompVersionHistory.COMP_VERSION_HISTORY,
            DSL.name("comp_version_history_pkey"),
            new TableField[] {
                CompVersionHistory.COMP_VERSION_HISTORY.VO_ID, CompVersionHistory.COMP_VERSION_HISTORY.SYS_VERSION
            },
            true);
    public static final UniqueKey<ContributionRecord> CONTRIBUTION_PKEY = Internal.createUniqueKey(
            Contribution.CONTRIBUTION,
            DSL.name("contribution_pkey"),
            new TableField[] {Contribution.CONTRIBUTION.ID},
            true);
    public static final UniqueKey<EhrRecord> EHR_PKEY =
            Internal.createUniqueKey(Ehr.EHR_, DSL.name("ehr_pkey"), new TableField[] {Ehr.EHR_.ID}, true);
    public static final UniqueKey<EhrFolderDataRecord> EHR_FOLDER_PKEY = Internal.createUniqueKey(
            EhrFolderData.EHR_FOLDER_DATA,
            DSL.name("ehr_folder_pkey"),
            new TableField[] {
                EhrFolderData.EHR_FOLDER_DATA.EHR_ID,
                EhrFolderData.EHR_FOLDER_DATA.EHR_FOLDERS_IDX,
                EhrFolderData.EHR_FOLDER_DATA.NUM
            },
            true);
    public static final UniqueKey<EhrFolderDataHistoryRecord> EHR_FOLDER_HISTORY_PKEY = Internal.createUniqueKey(
            EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY,
            DSL.name("ehr_folder_history_pkey"),
            new TableField[] {
                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_ID,
                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_FOLDERS_IDX,
                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.NUM,
                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.SYS_VERSION
            },
            true);
    public static final UniqueKey<EhrFolderVersionRecord> EHR_FOLDER_VERSION_PKEY = Internal.createUniqueKey(
            EhrFolderVersion.EHR_FOLDER_VERSION,
            DSL.name("ehr_folder_version_pkey"),
            new TableField[] {
                EhrFolderVersion.EHR_FOLDER_VERSION.EHR_ID, EhrFolderVersion.EHR_FOLDER_VERSION.EHR_FOLDERS_IDX
            },
            true);
    public static final UniqueKey<EhrFolderVersionHistoryRecord> EHR_FOLDER_VERSION_HISTORY_PKEY =
            Internal.createUniqueKey(
                    EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY,
                    DSL.name("ehr_folder_version_history_pkey"),
                    new TableField[] {
                        EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.EHR_ID,
                        EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.EHR_FOLDERS_IDX,
                        EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.SYS_VERSION
                    },
                    true);
    public static final UniqueKey<EhrStatusDataRecord> EHR_STATUS_PKEY = Internal.createUniqueKey(
            EhrStatusData.EHR_STATUS_DATA,
            DSL.name("ehr_status_pkey"),
            new TableField[] {EhrStatusData.EHR_STATUS_DATA.EHR_ID, EhrStatusData.EHR_STATUS_DATA.NUM},
            true);
    public static final UniqueKey<EhrStatusDataHistoryRecord> EHR_STATUS_HISTORY_PKEY = Internal.createUniqueKey(
            EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY,
            DSL.name("ehr_status_history_pkey"),
            new TableField[] {
                EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.EHR_ID,
                EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.NUM,
                EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.SYS_VERSION
            },
            true);
    public static final UniqueKey<EhrStatusVersionRecord> EHR_STATUS_VERSION_PKEY = Internal.createUniqueKey(
            EhrStatusVersion.EHR_STATUS_VERSION,
            DSL.name("ehr_status_version_pkey"),
            new TableField[] {EhrStatusVersion.EHR_STATUS_VERSION.EHR_ID},
            true);
    public static final UniqueKey<EhrStatusVersionHistoryRecord> EHR_STATUS_VERSION_HISTORY_PKEY =
            Internal.createUniqueKey(
                    EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY,
                    DSL.name("ehr_status_version_history_pkey"),
                    new TableField[] {
                        EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.EHR_ID,
                        EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.SYS_VERSION
                    },
                    true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            DSL.name("flyway_schema_history_pk"),
            new TableField[] {FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK},
            true);
    public static final UniqueKey<PluginRecord> PLUGIN_PKEY =
            Internal.createUniqueKey(Plugin.PLUGIN, DSL.name("plugin_pkey"), new TableField[] {Plugin.PLUGIN.ID}, true);
    public static final UniqueKey<StoredQueryRecord> STORED_QUERY_PKEY = Internal.createUniqueKey(
            StoredQuery.STORED_QUERY,
            DSL.name("stored_query_pkey"),
            new TableField[] {
                StoredQuery.STORED_QUERY.REVERSE_DOMAIN_NAME,
                StoredQuery.STORED_QUERY.SEMANTIC_ID,
                StoredQuery.STORED_QUERY.SEMVER
            },
            true);
    public static final UniqueKey<TemplateStoreRecord> TEMPLATE_STORE_PKEY = Internal.createUniqueKey(
            TemplateStore.TEMPLATE_STORE,
            DSL.name("template_store_pkey"),
            new TableField[] {TemplateStore.TEMPLATE_STORE.ID},
            true);
    public static final UniqueKey<UsersRecord> USERS_PKEY =
            Internal.createUniqueKey(Users.USERS, DSL.name("users_pkey"), new TableField[] {Users.USERS.ID}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AuditDetailsRecord, UsersRecord> AUDIT_DETAILS__AUDIT_DETAILS_USER_ID_FKEY =
            Internal.createForeignKey(
                    AuditDetails.AUDIT_DETAILS,
                    DSL.name("audit_details_user_id_fkey"),
                    new TableField[] {AuditDetails.AUDIT_DETAILS.USER_ID},
                    Keys.USERS_PKEY,
                    new TableField[] {Users.USERS.ID},
                    true);
    public static final ForeignKey<CompDataRecord, CompVersionRecord> COMP_DATA__COMP_DATA_VO_ID_FKEY =
            Internal.createForeignKey(
                    CompData.COMP_DATA,
                    DSL.name("comp_data_vo_id_fkey"),
                    new TableField[] {CompData.COMP_DATA.VO_ID},
                    Keys.COMP_VERSION_PKEY,
                    new TableField[] {CompVersion.COMP_VERSION.VO_ID},
                    true);
    public static final ForeignKey<CompDataHistoryRecord, CompVersionHistoryRecord>
            COMP_DATA_HISTORY__COMP_DATA_HISTORY_VO_ID_SYS_VERSION_FKEY = Internal.createForeignKey(
                    CompDataHistory.COMP_DATA_HISTORY,
                    DSL.name("comp_data_history_vo_id_sys_version_fkey"),
                    new TableField[] {
                        CompDataHistory.COMP_DATA_HISTORY.VO_ID, CompDataHistory.COMP_DATA_HISTORY.SYS_VERSION
                    },
                    Keys.COMP_VERSION_HISTORY_PKEY,
                    new TableField[] {
                        CompVersionHistory.COMP_VERSION_HISTORY.VO_ID,
                        CompVersionHistory.COMP_VERSION_HISTORY.SYS_VERSION
                    },
                    true);
    public static final ForeignKey<CompVersionRecord, AuditDetailsRecord> COMP_VERSION__COMP_VERSION_AUDIT_ID_FKEY =
            Internal.createForeignKey(
                    CompVersion.COMP_VERSION,
                    DSL.name("comp_version_audit_id_fkey"),
                    new TableField[] {CompVersion.COMP_VERSION.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<CompVersionRecord, ContributionRecord>
            COMP_VERSION__COMP_VERSION_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    CompVersion.COMP_VERSION,
                    DSL.name("comp_version_contribution_id_fkey"),
                    new TableField[] {CompVersion.COMP_VERSION.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<CompVersionRecord, EhrRecord> COMP_VERSION__COMP_VERSION_EHR_ID_FKEY =
            Internal.createForeignKey(
                    CompVersion.COMP_VERSION,
                    DSL.name("comp_version_ehr_id_fkey"),
                    new TableField[] {CompVersion.COMP_VERSION.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<CompVersionRecord, TemplateStoreRecord> COMP_VERSION__COMP_VERSION_TEMPLATE_ID_FKEY =
            Internal.createForeignKey(
                    CompVersion.COMP_VERSION,
                    DSL.name("comp_version_template_id_fkey"),
                    new TableField[] {CompVersion.COMP_VERSION.TEMPLATE_ID},
                    Keys.TEMPLATE_STORE_PKEY,
                    new TableField[] {TemplateStore.TEMPLATE_STORE.ID},
                    true);
    public static final ForeignKey<CompVersionHistoryRecord, AuditDetailsRecord>
            COMP_VERSION_HISTORY__COMP_VERSION_HISTORY_AUDIT_ID_FKEY = Internal.createForeignKey(
                    CompVersionHistory.COMP_VERSION_HISTORY,
                    DSL.name("comp_version_history_audit_id_fkey"),
                    new TableField[] {CompVersionHistory.COMP_VERSION_HISTORY.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<CompVersionHistoryRecord, ContributionRecord>
            COMP_VERSION_HISTORY__COMP_VERSION_HISTORY_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    CompVersionHistory.COMP_VERSION_HISTORY,
                    DSL.name("comp_version_history_contribution_id_fkey"),
                    new TableField[] {CompVersionHistory.COMP_VERSION_HISTORY.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<CompVersionHistoryRecord, EhrRecord>
            COMP_VERSION_HISTORY__COMP_VERSION_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    CompVersionHistory.COMP_VERSION_HISTORY,
                    DSL.name("comp_version_history_ehr_id_fkey"),
                    new TableField[] {CompVersionHistory.COMP_VERSION_HISTORY.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<CompVersionHistoryRecord, TemplateStoreRecord>
            COMP_VERSION_HISTORY__COMP_VERSION_HISTORY_TEMPLATE_ID_FKEY = Internal.createForeignKey(
                    CompVersionHistory.COMP_VERSION_HISTORY,
                    DSL.name("comp_version_history_template_id_fkey"),
                    new TableField[] {CompVersionHistory.COMP_VERSION_HISTORY.TEMPLATE_ID},
                    Keys.TEMPLATE_STORE_PKEY,
                    new TableField[] {TemplateStore.TEMPLATE_STORE.ID},
                    true);
    public static final ForeignKey<ContributionRecord, EhrRecord> CONTRIBUTION__CONTRIBUTION_EHR_ID_FKEY =
            Internal.createForeignKey(
                    Contribution.CONTRIBUTION,
                    DSL.name("contribution_ehr_id_fkey"),
                    new TableField[] {Contribution.CONTRIBUTION.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<ContributionRecord, AuditDetailsRecord> CONTRIBUTION__CONTRIBUTION_HAS_AUDIT_FKEY =
            Internal.createForeignKey(
                    Contribution.CONTRIBUTION,
                    DSL.name("contribution_has_audit_fkey"),
                    new TableField[] {Contribution.CONTRIBUTION.HAS_AUDIT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<EhrFolderDataRecord, EhrFolderVersionRecord>
            EHR_FOLDER_DATA__EHR_FOLDER_DATA_EHR_ID_EHR_FOLDERS_IDX_FKEY = Internal.createForeignKey(
                    EhrFolderData.EHR_FOLDER_DATA,
                    DSL.name("ehr_folder_data_ehr_id_ehr_folders_idx_fkey"),
                    new TableField[] {
                        EhrFolderData.EHR_FOLDER_DATA.EHR_ID, EhrFolderData.EHR_FOLDER_DATA.EHR_FOLDERS_IDX
                    },
                    Keys.EHR_FOLDER_VERSION_PKEY,
                    new TableField[] {
                        EhrFolderVersion.EHR_FOLDER_VERSION.EHR_ID, EhrFolderVersion.EHR_FOLDER_VERSION.EHR_FOLDERS_IDX
                    },
                    true);
    public static final ForeignKey<EhrFolderDataRecord, EhrRecord> EHR_FOLDER_DATA__EHR_FOLDER_EHR_ID_FKEY =
            Internal.createForeignKey(
                    EhrFolderData.EHR_FOLDER_DATA,
                    DSL.name("ehr_folder_ehr_id_fkey"),
                    new TableField[] {EhrFolderData.EHR_FOLDER_DATA.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrFolderDataHistoryRecord, EhrFolderVersionHistoryRecord>
            EHR_FOLDER_DATA_HISTORY__EHR_FOLDER_DATA_HISTORY_EHR_ID_EHR_FOLDERS_IDX_SYS_VERSION_FKEY =
                    Internal.createForeignKey(
                            EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY,
                            DSL.name("ehr_folder_data_history_ehr_id_ehr_folders_idx_sys_version_fkey"),
                            new TableField[] {
                                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_ID,
                                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_FOLDERS_IDX,
                                EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.SYS_VERSION
                            },
                            Keys.EHR_FOLDER_VERSION_HISTORY_PKEY,
                            new TableField[] {
                                EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.EHR_ID,
                                EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.EHR_FOLDERS_IDX,
                                EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.SYS_VERSION
                            },
                            true);
    public static final ForeignKey<EhrFolderDataHistoryRecord, EhrRecord>
            EHR_FOLDER_DATA_HISTORY__EHR_FOLDER_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY,
                    DSL.name("ehr_folder_history_ehr_id_fkey"),
                    new TableField[] {EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionRecord, AuditDetailsRecord>
            EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_AUDIT_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersion.EHR_FOLDER_VERSION,
                    DSL.name("ehr_folder_version_audit_id_fkey"),
                    new TableField[] {EhrFolderVersion.EHR_FOLDER_VERSION.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionRecord, ContributionRecord>
            EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersion.EHR_FOLDER_VERSION,
                    DSL.name("ehr_folder_version_contribution_id_fkey"),
                    new TableField[] {EhrFolderVersion.EHR_FOLDER_VERSION.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionRecord, EhrRecord>
            EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersion.EHR_FOLDER_VERSION,
                    DSL.name("ehr_folder_version_ehr_id_fkey"),
                    new TableField[] {EhrFolderVersion.EHR_FOLDER_VERSION.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionHistoryRecord, AuditDetailsRecord>
            EHR_FOLDER_VERSION_HISTORY__EHR_FOLDER_VERSION_HISTORY_AUDIT_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY,
                    DSL.name("ehr_folder_version_history_audit_id_fkey"),
                    new TableField[] {EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionHistoryRecord, ContributionRecord>
            EHR_FOLDER_VERSION_HISTORY__EHR_FOLDER_VERSION_HISTORY_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY,
                    DSL.name("ehr_folder_version_history_contribution_id_fkey"),
                    new TableField[] {EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<EhrFolderVersionHistoryRecord, EhrRecord>
            EHR_FOLDER_VERSION_HISTORY__EHR_FOLDER_VERSION_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY,
                    DSL.name("ehr_folder_version_history_ehr_id_fkey"),
                    new TableField[] {EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrStatusDataRecord, EhrStatusVersionRecord>
            EHR_STATUS_DATA__EHR_STATUS_DATA_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrStatusData.EHR_STATUS_DATA,
                    DSL.name("ehr_status_data_ehr_id_fkey"),
                    new TableField[] {EhrStatusData.EHR_STATUS_DATA.EHR_ID},
                    Keys.EHR_STATUS_VERSION_PKEY,
                    new TableField[] {EhrStatusVersion.EHR_STATUS_VERSION.EHR_ID},
                    true);
    public static final ForeignKey<EhrStatusDataRecord, EhrRecord> EHR_STATUS_DATA__EHR_STATUS_EHR_ID_FKEY =
            Internal.createForeignKey(
                    EhrStatusData.EHR_STATUS_DATA,
                    DSL.name("ehr_status_ehr_id_fkey"),
                    new TableField[] {EhrStatusData.EHR_STATUS_DATA.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrStatusDataHistoryRecord, EhrStatusVersionHistoryRecord>
            EHR_STATUS_DATA_HISTORY__EHR_STATUS_DATA_HISTORY_EHR_ID_SYS_VERSION_FKEY = Internal.createForeignKey(
                    EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY,
                    DSL.name("ehr_status_data_history_ehr_id_sys_version_fkey"),
                    new TableField[] {
                        EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.EHR_ID,
                        EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.SYS_VERSION
                    },
                    Keys.EHR_STATUS_VERSION_HISTORY_PKEY,
                    new TableField[] {
                        EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.EHR_ID,
                        EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.SYS_VERSION
                    },
                    true);
    public static final ForeignKey<EhrStatusDataHistoryRecord, EhrRecord>
            EHR_STATUS_DATA_HISTORY__EHR_STATUS_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY,
                    DSL.name("ehr_status_history_ehr_id_fkey"),
                    new TableField[] {EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionRecord, AuditDetailsRecord>
            EHR_STATUS_VERSION__EHR_STATUS_VERSION_AUDIT_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersion.EHR_STATUS_VERSION,
                    DSL.name("ehr_status_version_audit_id_fkey"),
                    new TableField[] {EhrStatusVersion.EHR_STATUS_VERSION.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionRecord, ContributionRecord>
            EHR_STATUS_VERSION__EHR_STATUS_VERSION_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersion.EHR_STATUS_VERSION,
                    DSL.name("ehr_status_version_contribution_id_fkey"),
                    new TableField[] {EhrStatusVersion.EHR_STATUS_VERSION.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionRecord, EhrRecord>
            EHR_STATUS_VERSION__EHR_STATUS_VERSION_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersion.EHR_STATUS_VERSION,
                    DSL.name("ehr_status_version_ehr_id_fkey"),
                    new TableField[] {EhrStatusVersion.EHR_STATUS_VERSION.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionHistoryRecord, AuditDetailsRecord>
            EHR_STATUS_VERSION_HISTORY__EHR_STATUS_VERSION_HISTORY_AUDIT_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY,
                    DSL.name("ehr_status_version_history_audit_id_fkey"),
                    new TableField[] {EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.AUDIT_ID},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionHistoryRecord, ContributionRecord>
            EHR_STATUS_VERSION_HISTORY__EHR_STATUS_VERSION_HISTORY_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY,
                    DSL.name("ehr_status_version_history_contribution_id_fkey"),
                    new TableField[] {EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.CONTRIBUTION_ID},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID},
                    true);
    public static final ForeignKey<EhrStatusVersionHistoryRecord, EhrRecord>
            EHR_STATUS_VERSION_HISTORY__EHR_STATUS_VERSION_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY,
                    DSL.name("ehr_status_version_history_ehr_id_fkey"),
                    new TableField[] {EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY.EHR_ID},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID},
                    true);
}
