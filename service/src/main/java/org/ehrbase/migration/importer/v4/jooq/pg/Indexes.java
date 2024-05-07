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

import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompData;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Contribution;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusVersion;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.FlywaySchemaHistory;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.TemplateStore;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Users;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling indexes of tables in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index COMP_DATA_LEAF_IDX = Internal.createIndex(
            DSL.name("comp_data_leaf_idx"),
            CompData.COMP_DATA,
            new OrderField[] {CompData.COMP_DATA.VO_ID, CompData.COMP_DATA.ENTITY_IDX},
            false);
    public static final Index COMP_VERSION_SYS_PERIOD_LOWER_IDX = Internal.createIndex(
            DSL.name("comp_version_sys_period_lower_idx"),
            CompVersion.COMP_VERSION,
            new OrderField[] {CompVersion.COMP_VERSION.SYS_PERIOD_LOWER.desc(), CompVersion.COMP_VERSION.VO_ID},
            false);
    public static final Index CONTRIBUTION_EHR_IDX = Internal.createIndex(
            DSL.name("contribution_ehr_idx"),
            Contribution.CONTRIBUTION,
            new OrderField[] {Contribution.CONTRIBUTION.EHR_ID},
            false);
    public static final Index EHR_STATUS_VERSION_SYS_PERIOD_LOWER_IDX = Internal.createIndex(
            DSL.name("ehr_status_version_sys_period_lower_idx"),
            EhrStatusVersion.EHR_STATUS_VERSION,
            new OrderField[] {
                EhrStatusVersion.EHR_STATUS_VERSION.SYS_PERIOD_LOWER.desc(), EhrStatusVersion.EHR_STATUS_VERSION.EHR_ID
            },
            false);
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex(
            DSL.name("flyway_schema_history_s_idx"),
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            new OrderField[] {FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS},
            false);
    public static final Index TEMPLATE_STORE_ID_UNQ = Internal.createIndex(
            DSL.name("template_store_id_unq"),
            TemplateStore.TEMPLATE_STORE,
            new OrderField[] {TemplateStore.TEMPLATE_STORE.TEMPLATE_ID},
            true);
    public static final Index USERS_USERNAME_IDX = Internal.createIndex(
            DSL.name("users_username_idx"), Users.USERS, new OrderField[] {Users.USERS.USERNAME}, true);
}
