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

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.importer.v4.jooq.pg.Keys;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrFolderVersionRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
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
public class EhrFolderVersion extends TableImpl<EhrFolderVersionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.ehr_folder_version</code>
     */
    public static final EhrFolderVersion EHR_FOLDER_VERSION = new EhrFolderVersion();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EhrFolderVersionRecord> getRecordType() {
        return EhrFolderVersionRecord.class;
    }

    /**
     * The column <code>ehr.ehr_folder_version.vo_id</code>.
     */
    public final TableField<EhrFolderVersionRecord, UUID> VO_ID =
            createField(DSL.name("vo_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.ehr_id</code>.
     */
    public final TableField<EhrFolderVersionRecord, UUID> EHR_ID =
            createField(DSL.name("ehr_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.contribution_id</code>.
     */
    public final TableField<EhrFolderVersionRecord, UUID> CONTRIBUTION_ID =
            createField(DSL.name("contribution_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.audit_id</code>.
     */
    public final TableField<EhrFolderVersionRecord, UUID> AUDIT_ID =
            createField(DSL.name("audit_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.sys_version</code>.
     */
    public final TableField<EhrFolderVersionRecord, Integer> SYS_VERSION =
            createField(DSL.name("sys_version"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.sys_period_lower</code>.
     */
    public final TableField<EhrFolderVersionRecord, OffsetDateTime> SYS_PERIOD_LOWER = createField(
            DSL.name("sys_period_lower"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "");

    /**
     * The column <code>ehr.ehr_folder_version.ehr_folders_idx</code>.
     */
    public final TableField<EhrFolderVersionRecord, Integer> EHR_FOLDERS_IDX =
            createField(DSL.name("ehr_folders_idx"), SQLDataType.INTEGER.nullable(false), this, "");

    private EhrFolderVersion(Name alias, Table<EhrFolderVersionRecord> aliased) {
        this(alias, aliased, null);
    }

    private EhrFolderVersion(Name alias, Table<EhrFolderVersionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.ehr_folder_version</code> table reference
     */
    public EhrFolderVersion(String alias) {
        this(DSL.name(alias), EHR_FOLDER_VERSION);
    }

    /**
     * Create an aliased <code>ehr.ehr_folder_version</code> table reference
     */
    public EhrFolderVersion(Name alias) {
        this(alias, EHR_FOLDER_VERSION);
    }

    /**
     * Create a <code>ehr.ehr_folder_version</code> table reference
     */
    public EhrFolderVersion() {
        this(DSL.name("ehr_folder_version"), null);
    }

    public <O extends Record> EhrFolderVersion(Table<O> child, ForeignKey<O, EhrFolderVersionRecord> key) {
        super(child, key, EHR_FOLDER_VERSION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : org.ehrbase.migration.importer.v4.jooq.pg.Ehr.EHR;
    }

    @Override
    public UniqueKey<EhrFolderVersionRecord> getPrimaryKey() {
        return Keys.EHR_FOLDER_VERSION_PKEY;
    }

    @Override
    public List<ForeignKey<EhrFolderVersionRecord, ?>> getReferences() {
        return Arrays.asList(
                Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_EHR_ID_FKEY,
                Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_CONTRIBUTION_ID_FKEY,
                Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_AUDIT_ID_FKEY);
    }

    private transient org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr _ehr;
    private transient Contribution _contribution;
    private transient AuditDetails _auditDetails;

    /**
     * Get the implicit join path to the <code>ehr.ehr</code> table.
     */
    public org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr ehr() {
        if (_ehr == null)
            _ehr = new org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr(
                    this, Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_EHR_ID_FKEY);

        return _ehr;
    }

    /**
     * Get the implicit join path to the <code>ehr.contribution</code> table.
     */
    public Contribution contribution() {
        if (_contribution == null)
            _contribution = new Contribution(this, Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_CONTRIBUTION_ID_FKEY);

        return _contribution;
    }

    /**
     * Get the implicit join path to the <code>ehr.audit_details</code> table.
     */
    public AuditDetails auditDetails() {
        if (_auditDetails == null)
            _auditDetails = new AuditDetails(this, Keys.EHR_FOLDER_VERSION__EHR_FOLDER_VERSION_AUDIT_ID_FKEY);

        return _auditDetails;
    }

    @Override
    public EhrFolderVersion as(String alias) {
        return new EhrFolderVersion(DSL.name(alias), this);
    }

    @Override
    public EhrFolderVersion as(Name alias) {
        return new EhrFolderVersion(alias, this);
    }

    @Override
    public EhrFolderVersion as(Table<?> alias) {
        return new EhrFolderVersion(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public EhrFolderVersion rename(String name) {
        return new EhrFolderVersion(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EhrFolderVersion rename(Name name) {
        return new EhrFolderVersion(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public EhrFolderVersion rename(Table<?> name) {
        return new EhrFolderVersion(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, UUID, UUID, Integer, OffsetDateTime, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function7<
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super Integer,
                            ? super OffsetDateTime,
                            ? super Integer,
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
            Function7<
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super Integer,
                            ? super OffsetDateTime,
                            ? super Integer,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}