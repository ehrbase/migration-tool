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
import java.util.function.Function;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.Keys;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TerminologyProviderRecord;
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
 * openEHR identified terminology provider
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TerminologyProvider extends TableImpl<TerminologyProviderRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.terminology_provider</code>
     */
    public static final TerminologyProvider TERMINOLOGY_PROVIDER = new TerminologyProvider();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TerminologyProviderRecord> getRecordType() {
        return TerminologyProviderRecord.class;
    }

    /**
     * The column <code>ehr.terminology_provider.code</code>.
     */
    public final TableField<TerminologyProviderRecord, String> CODE =
            createField(DSL.name("code"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.terminology_provider.source</code>.
     */
    public final TableField<TerminologyProviderRecord, String> SOURCE =
            createField(DSL.name("source"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.terminology_provider.authority</code>.
     */
    public final TableField<TerminologyProviderRecord, String> AUTHORITY =
            createField(DSL.name("authority"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.terminology_provider.sys_tenant</code>.
     */
    public final TableField<TerminologyProviderRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private TerminologyProvider(Name alias, Table<TerminologyProviderRecord> aliased) {
        this(alias, aliased, null);
    }

    private TerminologyProvider(Name alias, Table<TerminologyProviderRecord> aliased, Field<?>[] parameters) {
        super(
                alias,
                null,
                aliased,
                parameters,
                DSL.comment("openEHR identified terminology provider"),
                TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.terminology_provider</code> table reference
     */
    public TerminologyProvider(String alias) {
        this(DSL.name(alias), TERMINOLOGY_PROVIDER);
    }

    /**
     * Create an aliased <code>ehr.terminology_provider</code> table reference
     */
    public TerminologyProvider(Name alias) {
        this(alias, TERMINOLOGY_PROVIDER);
    }

    /**
     * Create a <code>ehr.terminology_provider</code> table reference
     */
    public TerminologyProvider() {
        this(DSL.name("terminology_provider"), null);
    }

    public <O extends Record> TerminologyProvider(Table<O> child, ForeignKey<O, TerminologyProviderRecord> key) {
        super(child, key, TERMINOLOGY_PROVIDER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public UniqueKey<TerminologyProviderRecord> getPrimaryKey() {
        return Keys.TERMINOLOGY_PROVIDER_PKEY;
    }

    @Override
    public List<ForeignKey<TerminologyProviderRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TERMINOLOGY_PROVIDER__TERMINOLOGY_PROVIDER_SYS_TENANT_FKEY);
    }

    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null)
            _tenant = new Tenant(this, Keys.TERMINOLOGY_PROVIDER__TERMINOLOGY_PROVIDER_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public TerminologyProvider as(String alias) {
        return new TerminologyProvider(DSL.name(alias), this);
    }

    @Override
    public TerminologyProvider as(Name alias) {
        return new TerminologyProvider(alias, this);
    }

    @Override
    public TerminologyProvider as(Table<?> alias) {
        return new TerminologyProvider(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public TerminologyProvider rename(String name) {
        return new TerminologyProvider(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TerminologyProvider rename(Name name) {
        return new TerminologyProvider(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public TerminologyProvider rename(Table<?> name) {
        return new TerminologyProvider(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, String, Short> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function4<? super String, ? super String, ? super String, ? super Short, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
            Class<U> toType,
            Function4<? super String, ? super String, ? super String, ? super Short, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
