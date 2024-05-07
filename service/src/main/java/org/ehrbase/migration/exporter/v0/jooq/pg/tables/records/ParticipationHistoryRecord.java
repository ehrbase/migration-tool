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
package org.ehrbase.migration.exporter.v0.jooq.pg.tables.records;

import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.ParticipationHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.Field;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ParticipationHistoryRecord extends TableRecordImpl<ParticipationHistoryRecord>
        implements Record12<
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
                Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.participation_history.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.participation_history.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.participation_history.event_context</code>.
     */
    public void setEventContext(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.participation_history.event_context</code>.
     */
    public UUID getEventContext() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>ehr.participation_history.performer</code>.
     */
    public void setPerformer(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.participation_history.performer</code>.
     */
    public UUID getPerformer() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>ehr.participation_history.function</code>.
     */
    public void setFunction(DvCodedTextRecord value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.participation_history.function</code>.
     */
    public DvCodedTextRecord getFunction() {
        return (DvCodedTextRecord) get(3);
    }

    /**
     * Setter for <code>ehr.participation_history.mode</code>.
     */
    public void setMode(DvCodedTextRecord value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.participation_history.mode</code>.
     */
    public DvCodedTextRecord getMode() {
        return (DvCodedTextRecord) get(4);
    }

    /**
     * Setter for <code>ehr.participation_history.time_lower</code>.
     */
    public void setTimeLower(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.participation_history.time_lower</code>.
     */
    public Timestamp getTimeLower() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>ehr.participation_history.time_lower_tz</code>.
     */
    public void setTimeLowerTz(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.participation_history.time_lower_tz</code>.
     */
    public String getTimeLowerTz() {
        return (String) get(6);
    }

    /**
     * Setter for <code>ehr.participation_history.sys_transaction</code>.
     */
    public void setSysTransaction(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.participation_history.sys_transaction</code>.
     */
    public Timestamp getSysTransaction() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>ehr.participation_history.sys_period</code>.
     */
    public void setSysPeriod(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.participation_history.sys_period</code>.
     */
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> getSysPeriod() {
        return (SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>) get(8);
    }

    /**
     * Setter for <code>ehr.participation_history.time_upper</code>.
     */
    public void setTimeUpper(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.participation_history.time_upper</code>.
     */
    public Timestamp getTimeUpper() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>ehr.participation_history.time_upper_tz</code>.
     */
    public void setTimeUpperTz(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>ehr.participation_history.time_upper_tz</code>.
     */
    public String getTimeUpperTz() {
        return (String) get(10);
    }

    /**
     * Setter for <code>ehr.participation_history.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(11, value);
    }

    /**
     * Getter for <code>ehr.participation_history.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(11);
    }

    // -------------------------------------------------------------------------
    // Record12 type implementation
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
            valuesRow() {
        return (Row12) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return ParticipationHistory.PARTICIPATION_HISTORY.ID;
    }

    @Override
    public Field<UUID> field2() {
        return ParticipationHistory.PARTICIPATION_HISTORY.EVENT_CONTEXT;
    }

    @Override
    public Field<UUID> field3() {
        return ParticipationHistory.PARTICIPATION_HISTORY.PERFORMER;
    }

    @Override
    public Field<DvCodedTextRecord> field4() {
        return ParticipationHistory.PARTICIPATION_HISTORY.FUNCTION;
    }

    @Override
    public Field<DvCodedTextRecord> field5() {
        return ParticipationHistory.PARTICIPATION_HISTORY.MODE;
    }

    @Override
    public Field<Timestamp> field6() {
        return ParticipationHistory.PARTICIPATION_HISTORY.TIME_LOWER;
    }

    @Override
    public Field<String> field7() {
        return ParticipationHistory.PARTICIPATION_HISTORY.TIME_LOWER_TZ;
    }

    @Override
    public Field<Timestamp> field8() {
        return ParticipationHistory.PARTICIPATION_HISTORY.SYS_TRANSACTION;
    }

    @Override
    public Field<SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>> field9() {
        return ParticipationHistory.PARTICIPATION_HISTORY.SYS_PERIOD;
    }

    @Override
    public Field<Timestamp> field10() {
        return ParticipationHistory.PARTICIPATION_HISTORY.TIME_UPPER;
    }

    @Override
    public Field<String> field11() {
        return ParticipationHistory.PARTICIPATION_HISTORY.TIME_UPPER_TZ;
    }

    @Override
    public Field<Short> field12() {
        return ParticipationHistory.PARTICIPATION_HISTORY.SYS_TENANT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getEventContext();
    }

    @Override
    public UUID component3() {
        return getPerformer();
    }

    @Override
    public DvCodedTextRecord component4() {
        return getFunction();
    }

    @Override
    public DvCodedTextRecord component5() {
        return getMode();
    }

    @Override
    public Timestamp component6() {
        return getTimeLower();
    }

    @Override
    public String component7() {
        return getTimeLowerTz();
    }

    @Override
    public Timestamp component8() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> component9() {
        return getSysPeriod();
    }

    @Override
    public Timestamp component10() {
        return getTimeUpper();
    }

    @Override
    public String component11() {
        return getTimeUpperTz();
    }

    @Override
    public Short component12() {
        return getSysTenant();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getEventContext();
    }

    @Override
    public UUID value3() {
        return getPerformer();
    }

    @Override
    public DvCodedTextRecord value4() {
        return getFunction();
    }

    @Override
    public DvCodedTextRecord value5() {
        return getMode();
    }

    @Override
    public Timestamp value6() {
        return getTimeLower();
    }

    @Override
    public String value7() {
        return getTimeLowerTz();
    }

    @Override
    public Timestamp value8() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value9() {
        return getSysPeriod();
    }

    @Override
    public Timestamp value10() {
        return getTimeUpper();
    }

    @Override
    public String value11() {
        return getTimeUpperTz();
    }

    @Override
    public Short value12() {
        return getSysTenant();
    }

    @Override
    public ParticipationHistoryRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value2(UUID value) {
        setEventContext(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value3(UUID value) {
        setPerformer(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value4(DvCodedTextRecord value) {
        setFunction(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value5(DvCodedTextRecord value) {
        setMode(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value6(Timestamp value) {
        setTimeLower(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value7(String value) {
        setTimeLowerTz(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value8(Timestamp value) {
        setSysTransaction(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value9(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        setSysPeriod(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value10(Timestamp value) {
        setTimeUpper(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value11(String value) {
        setTimeUpperTz(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord value12(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public ParticipationHistoryRecord values(
            UUID value1,
            UUID value2,
            UUID value3,
            DvCodedTextRecord value4,
            DvCodedTextRecord value5,
            Timestamp value6,
            String value7,
            Timestamp value8,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value9,
            Timestamp value10,
            String value11,
            Short value12) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ParticipationHistoryRecord
     */
    public ParticipationHistoryRecord() {
        super(ParticipationHistory.PARTICIPATION_HISTORY);
    }

    /**
     * Create a detached, initialised ParticipationHistoryRecord
     */
    public ParticipationHistoryRecord(
            UUID id,
            UUID eventContext,
            UUID performer,
            DvCodedTextRecord function,
            DvCodedTextRecord mode,
            Timestamp timeLower,
            String timeLowerTz,
            Timestamp sysTransaction,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> sysPeriod,
            Timestamp timeUpper,
            String timeUpperTz,
            Short sysTenant) {
        super(ParticipationHistory.PARTICIPATION_HISTORY);

        setId(id);
        setEventContext(eventContext);
        setPerformer(performer);
        setFunction(function);
        setMode(mode);
        setTimeLower(timeLower);
        setTimeLowerTz(timeLowerTz);
        setSysTransaction(sysTransaction);
        setSysPeriod(sysPeriod);
        setTimeUpper(timeUpper);
        setTimeUpperTz(timeUpperTz);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
