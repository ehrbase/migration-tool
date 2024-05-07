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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EventContextHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
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
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class EventContextHistory extends TableImpl<EventContextHistoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.event_context_history</code>
     */
    public static final EventContextHistory EVENT_CONTEXT_HISTORY = new EventContextHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EventContextHistoryRecord> getRecordType() {
        return EventContextHistoryRecord.class;
    }

    /**
     * The column <code>ehr.event_context_history.id</code>.
     */
    public final TableField<EventContextHistoryRecord, UUID> ID =
            createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.event_context_history.composition_id</code>.
     */
    public final TableField<EventContextHistoryRecord, UUID> COMPOSITION_ID =
            createField(DSL.name("composition_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.event_context_history.start_time</code>.
     */
    public final TableField<EventContextHistoryRecord, Timestamp> START_TIME =
            createField(DSL.name("start_time"), SQLDataType.TIMESTAMP(6).nullable(false), this, "");

    /**
     * The column <code>ehr.event_context_history.start_time_tzid</code>.
     */
    public final TableField<EventContextHistoryRecord, String> START_TIME_TZID =
            createField(DSL.name("start_time_tzid"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.event_context_history.end_time</code>.
     */
    public final TableField<EventContextHistoryRecord, Timestamp> END_TIME =
            createField(DSL.name("end_time"), SQLDataType.TIMESTAMP(6), this, "");

    /**
     * The column <code>ehr.event_context_history.end_time_tzid</code>.
     */
    public final TableField<EventContextHistoryRecord, String> END_TIME_TZID =
            createField(DSL.name("end_time_tzid"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.event_context_history.facility</code>.
     */
    public final TableField<EventContextHistoryRecord, UUID> FACILITY =
            createField(DSL.name("facility"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.event_context_history.location</code>.
     */
    public final TableField<EventContextHistoryRecord, String> LOCATION =
            createField(DSL.name("location"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.event_context_history.other_context</code>.
     */
    public final TableField<EventContextHistoryRecord, JSONB> OTHER_CONTEXT =
            createField(DSL.name("other_context"), SQLDataType.JSONB, this, "");

    /**
     * The column <code>ehr.event_context_history.setting</code>.
     */
    public final TableField<EventContextHistoryRecord, DvCodedTextRecord> SETTING = createField(
            DSL.name("setting"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.event_context_history.sys_transaction</code>.
     */
    public final TableField<EventContextHistoryRecord, Timestamp> SYS_TRANSACTION =
            createField(DSL.name("sys_transaction"), SQLDataType.TIMESTAMP(6).nullable(false), this, "");

    /**
     * The column <code>ehr.event_context_history.sys_period</code>.
     */
    public final TableField<EventContextHistoryRecord, SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>>
            SYS_PERIOD = createField(
                    DSL.name("sys_period"),
                    org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"tstzrange\"")
                            .nullable(false),
                    this,
                    "",
                    new SysPeriodBinder());

    /**
     * The column <code>ehr.event_context_history.sys_tenant</code>.
     */
    public final TableField<EventContextHistoryRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private EventContextHistory(Name alias, Table<EventContextHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private EventContextHistory(Name alias, Table<EventContextHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.event_context_history</code> table reference
     */
    public EventContextHistory(String alias) {
        this(DSL.name(alias), EVENT_CONTEXT_HISTORY);
    }

    /**
     * Create an aliased <code>ehr.event_context_history</code> table reference
     */
    public EventContextHistory(Name alias) {
        this(alias, EVENT_CONTEXT_HISTORY);
    }

    /**
     * Create a <code>ehr.event_context_history</code> table reference
     */
    public EventContextHistory() {
        this(DSL.name("event_context_history"), null);
    }

    public <O extends Record> EventContextHistory(Table<O> child, ForeignKey<O, EventContextHistoryRecord> key) {
        super(child, key, EVENT_CONTEXT_HISTORY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.EHR_EVENT_CONTEXT_HISTORY, Indexes.EVENT_CONTEXT_HISTORY_COMPOSITION_IDX);
    }

    @Override
    public List<ForeignKey<EventContextHistoryRecord, ?>> getReferences() {
        return Arrays.asList(Keys.EVENT_CONTEXT_HISTORY__EVENT_CONTEXT_HISTORY_SYS_TENANT_FKEY);
    }

    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null)
            _tenant = new Tenant(this, Keys.EVENT_CONTEXT_HISTORY__EVENT_CONTEXT_HISTORY_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public EventContextHistory as(String alias) {
        return new EventContextHistory(DSL.name(alias), this);
    }

    @Override
    public EventContextHistory as(Name alias) {
        return new EventContextHistory(alias, this);
    }

    @Override
    public EventContextHistory as(Table<?> alias) {
        return new EventContextHistory(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public EventContextHistory rename(String name) {
        return new EventContextHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventContextHistory rename(Name name) {
        return new EventContextHistory(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public EventContextHistory rename(Table<?> name) {
        return new EventContextHistory(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row13 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row13<
                    UUID,
                    UUID,
                    Timestamp,
                    String,
                    Timestamp,
                    String,
                    UUID,
                    String,
                    JSONB,
                    DvCodedTextRecord,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    Short>
            fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(
            Function13<
                            ? super UUID,
                            ? super UUID,
                            ? super Timestamp,
                            ? super String,
                            ? super Timestamp,
                            ? super String,
                            ? super UUID,
                            ? super String,
                            ? super JSONB,
                            ? super DvCodedTextRecord,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
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
            Function13<
                            ? super UUID,
                            ? super UUID,
                            ? super Timestamp,
                            ? super String,
                            ? super Timestamp,
                            ? super String,
                            ? super UUID,
                            ? super String,
                            ? super JSONB,
                            ? super DvCodedTextRecord,
                            ? super Timestamp,
                            ? super SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                            ? super Short,
                            ? extends U>
                    from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
