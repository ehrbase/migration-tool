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
package org.ehrbase.migration.importer.v4.jooq.pg.tables;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.importer.v4.jooq.pg.Indexes;
import org.ehrbase.migration.importer.v4.jooq.pg.Keys;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.CompDataRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function13;
import org.jooq.Index;
import org.jooq.JSONB;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row13;
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
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class CompData extends TableImpl<CompDataRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.comp_data</code>
     */
    public static final CompData COMP_DATA = new CompData();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CompDataRecord> getRecordType() {
        return CompDataRecord.class;
    }

    /**
     * The column <code>ehr.comp_data.vo_id</code>.
     */
    public final TableField<CompDataRecord, UUID> VO_ID =
            createField(DSL.name("vo_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.num</code>.
     */
    public final TableField<CompDataRecord, Integer> NUM =
            createField(DSL.name("num"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.citem_num</code>.
     */
    public final TableField<CompDataRecord, Integer> CITEM_NUM =
            createField(DSL.name("citem_num"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>ehr.comp_data.rm_entity</code>.
     */
    public final TableField<CompDataRecord, String> RM_ENTITY =
            createField(DSL.name("rm_entity"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.entity_concept</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_CONCEPT =
            createField(DSL.name("entity_concept"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.comp_data.entity_name</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_NAME =
            createField(DSL.name("entity_name"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.comp_data.entity_attribute</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_ATTRIBUTE =
            createField(DSL.name("entity_attribute"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.comp_data.entity_path</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_PATH =
            createField(DSL.name("entity_path"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.entity_path_cap</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_PATH_CAP =
            createField(DSL.name("entity_path_cap"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.entity_idx</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_IDX =
            createField(DSL.name("entity_idx"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.entity_idx_cap</code>.
     */
    public final TableField<CompDataRecord, String> ENTITY_IDX_CAP =
            createField(DSL.name("entity_idx_cap"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.entity_idx_len</code>.
     */
    public final TableField<CompDataRecord, Integer> ENTITY_IDX_LEN =
            createField(DSL.name("entity_idx_len"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>ehr.comp_data.data</code>.
     */
    public final TableField<CompDataRecord, JSONB> DATA =
            createField(DSL.name("data"), SQLDataType.JSONB.nullable(false), this, "");

    private CompData(Name alias, Table<CompDataRecord> aliased) {
        this(alias, aliased, null);
    }

    private CompData(Name alias, Table<CompDataRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.comp_data</code> table reference
     */
    public CompData(String alias) {
        this(DSL.name(alias), COMP_DATA);
    }

    /**
     * Create an aliased <code>ehr.comp_data</code> table reference
     */
    public CompData(Name alias) {
        this(alias, COMP_DATA);
    }

    /**
     * Create a <code>ehr.comp_data</code> table reference
     */
    public CompData() {
        this(DSL.name("comp_data"), null);
    }

    public <O extends Record> CompData(Table<O> child, ForeignKey<O, CompDataRecord> key) {
        super(child, key, COMP_DATA);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : org.ehrbase.migration.importer.v4.jooq.pg.Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.COMP_DATA_LEAF_IDX);
    }

    @Override
    public UniqueKey<CompDataRecord> getPrimaryKey() {
        return Keys.COMP_PKEY;
    }

    @Override
    public List<ForeignKey<CompDataRecord, ?>> getReferences() {
        return Arrays.asList(Keys.COMP_DATA__COMP_DATA_VO_ID_FKEY);
    }

    private transient CompVersion _compVersion;

    /**
     * Get the implicit join path to the <code>ehr.comp_version</code> table.
     */
    public CompVersion compVersion() {
        if (_compVersion == null) _compVersion = new CompVersion(this, Keys.COMP_DATA__COMP_DATA_VO_ID_FKEY);

        return _compVersion;
    }

    @Override
    public CompData as(String alias) {
        return new CompData(DSL.name(alias), this);
    }

    @Override
    public CompData as(Name alias) {
        return new CompData(alias, this);
    }

    @Override
    public CompData as(Table<?> alias) {
        return new CompData(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public CompData rename(String name) {
        return new CompData(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public CompData rename(Name name) {
        return new CompData(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public CompData rename(Table<?> name) {
        return new CompData(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row13 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row13<UUID, Integer, Integer, String, String, String, String, String, String, String, String, Integer, JSONB>
            fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function13<
                            ? super UUID,
                            ? super Integer,
                            ? super Integer,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super Integer,
                            ? super JSONB,
                            ? extends U>
                    from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(
            Class<U> toType,
            Function13<
                            ? super UUID,
                            ? super Integer,
                            ? super Integer,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super String,
                            ? super Integer,
                            ? super JSONB,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
