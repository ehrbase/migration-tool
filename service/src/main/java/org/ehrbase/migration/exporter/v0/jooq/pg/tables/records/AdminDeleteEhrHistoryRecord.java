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

import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AdminDeleteEhrHistory;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AdminDeleteEhrHistoryRecord extends TableRecordImpl<AdminDeleteEhrHistoryRecord>
        implements Record1<Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.admin_delete_ehr_history.num</code>.
     */
    public void setNum(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.admin_delete_ehr_history.num</code>.
     */
    public Integer getNum() {
        return (Integer) get(0);
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<Integer> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<Integer> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return AdminDeleteEhrHistory.ADMIN_DELETE_EHR_HISTORY.NUM;
    }

    @Override
    public Integer component1() {
        return getNum();
    }

    @Override
    public Integer value1() {
        return getNum();
    }

    @Override
    public AdminDeleteEhrHistoryRecord value1(Integer value) {
        setNum(value);
        return this;
    }

    @Override
    public AdminDeleteEhrHistoryRecord values(Integer value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AdminDeleteEhrHistoryRecord
     */
    public AdminDeleteEhrHistoryRecord() {
        super(AdminDeleteEhrHistory.ADMIN_DELETE_EHR_HISTORY);
    }

    /**
     * Create a detached, initialised AdminDeleteEhrHistoryRecord
     */
    public AdminDeleteEhrHistoryRecord(Integer num) {
        super(AdminDeleteEhrHistory.ADMIN_DELETE_EHR_HISTORY);

        setNum(num);
        resetChangedOnNotNull();
    }
}
