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
import org.ehrbase.migration.exporter.v0.jooq.pg.enums.EntryType;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Entry;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record14;
import org.jooq.Record2;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * this table hold the actual archetyped data values (fromBinder a template)
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class EntryRecord extends UpdatableRecordImpl<EntryRecord>
        implements Record14<
                UUID,
                UUID,
                Integer,
                EntryType,
                String,
                UUID,
                String,
                DvCodedTextRecord,
                JSONB,
                Timestamp,
                SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                String,
                DvCodedTextRecord,
                Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.entry.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.entry.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.entry.composition_id</code>.
     */
    public void setCompositionId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.entry.composition_id</code>.
     */
    public UUID getCompositionId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>ehr.entry.sequence</code>.
     */
    public void setSequence(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.entry.sequence</code>.
     */
    public Integer getSequence() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>ehr.entry.item_type</code>.
     */
    public void setItemType(EntryType value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.entry.item_type</code>.
     */
    public EntryType getItemType() {
        return (EntryType) get(3);
    }

    /**
     * Setter for <code>ehr.entry.template_id</code>.
     */
    public void setTemplateId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.entry.template_id</code>.
     */
    public String getTemplateId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ehr.entry.template_uuid</code>.
     */
    public void setTemplateUuid(UUID value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.entry.template_uuid</code>.
     */
    public UUID getTemplateUuid() {
        return (UUID) get(5);
    }

    /**
     * Setter for <code>ehr.entry.archetype_id</code>.
     */
    public void setArchetypeId(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.entry.archetype_id</code>.
     */
    public String getArchetypeId() {
        return (String) get(6);
    }

    /**
     * Setter for <code>ehr.entry.category</code>.
     */
    public void setCategory(DvCodedTextRecord value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.entry.category</code>.
     */
    public DvCodedTextRecord getCategory() {
        return (DvCodedTextRecord) get(7);
    }

    /**
     * Setter for <code>ehr.entry.entry</code>.
     */
    public void setEntry(JSONB value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.entry.entry</code>.
     */
    public JSONB getEntry() {
        return (JSONB) get(8);
    }

    /**
     * Setter for <code>ehr.entry.sys_transaction</code>.
     */
    public void setSysTransaction(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.entry.sys_transaction</code>.
     */
    public Timestamp getSysTransaction() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>ehr.entry.sys_period</code>.
     */
    public void setSysPeriod(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        set(10, value);
    }

    /**
     * Getter for <code>ehr.entry.sys_period</code>.
     */
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> getSysPeriod() {
        return (SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>) get(10);
    }

    /**
     * Setter for <code>ehr.entry.rm_version</code>.
     */
    public void setRmVersion(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>ehr.entry.rm_version</code>.
     */
    public String getRmVersion() {
        return (String) get(11);
    }

    /**
     * Setter for <code>ehr.entry.name</code>.
     */
    public void setName(DvCodedTextRecord value) {
        set(12, value);
    }

    /**
     * Getter for <code>ehr.entry.name</code>.
     */
    public DvCodedTextRecord getName() {
        return (DvCodedTextRecord) get(12);
    }

    /**
     * Setter for <code>ehr.entry.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(13, value);
    }

    /**
     * Getter for <code>ehr.entry.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, Short> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row14<
                    UUID,
                    UUID,
                    Integer,
                    EntryType,
                    String,
                    UUID,
                    String,
                    DvCodedTextRecord,
                    JSONB,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    String,
                    DvCodedTextRecord,
                    Short>
            fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    @Override
    public Row14<
                    UUID,
                    UUID,
                    Integer,
                    EntryType,
                    String,
                    UUID,
                    String,
                    DvCodedTextRecord,
                    JSONB,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    String,
                    DvCodedTextRecord,
                    Short>
            valuesRow() {
        return (Row14) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Entry.ENTRY.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Entry.ENTRY.COMPOSITION_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Entry.ENTRY.SEQUENCE;
    }

    @Override
    public Field<EntryType> field4() {
        return Entry.ENTRY.ITEM_TYPE;
    }

    @Override
    public Field<String> field5() {
        return Entry.ENTRY.TEMPLATE_ID;
    }

    @Override
    public Field<UUID> field6() {
        return Entry.ENTRY.TEMPLATE_UUID;
    }

    @Override
    public Field<String> field7() {
        return Entry.ENTRY.ARCHETYPE_ID;
    }

    @Override
    public Field<DvCodedTextRecord> field8() {
        return Entry.ENTRY.CATEGORY;
    }

    @Override
    public Field<JSONB> field9() {
        return Entry.ENTRY.ENTRY_;
    }

    @Override
    public Field<Timestamp> field10() {
        return Entry.ENTRY.SYS_TRANSACTION;
    }

    @Override
    public Field<SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>> field11() {
        return Entry.ENTRY.SYS_PERIOD;
    }

    @Override
    public Field<String> field12() {
        return Entry.ENTRY.RM_VERSION;
    }

    @Override
    public Field<DvCodedTextRecord> field13() {
        return Entry.ENTRY.NAME;
    }

    @Override
    public Field<Short> field14() {
        return Entry.ENTRY.SYS_TENANT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getCompositionId();
    }

    @Override
    public Integer component3() {
        return getSequence();
    }

    @Override
    public EntryType component4() {
        return getItemType();
    }

    @Override
    public String component5() {
        return getTemplateId();
    }

    @Override
    public UUID component6() {
        return getTemplateUuid();
    }

    @Override
    public String component7() {
        return getArchetypeId();
    }

    @Override
    public DvCodedTextRecord component8() {
        return getCategory();
    }

    @Override
    public JSONB component9() {
        return getEntry();
    }

    @Override
    public Timestamp component10() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> component11() {
        return getSysPeriod();
    }

    @Override
    public String component12() {
        return getRmVersion();
    }

    @Override
    public DvCodedTextRecord component13() {
        return getName();
    }

    @Override
    public Short component14() {
        return getSysTenant();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getCompositionId();
    }

    @Override
    public Integer value3() {
        return getSequence();
    }

    @Override
    public EntryType value4() {
        return getItemType();
    }

    @Override
    public String value5() {
        return getTemplateId();
    }

    @Override
    public UUID value6() {
        return getTemplateUuid();
    }

    @Override
    public String value7() {
        return getArchetypeId();
    }

    @Override
    public DvCodedTextRecord value8() {
        return getCategory();
    }

    @Override
    public JSONB value9() {
        return getEntry();
    }

    @Override
    public Timestamp value10() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value11() {
        return getSysPeriod();
    }

    @Override
    public String value12() {
        return getRmVersion();
    }

    @Override
    public DvCodedTextRecord value13() {
        return getName();
    }

    @Override
    public Short value14() {
        return getSysTenant();
    }

    @Override
    public EntryRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EntryRecord value2(UUID value) {
        setCompositionId(value);
        return this;
    }

    @Override
    public EntryRecord value3(Integer value) {
        setSequence(value);
        return this;
    }

    @Override
    public EntryRecord value4(EntryType value) {
        setItemType(value);
        return this;
    }

    @Override
    public EntryRecord value5(String value) {
        setTemplateId(value);
        return this;
    }

    @Override
    public EntryRecord value6(UUID value) {
        setTemplateUuid(value);
        return this;
    }

    @Override
    public EntryRecord value7(String value) {
        setArchetypeId(value);
        return this;
    }

    @Override
    public EntryRecord value8(DvCodedTextRecord value) {
        setCategory(value);
        return this;
    }

    @Override
    public EntryRecord value9(JSONB value) {
        setEntry(value);
        return this;
    }

    @Override
    public EntryRecord value10(Timestamp value) {
        setSysTransaction(value);
        return this;
    }

    @Override
    public EntryRecord value11(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        setSysPeriod(value);
        return this;
    }

    @Override
    public EntryRecord value12(String value) {
        setRmVersion(value);
        return this;
    }

    @Override
    public EntryRecord value13(DvCodedTextRecord value) {
        setName(value);
        return this;
    }

    @Override
    public EntryRecord value14(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public EntryRecord values(
            UUID value1,
            UUID value2,
            Integer value3,
            EntryType value4,
            String value5,
            UUID value6,
            String value7,
            DvCodedTextRecord value8,
            JSONB value9,
            Timestamp value10,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value11,
            String value12,
            DvCodedTextRecord value13,
            Short value14) {
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
        value13(value13);
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EntryRecord
     */
    public EntryRecord() {
        super(Entry.ENTRY);
    }

    /**
     * Create a detached, initialised EntryRecord
     */
    public EntryRecord(
            UUID id,
            UUID compositionId,
            Integer sequence,
            EntryType itemType,
            String templateId,
            UUID templateUuid,
            String archetypeId,
            DvCodedTextRecord category,
            JSONB entry,
            Timestamp sysTransaction,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> sysPeriod,
            String rmVersion,
            DvCodedTextRecord name,
            Short sysTenant) {
        super(Entry.ENTRY);

        setId(id);
        setCompositionId(compositionId);
        setSequence(sequence);
        setItemType(itemType);
        setTemplateId(templateId);
        setTemplateUuid(templateUuid);
        setArchetypeId(archetypeId);
        setCategory(category);
        setEntry(entry);
        setSysTransaction(sysTransaction);
        setSysPeriod(sysPeriod);
        setRmVersion(rmVersion);
        setName(name);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
