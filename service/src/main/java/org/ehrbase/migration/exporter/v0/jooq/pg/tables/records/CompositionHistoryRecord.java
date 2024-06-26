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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompositionHistory;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class CompositionHistoryRecord extends TableRecordImpl<CompositionHistoryRecord>
        implements Record15<
                UUID,
                UUID,
                UUID,
                Boolean,
                Boolean,
                String,
                Integer,
                UUID,
                Timestamp,
                SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                UUID,
                UUID,
                JSONB,
                JSONB,
                Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.composition_history.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.composition_history.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.composition_history.ehr_id</code>.
     */
    public void setEhrId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.composition_history.ehr_id</code>.
     */
    public UUID getEhrId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>ehr.composition_history.in_contribution</code>.
     */
    public void setInContribution(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.composition_history.in_contribution</code>.
     */
    public UUID getInContribution() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>ehr.composition_history.active</code>.
     */
    public void setActive(Boolean value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.composition_history.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(3);
    }

    /**
     * Setter for <code>ehr.composition_history.is_persistent</code>.
     */
    public void setIsPersistent(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.composition_history.is_persistent</code>.
     */
    public Boolean getIsPersistent() {
        return (Boolean) get(4);
    }

    /**
     * Setter for <code>ehr.composition_history.language</code>.
     */
    public void setLanguage(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.composition_history.language</code>.
     */
    public String getLanguage() {
        return (String) get(5);
    }

    /**
     * Setter for <code>ehr.composition_history.territory</code>.
     */
    public void setTerritory(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.composition_history.territory</code>.
     */
    public Integer getTerritory() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>ehr.composition_history.composer</code>.
     */
    public void setComposer(UUID value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.composition_history.composer</code>.
     */
    public UUID getComposer() {
        return (UUID) get(7);
    }

    /**
     * Setter for <code>ehr.composition_history.sys_transaction</code>.
     */
    public void setSysTransaction(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.composition_history.sys_transaction</code>.
     */
    public Timestamp getSysTransaction() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>ehr.composition_history.sys_period</code>.
     */
    public void setSysPeriod(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.composition_history.sys_period</code>.
     */
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> getSysPeriod() {
        return (SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>) get(9);
    }

    /**
     * Setter for <code>ehr.composition_history.has_audit</code>.
     */
    public void setHasAudit(UUID value) {
        set(10, value);
    }

    /**
     * Getter for <code>ehr.composition_history.has_audit</code>.
     */
    public UUID getHasAudit() {
        return (UUID) get(10);
    }

    /**
     * Setter for <code>ehr.composition_history.attestation_ref</code>.
     */
    public void setAttestationRef(UUID value) {
        set(11, value);
    }

    /**
     * Getter for <code>ehr.composition_history.attestation_ref</code>.
     */
    public UUID getAttestationRef() {
        return (UUID) get(11);
    }

    /**
     * Setter for <code>ehr.composition_history.feeder_audit</code>.
     */
    public void setFeederAudit(JSONB value) {
        set(12, value);
    }

    /**
     * Getter for <code>ehr.composition_history.feeder_audit</code>.
     */
    public JSONB getFeederAudit() {
        return (JSONB) get(12);
    }

    /**
     * Setter for <code>ehr.composition_history.links</code>.
     */
    public void setLinks(JSONB value) {
        set(13, value);
    }

    /**
     * Getter for <code>ehr.composition_history.links</code>.
     */
    public JSONB getLinks() {
        return (JSONB) get(13);
    }

    /**
     * Setter for <code>ehr.composition_history.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(14, value);
    }

    /**
     * Getter for <code>ehr.composition_history.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(14);
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row15<
                    UUID,
                    UUID,
                    UUID,
                    Boolean,
                    Boolean,
                    String,
                    Integer,
                    UUID,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    UUID,
                    UUID,
                    JSONB,
                    JSONB,
                    Short>
            fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    @Override
    public Row15<
                    UUID,
                    UUID,
                    UUID,
                    Boolean,
                    Boolean,
                    String,
                    Integer,
                    UUID,
                    Timestamp,
                    SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>,
                    UUID,
                    UUID,
                    JSONB,
                    JSONB,
                    Short>
            valuesRow() {
        return (Row15) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return CompositionHistory.COMPOSITION_HISTORY.ID;
    }

    @Override
    public Field<UUID> field2() {
        return CompositionHistory.COMPOSITION_HISTORY.EHR_ID;
    }

    @Override
    public Field<UUID> field3() {
        return CompositionHistory.COMPOSITION_HISTORY.IN_CONTRIBUTION;
    }

    @Override
    public Field<Boolean> field4() {
        return CompositionHistory.COMPOSITION_HISTORY.ACTIVE;
    }

    @Override
    public Field<Boolean> field5() {
        return CompositionHistory.COMPOSITION_HISTORY.IS_PERSISTENT;
    }

    @Override
    public Field<String> field6() {
        return CompositionHistory.COMPOSITION_HISTORY.LANGUAGE;
    }

    @Override
    public Field<Integer> field7() {
        return CompositionHistory.COMPOSITION_HISTORY.TERRITORY;
    }

    @Override
    public Field<UUID> field8() {
        return CompositionHistory.COMPOSITION_HISTORY.COMPOSER;
    }

    @Override
    public Field<Timestamp> field9() {
        return CompositionHistory.COMPOSITION_HISTORY.SYS_TRANSACTION;
    }

    @Override
    public Field<SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime>> field10() {
        return CompositionHistory.COMPOSITION_HISTORY.SYS_PERIOD;
    }

    @Override
    public Field<UUID> field11() {
        return CompositionHistory.COMPOSITION_HISTORY.HAS_AUDIT;
    }

    @Override
    public Field<UUID> field12() {
        return CompositionHistory.COMPOSITION_HISTORY.ATTESTATION_REF;
    }

    @Override
    public Field<JSONB> field13() {
        return CompositionHistory.COMPOSITION_HISTORY.FEEDER_AUDIT;
    }

    @Override
    public Field<JSONB> field14() {
        return CompositionHistory.COMPOSITION_HISTORY.LINKS;
    }

    @Override
    public Field<Short> field15() {
        return CompositionHistory.COMPOSITION_HISTORY.SYS_TENANT;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getEhrId();
    }

    @Override
    public UUID component3() {
        return getInContribution();
    }

    @Override
    public Boolean component4() {
        return getActive();
    }

    @Override
    public Boolean component5() {
        return getIsPersistent();
    }

    @Override
    public String component6() {
        return getLanguage();
    }

    @Override
    public Integer component7() {
        return getTerritory();
    }

    @Override
    public UUID component8() {
        return getComposer();
    }

    @Override
    public Timestamp component9() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> component10() {
        return getSysPeriod();
    }

    @Override
    public UUID component11() {
        return getHasAudit();
    }

    @Override
    public UUID component12() {
        return getAttestationRef();
    }

    @Override
    public JSONB component13() {
        return getFeederAudit();
    }

    @Override
    public JSONB component14() {
        return getLinks();
    }

    @Override
    public Short component15() {
        return getSysTenant();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getEhrId();
    }

    @Override
    public UUID value3() {
        return getInContribution();
    }

    @Override
    public Boolean value4() {
        return getActive();
    }

    @Override
    public Boolean value5() {
        return getIsPersistent();
    }

    @Override
    public String value6() {
        return getLanguage();
    }

    @Override
    public Integer value7() {
        return getTerritory();
    }

    @Override
    public UUID value8() {
        return getComposer();
    }

    @Override
    public Timestamp value9() {
        return getSysTransaction();
    }

    @Override
    public SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value10() {
        return getSysPeriod();
    }

    @Override
    public UUID value11() {
        return getHasAudit();
    }

    @Override
    public UUID value12() {
        return getAttestationRef();
    }

    @Override
    public JSONB value13() {
        return getFeederAudit();
    }

    @Override
    public JSONB value14() {
        return getLinks();
    }

    @Override
    public Short value15() {
        return getSysTenant();
    }

    @Override
    public CompositionHistoryRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value2(UUID value) {
        setEhrId(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value3(UUID value) {
        setInContribution(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value4(Boolean value) {
        setActive(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value5(Boolean value) {
        setIsPersistent(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value6(String value) {
        setLanguage(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value7(Integer value) {
        setTerritory(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value8(UUID value) {
        setComposer(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value9(Timestamp value) {
        setSysTransaction(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value10(SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value) {
        setSysPeriod(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value11(UUID value) {
        setHasAudit(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value12(UUID value) {
        setAttestationRef(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value13(JSONB value) {
        setFeederAudit(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value14(JSONB value) {
        setLinks(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord value15(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public CompositionHistoryRecord values(
            UUID value1,
            UUID value2,
            UUID value3,
            Boolean value4,
            Boolean value5,
            String value6,
            Integer value7,
            UUID value8,
            Timestamp value9,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> value10,
            UUID value11,
            UUID value12,
            JSONB value13,
            JSONB value14,
            Short value15) {
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
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CompositionHistoryRecord
     */
    public CompositionHistoryRecord() {
        super(CompositionHistory.COMPOSITION_HISTORY);
    }

    /**
     * Create a detached, initialised CompositionHistoryRecord
     */
    public CompositionHistoryRecord(
            UUID id,
            UUID ehrId,
            UUID inContribution,
            Boolean active,
            Boolean isPersistent,
            String language,
            Integer territory,
            UUID composer,
            Timestamp sysTransaction,
            SimpleEntry<java.time.OffsetDateTime, java.time.OffsetDateTime> sysPeriod,
            UUID hasAudit,
            UUID attestationRef,
            JSONB feederAudit,
            JSONB links,
            Short sysTenant) {
        super(CompositionHistory.COMPOSITION_HISTORY);

        setId(id);
        setEhrId(ehrId);
        setInContribution(inContribution);
        setActive(active);
        setIsPersistent(isPersistent);
        setLanguage(language);
        setTerritory(territory);
        setComposer(composer);
        setSysTransaction(sysTransaction);
        setSysPeriod(sysPeriod);
        setHasAudit(hasAudit);
        setAttestationRef(attestationRef);
        setFeederAudit(feederAudit);
        setLinks(links);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
