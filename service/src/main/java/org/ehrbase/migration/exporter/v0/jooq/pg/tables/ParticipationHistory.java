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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ParticipationHistoryRecord;
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
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ParticipationHistory extends TableImpl<ParticipationHistoryRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ehr.participation_history</code>
     */
    public static final ParticipationHistory PARTICIPATION_HISTORY = new ParticipationHistory();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ParticipationHistoryRecord> getRecordType() {
        return ParticipationHistoryRecord.class;
    }

    /**
     * The column <code>ehr.participation_history.id</code>.
     */
    public final TableField<ParticipationHistoryRecord, UUID> ID =
            createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.participation_history.event_context</code>.
     */
    public final TableField<ParticipationHistoryRecord, UUID> EVENT_CONTEXT =
            createField(DSL.name("event_context"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>ehr.participation_history.performer</code>.
     */
    public final TableField<ParticipationHistoryRecord, UUID> PERFORMER =
            createField(DSL.name("performer"), SQLDataType.UUID, this, "");

    /**
     * The column <code>ehr.participation_history.function</code>.
     */
    public final TableField<ParticipationHistoryRecord, DvCodedTextRecord> FUNCTION = createField(
            DSL.name("function"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.participation_history.mode</code>.
     */
    public final TableField<ParticipationHistoryRecord, DvCodedTextRecord> MODE = createField(
            DSL.name("mode"),
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            this,
            "");

    /**
     * The column <code>ehr.participation_history.time_lower</code>.
     */
    public final TableField<ParticipationHistoryRecord, Timestamp> TIME_LOWER =
            createField(DSL.name("time_lower"), SQLDataType.TIMESTAMP(6), this, "");

    /**
     * The column <code>ehr.participation_history.time_lower_tz</code>.
     */
    public final TableField<ParticipationHistoryRecord, String> TIME_LOWER_TZ =
            createField(DSL.name("time_lower_tz"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.participation_history.sys_transaction</code>.
     */
    public final TableField<ParticipationHistoryRecord, Timestamp> SYS_TRANSACTION =
            createField(DSL.name("sys_transaction"), SQLDataType.TIMESTAMP(6).nullable(false), this, "");

    /**
     * The column <code>ehr.participation_history.sys_period</code>.
     */
    public final TableField<ParticipationHistoryRecord, SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>>
            SYS_PERIOD = createField(
                    DSL.name("sys_period"),
                    org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"tstzrange\"")
                            .nullable(false),
                    this,
                    "",
                    new SysPeriodBinder());

    /**
     * The column <code>ehr.participation_history.time_upper</code>.
     */
    public final TableField<ParticipationHistoryRecord, Timestamp> TIME_UPPER =
            createField(DSL.name("time_upper"), SQLDataType.TIMESTAMP(6), this, "");

    /**
     * The column <code>ehr.participation_history.time_upper_tz</code>.
     */
    public final TableField<ParticipationHistoryRecord, String> TIME_UPPER_TZ =
            createField(DSL.name("time_upper_tz"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>ehr.participation_history.sys_tenant</code>.
     */
    public final TableField<ParticipationHistoryRecord, Short> SYS_TENANT = createField(
            DSL.name("sys_tenant"),
            SQLDataType.SMALLINT.defaultValue(DSL.field(DSL.raw("1"), SQLDataType.SMALLINT)),
            this,
            "");

    private ParticipationHistory(Name alias, Table<ParticipationHistoryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ParticipationHistory(Name alias, Table<ParticipationHistoryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ehr.participation_history</code> table reference
     */
    public ParticipationHistory(String alias) {
        this(DSL.name(alias), PARTICIPATION_HISTORY);
    }

    /**
     * Create an aliased <code>ehr.participation_history</code> table reference
     */
    public ParticipationHistory(Name alias) {
        this(alias, PARTICIPATION_HISTORY);
    }

    /**
     * Create a <code>ehr.participation_history</code> table reference
     */
    public ParticipationHistory() {
        this(DSL.name("participation_history"), null);
    }

    public <O extends Record> ParticipationHistory(Table<O> child, ForeignKey<O, ParticipationHistoryRecord> key) {
        super(child, key, PARTICIPATION_HISTORY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Ehr.EHR;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.EHR_PARTICIPATION_HISTORY, Indexes.PARTICIPATION_HISTORY_EVENT_CONTEXT_IDX);
    }

    @Override
    public List<ForeignKey<ParticipationHistoryRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PARTICIPATION_HISTORY__PARTICIPATION_HISTORY_SYS_TENANT_FKEY);
    }

    private transient Tenant _tenant;

    /**
     * Get the implicit join path to the <code>ehr.tenant</code> table.
     */
    public Tenant tenant() {
        if (_tenant == null)
            _tenant = new Tenant(this, Keys.PARTICIPATION_HISTORY__PARTICIPATION_HISTORY_SYS_TENANT_FKEY);

        return _tenant;
    }

    @Override
    public ParticipationHistory as(String alias) {
        return new ParticipationHistory(DSL.name(alias), this);
    }

    @Override
    public ParticipationHistory as(Name alias) {
        return new ParticipationHistory(alias, this);
    }

    @Override
    public ParticipationHistory as(Table<?> alias) {
        return new ParticipationHistory(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ParticipationHistory rename(String name) {
        return new ParticipationHistory(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ParticipationHistory rename(Name name) {
        return new ParticipationHistory(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ParticipationHistory rename(Table<?> name) {
        return new ParticipationHistory(name.getQualifiedName(), null);
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
