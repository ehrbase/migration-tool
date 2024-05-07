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
package org.ehrbase.migration.importer.v4.jooq.pg.tables.records;

import java.time.OffsetDateTime;
import java.util.UUID;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.CompVersionHistory;
import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Record2;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class CompVersionHistoryRecord extends UpdatableRecordImpl<CompVersionHistoryRecord>
        implements Record10<UUID, UUID, UUID, UUID, UUID, Integer, OffsetDateTime, OffsetDateTime, Boolean, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.comp_version_history.vo_id</code>.
     */
    public void setVoId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.vo_id</code>.
     */
    public UUID getVoId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.comp_version_history.ehr_id</code>.
     */
    public void setEhrId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.ehr_id</code>.
     */
    public UUID getEhrId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>ehr.comp_version_history.contribution_id</code>.
     */
    public void setContributionId(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.contribution_id</code>.
     */
    public UUID getContributionId() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>ehr.comp_version_history.audit_id</code>.
     */
    public void setAuditId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.audit_id</code>.
     */
    public UUID getAuditId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>ehr.comp_version_history.template_id</code>.
     */
    public void setTemplateId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.template_id</code>.
     */
    public UUID getTemplateId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>ehr.comp_version_history.sys_version</code>.
     */
    public void setSysVersion(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.sys_version</code>.
     */
    public Integer getSysVersion() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>ehr.comp_version_history.sys_period_lower</code>.
     */
    public void setSysPeriodLower(OffsetDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.sys_period_lower</code>.
     */
    public OffsetDateTime getSysPeriodLower() {
        return (OffsetDateTime) get(6);
    }

    /**
     * Setter for <code>ehr.comp_version_history.sys_period_upper</code>.
     */
    public void setSysPeriodUpper(OffsetDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.sys_period_upper</code>.
     */
    public OffsetDateTime getSysPeriodUpper() {
        return (OffsetDateTime) get(7);
    }

    /**
     * Setter for <code>ehr.comp_version_history.sys_deleted</code>.
     */
    public void setSysDeleted(Boolean value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.sys_deleted</code>.
     */
    public Boolean getSysDeleted() {
        return (Boolean) get(8);
    }

    /**
     * Setter for <code>ehr.comp_version_history.root_concept</code>.
     */
    public void setRootConcept(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.comp_version_history.root_concept</code>.
     */
    public String getRootConcept() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<UUID, UUID, UUID, UUID, UUID, Integer, OffsetDateTime, OffsetDateTime, Boolean, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<UUID, UUID, UUID, UUID, UUID, Integer, OffsetDateTime, OffsetDateTime, Boolean, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return CompVersionHistory.COMP_VERSION_HISTORY.VO_ID;
    }

    @Override
    public Field<UUID> field2() {
        return CompVersionHistory.COMP_VERSION_HISTORY.EHR_ID;
    }

    @Override
    public Field<UUID> field3() {
        return CompVersionHistory.COMP_VERSION_HISTORY.CONTRIBUTION_ID;
    }

    @Override
    public Field<UUID> field4() {
        return CompVersionHistory.COMP_VERSION_HISTORY.AUDIT_ID;
    }

    @Override
    public Field<UUID> field5() {
        return CompVersionHistory.COMP_VERSION_HISTORY.TEMPLATE_ID;
    }

    @Override
    public Field<Integer> field6() {
        return CompVersionHistory.COMP_VERSION_HISTORY.SYS_VERSION;
    }

    @Override
    public Field<OffsetDateTime> field7() {
        return CompVersionHistory.COMP_VERSION_HISTORY.SYS_PERIOD_LOWER;
    }

    @Override
    public Field<OffsetDateTime> field8() {
        return CompVersionHistory.COMP_VERSION_HISTORY.SYS_PERIOD_UPPER;
    }

    @Override
    public Field<Boolean> field9() {
        return CompVersionHistory.COMP_VERSION_HISTORY.SYS_DELETED;
    }

    @Override
    public Field<String> field10() {
        return CompVersionHistory.COMP_VERSION_HISTORY.ROOT_CONCEPT;
    }

    @Override
    public UUID component1() {
        return getVoId();
    }

    @Override
    public UUID component2() {
        return getEhrId();
    }

    @Override
    public UUID component3() {
        return getContributionId();
    }

    @Override
    public UUID component4() {
        return getAuditId();
    }

    @Override
    public UUID component5() {
        return getTemplateId();
    }

    @Override
    public Integer component6() {
        return getSysVersion();
    }

    @Override
    public OffsetDateTime component7() {
        return getSysPeriodLower();
    }

    @Override
    public OffsetDateTime component8() {
        return getSysPeriodUpper();
    }

    @Override
    public Boolean component9() {
        return getSysDeleted();
    }

    @Override
    public String component10() {
        return getRootConcept();
    }

    @Override
    public UUID value1() {
        return getVoId();
    }

    @Override
    public UUID value2() {
        return getEhrId();
    }

    @Override
    public UUID value3() {
        return getContributionId();
    }

    @Override
    public UUID value4() {
        return getAuditId();
    }

    @Override
    public UUID value5() {
        return getTemplateId();
    }

    @Override
    public Integer value6() {
        return getSysVersion();
    }

    @Override
    public OffsetDateTime value7() {
        return getSysPeriodLower();
    }

    @Override
    public OffsetDateTime value8() {
        return getSysPeriodUpper();
    }

    @Override
    public Boolean value9() {
        return getSysDeleted();
    }

    @Override
    public String value10() {
        return getRootConcept();
    }

    @Override
    public CompVersionHistoryRecord value1(UUID value) {
        setVoId(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value2(UUID value) {
        setEhrId(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value3(UUID value) {
        setContributionId(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value4(UUID value) {
        setAuditId(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value5(UUID value) {
        setTemplateId(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value6(Integer value) {
        setSysVersion(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value7(OffsetDateTime value) {
        setSysPeriodLower(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value8(OffsetDateTime value) {
        setSysPeriodUpper(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value9(Boolean value) {
        setSysDeleted(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord value10(String value) {
        setRootConcept(value);
        return this;
    }

    @Override
    public CompVersionHistoryRecord values(
            UUID value1,
            UUID value2,
            UUID value3,
            UUID value4,
            UUID value5,
            Integer value6,
            OffsetDateTime value7,
            OffsetDateTime value8,
            Boolean value9,
            String value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CompVersionHistoryRecord
     */
    public CompVersionHistoryRecord() {
        super(CompVersionHistory.COMP_VERSION_HISTORY);
    }

    /**
     * Create a detached, initialised CompVersionHistoryRecord
     */
    public CompVersionHistoryRecord(
            UUID voId,
            UUID ehrId,
            UUID contributionId,
            UUID auditId,
            UUID templateId,
            Integer sysVersion,
            OffsetDateTime sysPeriodLower,
            OffsetDateTime sysPeriodUpper,
            Boolean sysDeleted,
            String rootConcept) {
        super(CompVersionHistory.COMP_VERSION_HISTORY);

        setVoId(voId);
        setEhrId(ehrId);
        setContributionId(contributionId);
        setAuditId(auditId);
        setTemplateId(templateId);
        setSysVersion(sysVersion);
        setSysPeriodLower(sysPeriodLower);
        setSysPeriodUpper(sysPeriodUpper);
        setSysDeleted(sysDeleted);
        setRootConcept(rootConcept);
        resetChangedOnNotNull();
    }
}