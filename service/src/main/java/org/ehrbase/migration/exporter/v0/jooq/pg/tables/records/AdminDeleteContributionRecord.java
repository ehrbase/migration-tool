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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AdminDeleteContribution;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AdminDeleteContributionRecord extends TableRecordImpl<AdminDeleteContributionRecord>
        implements Record2<Integer, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.admin_delete_contribution.num</code>.
     */
    public void setNum(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.admin_delete_contribution.num</code>.
     */
    public Integer getNum() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>ehr.admin_delete_contribution.audit</code>.
     */
    public void setAudit(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.admin_delete_contribution.audit</code>.
     */
    public UUID getAudit() {
        return (UUID) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, UUID> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AdminDeleteContribution.ADMIN_DELETE_CONTRIBUTION.NUM;
    }

    @Override
    public Field<UUID> field2() {
        return AdminDeleteContribution.ADMIN_DELETE_CONTRIBUTION.AUDIT;
    }

    @Override
    public Integer component1() {
        return getNum();
    }

    @Override
    public UUID component2() {
        return getAudit();
    }

    @Override
    public Integer value1() {
        return getNum();
    }

    @Override
    public UUID value2() {
        return getAudit();
    }

    @Override
    public AdminDeleteContributionRecord value1(Integer value) {
        setNum(value);
        return this;
    }

    @Override
    public AdminDeleteContributionRecord value2(UUID value) {
        setAudit(value);
        return this;
    }

    @Override
    public AdminDeleteContributionRecord values(Integer value1, UUID value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AdminDeleteContributionRecord
     */
    public AdminDeleteContributionRecord() {
        super(AdminDeleteContribution.ADMIN_DELETE_CONTRIBUTION);
    }

    /**
     * Create a detached, initialised AdminDeleteContributionRecord
     */
    public AdminDeleteContributionRecord(Integer num, UUID audit) {
        super(AdminDeleteContribution.ADMIN_DELETE_CONTRIBUTION);

        setNum(num);
        setAudit(audit);
        resetChangedOnNotNull();
    }
}
