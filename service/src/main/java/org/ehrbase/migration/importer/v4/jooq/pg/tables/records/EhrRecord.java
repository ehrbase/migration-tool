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
import org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class EhrRecord extends UpdatableRecordImpl<EhrRecord> implements Record2<UUID, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.ehr.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.ehr.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.ehr.creation_date</code>.
     */
    public void setCreationDate(OffsetDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.ehr.creation_date</code>.
     */
    public OffsetDateTime getCreationDate() {
        return (OffsetDateTime) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, OffsetDateTime> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UUID, OffsetDateTime> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Ehr.EHR_.ID;
    }

    @Override
    public Field<OffsetDateTime> field2() {
        return Ehr.EHR_.CREATION_DATE;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public OffsetDateTime component2() {
        return getCreationDate();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public OffsetDateTime value2() {
        return getCreationDate();
    }

    @Override
    public EhrRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EhrRecord value2(OffsetDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public EhrRecord values(UUID value1, OffsetDateTime value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EhrRecord
     */
    public EhrRecord() {
        super(Ehr.EHR_);
    }

    /**
     * Create a detached, initialised EhrRecord
     */
    public EhrRecord(UUID id, OffsetDateTime creationDate) {
        super(Ehr.EHR_);

        setId(id);
        setCreationDate(creationDate);
        resetChangedOnNotNull();
    }
}