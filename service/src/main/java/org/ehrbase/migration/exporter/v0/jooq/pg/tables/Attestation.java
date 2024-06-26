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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AttestationRecord;
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
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Attestation extends TableImpl<AttestationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.attestation</code>
     */
    public static final Attestation ATTESTATION = new Attestation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AttestationRecord> getRecordType() {
        return AttestationRecord.class;
    }

    /**
     * The column <code>ehr.attestation.id</code>.
     */
    public final TableField<AttestationRecord, UUID> ID = createField(
            DSL.name("id"),
            SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)),
            this,
            "");

    /**
     * The column <code>ehr.attestation.proof</code>.
     */
    public final TableField<AttestationRecord, String> PROOF =
            createField(DSL.name("proof"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.attestation.reason</code>.
     */
    public final TableField<AttestationRecord, String> REASON =
            createField(DSL.name("reason"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.attestation.is_pending</code>.
     */
    public final TableField<AttestationRecord, Boolean> IS_PENDING =
            createField(DSL.name("is_pending"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>ehr.attestation.has_audit</code>.
     */
    public final TableField<AttestationRecord, UUID> HAS_AUDIT =
            createField(DSL.name("has_audit"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.attestation.reference</code>.
     */
    public final TableField<AttestationRecord, UUID> REFERENCE =
            createField(DSL.name("reference"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.attestation.sys_tenant</code>.
     */
    public final TableField<AttestationRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private Attestation(Name alias, Table<AttestationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Attestation(Name alias, Table<AttestationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.attestation</code> table reference
     */
    public Attestation(String alias) {
        this(DSL.name(alias), ATTESTATION);
    }

    /**
     * Create an aliased <code>ehr.attestation</code> table reference
     */
    public Attestation(Name alias) {
        this(alias, ATTESTATION);
    }

    /**
     * Create a <code>ehr.attestation</code> table reference
     */
    public Attestation() {
        this(DSL.name("attestation"), null);
    }

    public <O extends Record> Attestation(Table<O> child, ForeignKey<O, AttestationRecord> key) {
        super(child, key, ATTESTATION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.ATTESTATION_REFERENCE_IDX);
    }

    @Override
    public UniqueKey<AttestationRecord> getPrimaryKey() {
        return Keys.ATTESTATION_PKEY;
    }

    @Override
    public List<ForeignKey<AttestationRecord, ?>> getReferences() {
        return Arrays.asList(
                Keys.ATTESTATION__ATTESTATION_HAS_AUDIT_FKEY,
                Keys.ATTESTATION__ATTESTATION_REFERENCE_FKEY,
                Keys.ATTESTATION__ATTESTATION_SYS_TENANT_FKEY);
    }

    private transient AuditDetails _auditDetails;
    private transient AttestationRef _attestationRef;
    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.audit_details</code> table.
     */
    public AuditDetails auditDetails() {
        if (_auditDetails == null) _auditDetails = new AuditDetails(this, Keys.ATTESTATION__ATTESTATION_HAS_AUDIT_FKEY);

        return _auditDetails;
    }

    /**
     * Get the implicit join path to the <code>ehr.attestation_ref</code> table.
     */
    public AttestationRef attestationRef() {
        if (_attestationRef == null)
            _attestationRef = new AttestationRef(this, Keys.ATTESTATION__ATTESTATION_REFERENCE_FKEY);

        return _attestationRef;
    }

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null) _tenant = new Tenant(this, Keys.ATTESTATION__ATTESTATION_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public Attestation as(String alias) {
        return new Attestation(DSL.name(alias), this);
    }

    @Override
    public Attestation as(Name alias) {
        return new Attestation(alias, this);
    }

    @Override
    public Attestation as(Table<?> alias) {
        return new Attestation(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Attestation rename(String name) {
        return new Attestation(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Attestation rename(Name name) {
        return new Attestation(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Attestation rename(Table<?> name) {
        return new Attestation(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, String, String, Boolean, UUID, UUID, Short> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function7<
                            ? super UUID,
                            ? super String,
                            ? super String,
                            ? super Boolean,
                            ? super UUID,
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
                            ? super String,
                            ? super String,
                            ? super Boolean,
                            ? super UUID,
                            ? super UUID,
                            ? super Short,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
