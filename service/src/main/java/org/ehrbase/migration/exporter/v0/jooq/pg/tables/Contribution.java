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
import org.ehrbase.migration.exporter.v0.jooq.pg.Indexes;
import org.ehrbase.migration.exporter.v0.jooq.pg.Keys;
import org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionDataType;
import org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionState;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ContributionRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Index;
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
 * Contribution table, compositions reference this table
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Contribution extends TableImpl<ContributionRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.contribution</code>
     */
    public static final Contribution CONTRIBUTION = new Contribution();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ContributionRecord> getRecordType() {
        return ContributionRecord.class;
    }

    /**
     * The column <code>ehr.contribution.id</code>.
     */
    public final TableField<ContributionRecord, UUID> ID = createField(
            DSL.name("id"),
            SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)),
            this,
            "");

    /**
     * The column <code>ehr.contribution.ehr_id</code>.
     */
    public final TableField<ContributionRecord, UUID> EHR_ID =
            createField(DSL.name("ehr_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.contribution.contribution_type</code>.
     */
    public final TableField<ContributionRecord, ContributionDataType> CONTRIBUTION_TYPE = createField(
            DSL.name("contribution_type"),
            SQLDataType.VARCHAR.asEnumDataType(
                    org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionDataType.class),
            this,
            "");

    /**
     * The column <code>ehr.contribution.state</code>.
     */
    public final TableField<ContributionRecord, ContributionState> STATE = createField(
            DSL.name("state"),
            SQLDataType.VARCHAR.asEnumDataType(org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionState.class),
            this,
            "");

    /**
     * The column <code>ehr.contribution.signature</code>.
     */
    public final TableField<ContributionRecord, String> SIGNATURE =
            createField(DSL.name("signature"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.contribution.has_audit</code>.
     */
    public final TableField<ContributionRecord, UUID> HAS_AUDIT =
            createField(DSL.name("has_audit"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.contribution.sys_tenant</code>.
     */
    public final TableField<ContributionRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private Contribution(Name alias, Table<ContributionRecord> aliased) {
        this(alias, aliased, null);
    }

    private Contribution(Name alias, Table<ContributionRecord> aliased, Field<?>[] parameters) {
        super(
                alias,
                null,
                aliased,
                parameters,
                DSL.comment("Contribution table, compositions reference this table"),
                TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.contribution</code> table reference
     */
    public Contribution(String alias) {
        this(DSL.name(alias), CONTRIBUTION);
    }

    /**
     * Create an aliased <code>ehr.contribution</code> table reference
     */
    public Contribution(Name alias) {
        this(alias, CONTRIBUTION);
    }

    /**
     * Create a <code>ehr.contribution</code> table reference
     */
    public Contribution() {
        this(DSL.name("contribution"), null);
    }

    public <O extends Record> Contribution(Table<O> child, ForeignKey<O, ContributionRecord> key) {
        super(child, key, CONTRIBUTION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.CONTRIBUTION_EHR_IDX);
    }

    @Override
    public UniqueKey<ContributionRecord> getPrimaryKey() {
        return Keys.CONTRIBUTION_PKEY;
    }

    @Override
    public List<ForeignKey<ContributionRecord, ?>> getReferences() {
        return Arrays.asList(
                Keys.CONTRIBUTION__CONTRIBUTION_EHR_ID_FKEY,
                Keys.CONTRIBUTION__CONTRIBUTION_HAS_AUDIT_FKEY,
                Keys.CONTRIBUTION__CONTRIBUTION_SYS_TENANT_FKEY);
    }

    private transient org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr _ehr;
    private transient AuditDetails _auditDetails;
    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.ehr</code> table.
     */
    public org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr ehr() {
        if (_ehr == null)
            _ehr = new org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr(
                    this, Keys.CONTRIBUTION__CONTRIBUTION_EHR_ID_FKEY);

        return _ehr;
    }

    /**
     * Get the implicit join path to the <code>ehr.audit_details</code> table.
     */
    public AuditDetails auditDetails() {
        if (_auditDetails == null)
            _auditDetails = new AuditDetails(this, Keys.CONTRIBUTION__CONTRIBUTION_HAS_AUDIT_FKEY);

        return _auditDetails;
    }

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null) _tenant = new Tenant(this, Keys.CONTRIBUTION__CONTRIBUTION_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public Contribution as(String alias) {
        return new Contribution(DSL.name(alias), this);
    }

    @Override
    public Contribution as(Name alias) {
        return new Contribution(alias, this);
    }

    @Override
    public Contribution as(Table<?> alias) {
        return new Contribution(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Contribution rename(String name) {
        return new Contribution(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Contribution rename(Name name) {
        return new Contribution(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Contribution rename(Table<?> name) {
        return new Contribution(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, ContributionDataType, ContributionState, String, UUID, Short> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function7<
                            ? super UUID,
                            ? super UUID,
                            ? super ContributionDataType,
                            ? super ContributionState,
                            ? super String,
                            ? super UUID,
                            ? super Short,
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
                            ? super ContributionDataType,
                            ? super ContributionState,
                            ? super String,
                            ? super UUID,
                            ? super Short,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
