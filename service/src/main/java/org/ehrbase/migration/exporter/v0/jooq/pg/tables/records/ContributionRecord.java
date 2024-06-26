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

import java.util.UUID;
import org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionDataType;
import org.ehrbase.migration.exporter.v0.jooq.pg.enums.ContributionState;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Contribution;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * Contribution table, compositions reference this table
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ContributionRecord extends UpdatableRecordImpl<ContributionRecord>
        implements Record7<UUID, UUID, ContributionDataType, ContributionState, String, UUID, Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.contribution.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.contribution.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.contribution.ehr_id</code>.
     */
    public void setEhrId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.contribution.ehr_id</code>.
     */
    public UUID getEhrId() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>ehr.contribution.contribution_type</code>.
     */
    public void setContributionType(ContributionDataType value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.contribution.contribution_type</code>.
     */
    public ContributionDataType getContributionType() {
        return (ContributionDataType) get(2);
    }

    /**
     * Setter for <code>ehr.contribution.state</code>.
     */
    public void setState(ContributionState value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.contribution.state</code>.
     */
    public ContributionState getState() {
        return (ContributionState) get(3);
    }

    /**
     * Setter for <code>ehr.contribution.signature</code>.
     */
    public void setSignature(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.contribution.signature</code>.
     */
    public String getSignature() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ehr.contribution.has_audit</code>.
     */
    public void setHasAudit(UUID value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.contribution.has_audit</code>.
     */
    public UUID getHasAudit() {
        return (UUID) get(5);
    }

    /**
     * Setter for <code>ehr.contribution.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.contribution.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, Short> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<UUID, UUID, ContributionDataType, ContributionState, String, UUID, Short> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<UUID, UUID, ContributionDataType, ContributionState, String, UUID, Short> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Contribution.CONTRIBUTION.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Contribution.CONTRIBUTION.EHR_ID;
    }

    @Override
    public Field<ContributionDataType> field3() {
        return Contribution.CONTRIBUTION.CONTRIBUTION_TYPE;
    }

    @Override
    public Field<ContributionState> field4() {
        return Contribution.CONTRIBUTION.STATE;
    }

    @Override
    public Field<String> field5() {
        return Contribution.CONTRIBUTION.SIGNATURE;
    }

    @Override
    public Field<UUID> field6() {
        return Contribution.CONTRIBUTION.HAS_AUDIT;
    }

    @Override
    public Field<Short> field7() {
        return Contribution.CONTRIBUTION.SYS_TENANT;
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
    public ContributionDataType component3() {
        return getContributionType();
    }

    @Override
    public ContributionState component4() {
        return getState();
    }

    @Override
    public String component5() {
        return getSignature();
    }

    @Override
    public UUID component6() {
        return getHasAudit();
    }

    @Override
    public Short component7() {
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
    public ContributionDataType value3() {
        return getContributionType();
    }

    @Override
    public ContributionState value4() {
        return getState();
    }

    @Override
    public String value5() {
        return getSignature();
    }

    @Override
    public UUID value6() {
        return getHasAudit();
    }

    @Override
    public Short value7() {
        return getSysTenant();
    }

    @Override
    public ContributionRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ContributionRecord value2(UUID value) {
        setEhrId(value);
        return this;
    }

    @Override
    public ContributionRecord value3(ContributionDataType value) {
        setContributionType(value);
        return this;
    }

    @Override
    public ContributionRecord value4(ContributionState value) {
        setState(value);
        return this;
    }

    @Override
    public ContributionRecord value5(String value) {
        setSignature(value);
        return this;
    }

    @Override
    public ContributionRecord value6(UUID value) {
        setHasAudit(value);
        return this;
    }

    @Override
    public ContributionRecord value7(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public ContributionRecord values(
            UUID value1,
            UUID value2,
            ContributionDataType value3,
            ContributionState value4,
            String value5,
            UUID value6,
            Short value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ContributionRecord
     */
    public ContributionRecord() {
        super(Contribution.CONTRIBUTION);
    }

    /**
     * Create a detached, initialised ContributionRecord
     */
    public ContributionRecord(
            UUID id,
            UUID ehrId,
            ContributionDataType contributionType,
            ContributionState state,
            String signature,
            UUID hasAudit,
            Short sysTenant) {
        super(Contribution.CONTRIBUTION);

        setId(id);
        setEhrId(ehrId);
        setContributionType(contributionType);
        setState(state);
        setSignature(signature);
        setHasAudit(hasAudit);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
