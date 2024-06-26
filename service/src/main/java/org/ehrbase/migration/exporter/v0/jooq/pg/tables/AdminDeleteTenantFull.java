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
package org.ehrbase.migration.exporter.v0.jooq.pg.tables;

import java.util.function.Function;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AdminDeleteTenantFullRecord;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Name;
import org.jooq.Records;
import org.jooq.Row1;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AdminDeleteTenantFull extends TableImpl<AdminDeleteTenantFullRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.admin_delete_tenant_full</code>
     */
    public static final AdminDeleteTenantFull ADMIN_DELETE_TENANT_FULL = new AdminDeleteTenantFull();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AdminDeleteTenantFullRecord> getRecordType() {
        return AdminDeleteTenantFullRecord.class;
    }

    /**
     * The column <code>ehr.admin_delete_tenant_full.deleted</code>.
     */
    public final TableField<AdminDeleteTenantFullRecord, Boolean> DELETED =
            createField(DSL.name("deleted"), SQLDataType.BOOLEAN, this, "");

    private AdminDeleteTenantFull(Name alias, Table<AdminDeleteTenantFullRecord> aliased) {
        this(alias, aliased, new Field[] {DSL.val(null, SQLDataType.SMALLINT)});
    }

    private AdminDeleteTenantFull(Name alias, Table<AdminDeleteTenantFullRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.function());
    }

    /**
     * Create an aliased <code>ehr.admin_delete_tenant_full</code> table
     * reference
     */
    public AdminDeleteTenantFull(String alias) {
        this(DSL.name(alias), ADMIN_DELETE_TENANT_FULL);
    }

    /**
     * Create an aliased <code>ehr.admin_delete_tenant_full</code> table
     * reference
     */
    public AdminDeleteTenantFull(Name alias) {
        this(alias, ADMIN_DELETE_TENANT_FULL);
    }

    /**
     * Create a <code>ehr.admin_delete_tenant_full</code> table reference
     */
    public AdminDeleteTenantFull() {
        this(DSL.name("admin_delete_tenant_full"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public AdminDeleteTenantFull as(String alias) {
        return new AdminDeleteTenantFull(DSL.name(alias), this, parameters);
    }

    @Override
    public AdminDeleteTenantFull as(Name alias) {
        return new AdminDeleteTenantFull(alias, this, parameters);
    }

    @Override
    public AdminDeleteTenantFull as(Table<?> alias) {
        return new AdminDeleteTenantFull(alias.getQualifiedName(), this, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public AdminDeleteTenantFull rename(String name) {
        return new AdminDeleteTenantFull(DSL.name(name), null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public AdminDeleteTenantFull rename(Name name) {
        return new AdminDeleteTenantFull(name, null, parameters);
    }

    /**
     * Rename this table
     */
    @Override
    public AdminDeleteTenantFull rename(Table<?> name) {
        return new AdminDeleteTenantFull(name.getQualifiedName(), null, parameters);
    }

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row1<Boolean> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    /**
     * Call this table-valued function
     */
    public AdminDeleteTenantFull call(Short tenantIdParam) {
        AdminDeleteTenantFull result = new AdminDeleteTenantFull(
                DSL.name("admin_delete_tenant_full"), null, new Field[] {DSL.val(tenantIdParam, SQLDataType.SMALLINT)});

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Call this table-valued function
     */
    public AdminDeleteTenantFull call(Field<Short> tenantIdParam) {
        AdminDeleteTenantFull result =
                new AdminDeleteTenantFull(DSL.name("admin_delete_tenant_full"), null, new Field[] {tenantIdParam});

        return aliased() ? result.as(getUnqualifiedName()) : result;
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function1<? super Boolean, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function1<? super Boolean, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
