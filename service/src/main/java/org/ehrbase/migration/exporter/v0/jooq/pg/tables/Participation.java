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

import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import org.ehrbase.migration.exporter.v0.jooq.binding.SysPeriodBinder;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.Indexes;
import org.ehrbase.migration.exporter.v0.jooq.pg.Keys;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ParticipationRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function12;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row12;
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
 * define a participating party for an event f.ex.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Participation extends TableImpl<ParticipationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.participation</code>
     */
    public static final Participation PARTICIPATION = new Participation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ParticipationRecord> getRecordType() {
        return ParticipationRecord.class;
    }

    /**
     * The column <code>ehr.participation.id</code>.
     */
    public final TableField<ParticipationRecord, UUID> ID = createField(
            DSL.name("id"),
            SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("uuid_generate_v4()"), SQLDataType.UUID)),
            this,
            "");

    /**
     * The column <code>ehr.participation.event_context</code>.
     */
    public final TableField<ParticipationRecord, UUID> EVENT_CONTEXT =
            createField(DSL.name("event_context"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.participation.performer</code>.
     */
    public final TableField<ParticipationRecord, UUID> PERFORMER =
            createField(DSL.name("performer"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.participation.function</code>.
     */
    public final TableField<ParticipationRecord, DvCodedTextRecord> FUNCTION = createField(
            DSL.name("function"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.participation.mode</code>.
     */
    public final TableField<ParticipationRecord, DvCodedTextRecord> MODE = createField(
            DSL.name("mode"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.participation.time_lower</code>.
     */
    public final TableField<ParticipationRecord, Timestamp> TIME_LOWER =
            createField(DSL.name("time_lower"), SQLDataType.TIMESTAMP(6), this, "");

    /**
     * The column <code>ehr.participation.time_lower_tz</code>.
     */
    public final TableField<ParticipationRecord, String> TIME_LOWER_TZ =
            createField(DSL.name("time_lower_tz"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.participation.sys_transaction</code>.
     */
    public final TableField<ParticipationRecord, Timestamp> SYS_TRANSACTION =
            createField(DSL.name("sys_transaction"), SQLDataType.TIMESTAMP(6).nullable(false), this, "");

    /**
     * The column <code>ehr.participation.sys_period</code>.
     */
    public final TableField<ParticipationRecord, SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>>
            SYS_PERIOD = createField(
                    DSL.name("sys_period"),
                    org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"tstzrange\"")
                            .nullable(false),
                    this,
                    "",
                    new SysPeriodBinder());

    /**
     * The column <code>ehr.participation.time_upper</code>.
     */
    public final TableField<ParticipationRecord, Timestamp> TIME_UPPER =
            createField(DSL.name("time_upper"), SQLDataType.TIMESTAMP(6), this, "");

    /**
     * The column <code>ehr.participation.time_upper_tz</code>.
     */
    public final TableField<ParticipationRecord, String> TIME_UPPER_TZ =
            createField(DSL.name("time_upper_tz"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.participation.sys_tenant</code>.
     */
    public final TableField<ParticipationRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.nullable(false).defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private Participation(Name alias, Table<ParticipationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Participation(Name alias, Table<ParticipationRecord> aliased, Field<?>[] parameters) {
        super(
                alias,
                null,
                aliased,
                parameters,
                DSL.comment("define a participating party for an event f.ex."),
                TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.participation</code> table reference
     */
    public Participation(String alias) {
        this(DSL.name(alias), PARTICIPATION);
    }

    /**
     * Create an aliased <code>ehr.participation</code> table reference
     */
    public Participation(Name alias) {
        this(alias, PARTICIPATION);
    }

    /**
     * Create a <code>ehr.participation</code> table reference
     */
    public Participation() {
        this(DSL.name("participation"), null);
    }

    public <O extends Record> Participation(Table<O> child, ForeignKey<O, ParticipationRecord> key) {
        super(child, key, PARTICIPATION);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.CONTEXT_PARTICIPATION_INDEX);
    }

    @Override
    public UniqueKey<ParticipationRecord> getPrimaryKey() {
        return Keys.PARTICIPATION_PKEY;
    }

    @Override
    public List<ForeignKey<ParticipationRecord, ?>> getReferences() {
        return Arrays.asList(
                Keys.PARTICIPATION__PARTICIPATION_EVENT_CONTEXT_FKEY,
                Keys.PARTICIPATION__PARTICIPATION_PERFORMER_FKEY,
                Keys.PARTICIPATION__PARTICIPATION_SYS_TENANT_FKEY);
    }

    private transient EventContext _eventContext;
    private transient PartyIdentified _partyIdentified;
    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.event_context</code> table.
     */
    public EventContext eventContext() {
        if (_eventContext == null)
            _eventContext = new EventContext(this, Keys.PARTICIPATION__PARTICIPATION_EVENT_CONTEXT_FKEY);

        return _eventContext;
    }

    /**
     * Get the implicit join path to the <code>ehr.party_identified</code>
     * table.
     */
    public PartyIdentified partyIdentified() {
        if (_partyIdentified == null)
            _partyIdentified = new PartyIdentified(this, Keys.PARTICIPATION__PARTICIPATION_PERFORMER_FKEY);

        return _partyIdentified;
    }

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null) _tenant = new Tenant(this, Keys.PARTICIPATION__PARTICIPATION_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public Participation as(String alias) {
        return new Participation(DSL.name(alias), this);
    }

    @Override
    public Participation as(Name alias) {
        return new Participation(alias, this);
    }

    @Override
    public Participation as(Table<?> alias) {
        return new Participation(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Participation rename(String name) {
        return new Participation(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Participation rename(Name name) {
        return new Participation(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Participation rename(Table<?> name) {
        return new Participation(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<
                    UUID,
                    UUID,
                    UUID,
                    DvCodedTextRecord,
                    DvCodedTextRecord,
                    Timestamp,
                    String,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    Timestamp,
                    String,
                    Short>
            fieldsRow() {
        return (Row12) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function12<
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super DvCodedTextRecord,
                            ? super DvCodedTextRecord,
                            ? super Timestamp,
                            ? super String,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                            ? super Timestamp,
                            ? super String,
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
            Function12<
                            ? super UUID,
                            ? super UUID,
                            ? super UUID,
                            ? super DvCodedTextRecord,
                            ? super DvCodedTextRecord,
                            ? super Timestamp,
                            ? super String,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                            ? super Timestamp,
                            ? super String,
                            ? super Short,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
