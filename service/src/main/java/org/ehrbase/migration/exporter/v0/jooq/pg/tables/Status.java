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

import com.nedap.archie.rm.datastructures.ItemStructure;
import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.exporter.v0.jooq.binding.OtherDetailsJsonbBinder;
import org.ehrbase.migration.exporter.v0.jooq.binding.SysPeriodBinder;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.Indexes;
import org.ehrbase.migration.exporter.v0.jooq.pg.Keys;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.StatusRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function14;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row14;
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
 * specifies an ehr modality and ownership (patient)
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Status extends TableImpl<StatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.status</code>
     */
    public static final Status STATUS = new Status();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StatusRecord> getRecordType() {
        return StatusRecord.class;
    }

    /**
     * The column <code>ehr.status.id</code>.
     */
    public final TableField<StatusRecord, UUID> ID = createField(
            DSL.name("id"),
            SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)),
            this,
            "");

    /**
     * The column <code>ehr.status.ehr_id</code>.
     */
    public final TableField<StatusRecord, UUID> EHR_ID = createField(DSL.name("ehr_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.status.is_queryable</code>.
     */
    public final TableField<StatusRecord, Boolean> IS_QUERYABLE = createField(
            DSL.name("is_queryable"),
            SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)),
            this,
            "");

    /**
     * The column <code>ehr.status.is_modifiable</code>.
     */
    public final TableField<StatusRecord, Boolean> IS_MODIFIABLE = createField(
            DSL.name("is_modifiable"),
            SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("true"), SQLDataType.BOOLEAN)),
            this,
            "");

    /**
     * The column <code>ehr.status.party</code>.
     */
    public final TableField<StatusRecord, UUID> PARTY =
            createField(DSL.name("party"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.status.other_details</code>.
     */
    public final TableField<StatusRecord, ItemStructure> OTHER_DETAILS =
            createField(DSL.name("other_details"), SQLDataType.JSONB, this, "", new OtherDetailsJsonbBinder());

    /**
     * The column <code>ehr.status.sys_transaction</code>.
     */
    public final TableField<StatusRecord, Timestamp> SYS_TRANSACTION =
            createField(DSL.name("sys_transaction"), SQLDataType.TIMESTAMP(6).nullable(false), this, "");

    /**
     * The column <code>ehr.status.sys_period</code>.
     */
    public final TableField<StatusRecord, SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>> SYS_PERIOD =
            createField(
                    DSL.name("sys_period"),
                    org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"tstzrange\"")
                            .nullable(false),
                    this,
                    "",
                    new SysPeriodBinder());

    /**
     * The column <code>ehr.status.has_audit</code>.
     */
    public final TableField<StatusRecord, UUID> HAS_AUDIT =
            createField(DSL.name("has_audit"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.status.attestation_ref</code>.
     */
    public final TableField<StatusRecord, UUID> ATTESTATION_REF =
            createField(DSL.name("attestation_ref"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.status.in_contribution</code>.
     */
    public final TableField<StatusRecord, UUID> IN_CONTRIBUTION =
            createField(DSL.name("in_contribution"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.status.archetype_node_id</code>.
     */
    public final TableField<StatusRecord, String> ARCHETYPE_NODE_ID = createField(
            DSL.name("archetype_node_id"),
            SQLDataType.CLOB
                    .nullable(false)
                    .defaultValue(DSL.field(DSL.raw("'openEHR-EHR-EHR_STATUS.generic.v1'::text"), SQLDataType.CLOB)),
            this,
            "");

    /**
     * The column <code>ehr.status.name</code>.
     */
    public final TableField<StatusRecord, DvCodedTextRecord> NAME = createField(
            DSL.name("name"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.status.sys_tenant</code>.
     */
    public final TableField<StatusRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private Status(Name alias, Table<StatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private Status(Name alias, Table<StatusRecord> aliased, Field<?>[] parameters) {
        super(
                alias,
                null,
                aliased,
                parameters,
                DSL.comment("specifies an ehr modality and ownership (patient)"),
                TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.status</code> table reference
     */
    public Status(String alias) {
        this(DSL.name(alias), STATUS);
    }

    /**
     * Create an aliased <code>ehr.status</code> table reference
     */
    public Status(Name alias) {
        this(alias, STATUS);
    }

    /**
     * Create a <code>ehr.status</code> table reference
     */
    public Status() {
        this(DSL.name("status"), null);
    }

    public <O extends Record> Status(Table<O> child, ForeignKey<O, StatusRecord> key) {
        super(child, key, STATUS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.STATUS_PARTY_IDX);
    }

    @Override
    public UniqueKey<StatusRecord> getPrimaryKey() {
        return Keys.STATUS_PKEY;
    }

    @Override
    public List<UniqueKey<StatusRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.STATUS_EHR_ID_KEY);
    }

    @Override
    public List<ForeignKey<StatusRecord, ?>> getReferences() {
        return Arrays.asList(
                Keys.STATUS__STATUS_EHR_ID_FKEY,
                Keys.STATUS__STATUS_PARTY_FKEY,
                Keys.STATUS__STATUS_HAS_AUDIT_FKEY,
                Keys.STATUS__STATUS_ATTESTATION_REF_FKEY,
                Keys.STATUS__STATUS_IN_CONTRIBUTION_FKEY,
                Keys.STATUS__STATUS_SYS_TENANT_FKEY);
    }

    private transient org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr _ehr;
    private transient PartyIdentified _partyIdentified;
    private transient AuditDetails _auditDetails;
    private transient AttestationRef _attestationRef;
    private transient Contribution _contribution;
    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.ehr</code> table.
     */
    public org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr ehr() {
        if (_ehr == null)
            _ehr = new org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr(this, Keys.STATUS__STATUS_EHR_ID_FKEY);

        return _ehr;
    }

    /**
     * Get the implicit join path to the <code>ehr.party_identified</code>
     * table.
     */
    public PartyIdentified partyIdentified() {
        if (_partyIdentified == null) _partyIdentified = new PartyIdentified(this, Keys.STATUS__STATUS_PARTY_FKEY);

        return _partyIdentified;
    }

    /**
     * Get the implicit join path to the <code>ehr.audit_details</code> table.
     */
    public AuditDetails auditDetails() {
        if (_auditDetails == null) _auditDetails = new AuditDetails(this, Keys.STATUS__STATUS_HAS_AUDIT_FKEY);

        return _auditDetails;
    }

    /**
     * Get the implicit join path to the <code>ehr.attestation_ref</code> table.
     */
    public AttestationRef attestationRef() {
        if (_attestationRef == null)
            _attestationRef = new AttestationRef(this, Keys.STATUS__STATUS_ATTESTATION_REF_FKEY);

        return _attestationRef;
    }

    /**
     * Get the implicit join path to the <code>ehr.contribution</code> table.
     */
    public Contribution contribution() {
        if (_contribution == null) _contribution = new Contribution(this, Keys.STATUS__STATUS_IN_CONTRIBUTION_FKEY);

        return _contribution;
    }

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null) _tenant = new Tenant(this, Keys.STATUS__STATUS_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public Status as(String alias) {
        return new Status(DSL.name(alias), this);
    }

    @Override
    public Status as(Name alias) {
        return new Status(alias, this);
    }

    @Override
    public Status as(Table<?> alias) {
        return new Status(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Status rename(String name) {
        return new Status(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Status rename(Name name) {
        return new Status(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Status rename(Table<?> name) {
        return new Status(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row14 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row14<
                    UUID,
                    UUID,
                    Boolean,
                    Boolean,
                    UUID,
                    ItemStructure,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    UUID,
                    UUID,
                    UUID,
                    String,
                    DvCodedTextRecord,
                    Short>
            fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function14<
                            ? super UUID,
                            ? super UUID,
                            ? super Boolean,
                            ? super Boolean,
                            ? super UUID,
                            ? super ItemStructure,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super String,
                            ? super DvCodedTextRecord,
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
            Function14<
                            ? super UUID,
                            ? super UUID,
                            ? super Boolean,
                            ? super Boolean,
                            ? super UUID,
                            ? super ItemStructure,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super String,
                            ? super DvCodedTextRecord,
                            ? super Short,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
