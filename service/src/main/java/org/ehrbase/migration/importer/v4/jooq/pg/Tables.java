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

/**
 * Convenience access to all tables in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Tables {

    /**
     * The table <code>ehr.audit_details</code>.
     */
    public static final AuditDetails AUDIT_DETAILS = AuditDetails.AUDIT_DETAILS;

    /**
     * The table <code>ehr.comp_data</code>.
     */
    public static final CompData COMP_DATA = CompData.COMP_DATA;

    /**
     * The table <code>ehr.comp_data_history</code>.
     */
    public static final CompDataHistory COMP_DATA_HISTORY = CompDataHistory.COMP_DATA_HISTORY;

    /**
     * The table <code>ehr.comp_version</code>.
     */
    public static final CompVersion COMP_VERSION = CompVersion.COMP_VERSION;

    /**
     * The table <code>ehr.comp_version_history</code>.
     */
    public static final CompVersionHistory COMP_VERSION_HISTORY = CompVersionHistory.COMP_VERSION_HISTORY;

    /**
     * The table <code>ehr.contribution</code>.
     */
    public static final Contribution CONTRIBUTION = Contribution.CONTRIBUTION;

    /**
     * The table <code>ehr.ehr</code>.
     */
    public static final org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr EHR_ =
            org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr.EHR_;

    /**
     * The table <code>ehr.ehr_folder_data</code>.
     */
    public static final EhrFolderData EHR_FOLDER_DATA = EhrFolderData.EHR_FOLDER_DATA;

    /**
     * The table <code>ehr.ehr_folder_data_history</code>.
     */
    public static final EhrFolderDataHistory EHR_FOLDER_DATA_HISTORY = EhrFolderDataHistory.EHR_FOLDER_DATA_HISTORY;

    /**
     * The table <code>ehr.ehr_folder_version</code>.
     */
    public static final EhrFolderVersion EHR_FOLDER_VERSION = EhrFolderVersion.EHR_FOLDER_VERSION;

    /**
     * The table <code>ehr.ehr_folder_version_history</code>.
     */
    public static final EhrFolderVersionHistory EHR_FOLDER_VERSION_HISTORY =
            EhrFolderVersionHistory.EHR_FOLDER_VERSION_HISTORY;

    /**
     * The table <code>ehr.ehr_status_data</code>.
     */
    public static final EhrStatusData EHR_STATUS_DATA = EhrStatusData.EHR_STATUS_DATA;

    /**
     * The table <code>ehr.ehr_status_data_history</code>.
     */
    public static final EhrStatusDataHistory EHR_STATUS_DATA_HISTORY = EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY;

    /**
     * The table <code>ehr.ehr_status_version</code>.
     */
    public static final EhrStatusVersion EHR_STATUS_VERSION = EhrStatusVersion.EHR_STATUS_VERSION;

    /**
     * The table <code>ehr.ehr_status_version_history</code>.
     */
    public static final EhrStatusVersionHistory EHR_STATUS_VERSION_HISTORY =
            EhrStatusVersionHistory.EHR_STATUS_VERSION_HISTORY;

    /**
     * The table <code>ehr.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * key value store for plugin sub system
     */
    public static final Plugin PLUGIN = Plugin.PLUGIN;

    /**
     * The table <code>ehr.stored_query</code>.
     */
    public static final StoredQuery STORED_QUERY = StoredQuery.STORED_QUERY;

    /**
     * The table <code>ehr.template_store</code>.
     */
    public static final TemplateStore TEMPLATE_STORE = TemplateStore.TEMPLATE_STORE;

    /**
     * The table <code>ehr.users</code>.
     */
    public static final Users USERS = Users.USERS;
}
