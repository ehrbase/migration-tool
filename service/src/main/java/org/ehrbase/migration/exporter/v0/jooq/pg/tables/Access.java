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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.Keys;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AccessRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function4;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

/**
 * defines the modality for accessing an com.ethercis.ehr (security strategy
 * implementation)
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Access extends TableImpl<AccessRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.access</code>
     */
    public static final Access ACCESS = new Access();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccessRecord> getRecordType() {
        return AccessRecord.class;
    }

    /**
     * The column <code>ehr.access.id</code>.
     */
    public final TableField<AccessRecord, UUID> ID = createField(
            DSL.name("id"),
            SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)),
            this,
            "");

    /**
     * The column <code>ehr.access.settings</code>.
     */
    public final TableField<AccessRecord, String> SETTINGS =
            createField(DSL.name("settings"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.access.scheme</code>.
     */
    public final TableField<AccessRecord, String> SCHEME = createField(DSL.name("scheme"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.access.sys_tenant</code>.
     */
    public final TableField<AccessRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private Access(Name alias, Table<AccessRecord> aliased) {
        this(alias, aliased, null);
    }

    private Access(Name alias, Table<AccessRecord> aliased, Field<?>[] parameters) {
        super(
                alias,
                null,
                aliased,
                parameters,
                DSL.comment(
                        "defines the modality for accessing an com.ethercis.ehr (security strategy implementation)"),
                TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.access</code> table reference
     */
    public Access(String alias) {
        this(DSL.name(alias), ACCESS);
    }

    /**
     * Create an aliased <code>ehr.access</code> table reference
     */
    public Access(Name alias) {
        this(alias, ACCESS);
    }

    /**
     * Create a <code>ehr.access</code> table reference
     */
    public Access() {
        this(DSL.name("access"), null);
    }

    public <O extends Record> Access(Table<O> child, ForeignKey<O, AccessRecord> key) {
        super(child, key, ACCESS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public UniqueKey<AccessRecord> getPrimaryKey() {
        return Keys.ACCESS_PKEY;
    }

    @Override
    public List<ForeignKey<AccessRecord, ?>> getReferences() {
        return Arrays.asList(Keys.ACCESS__ACCESS_SYS_TENANT_FKEY);
    }

    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null) _tenant = new Tenant(this, Keys.ACCESS__ACCESS_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public Access as(String alias) {
        return new Access(DSL.name(alias), this);
    }

    @Override
    public Access as(Name alias) {
        return new Access(alias, this);
    }

    @Override
    public Access as(Table<?> alias) {
        return new Access(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Access rename(String name) {
        return new Access(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Access rename(Name name) {
        return new Access(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Access rename(Table<?> name) {
        return new Access(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<UUID, String, String, Short> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function4<? super UUID, ? super String, ? super String, ? super Short, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
            Class<U> toType, Function4<? super UUID, ? super String, ? super String, ? super Short, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
