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

import org.ehrbase.migration.exporter.v0.jooq.pg.tables.XjsonbArrayElements;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record1;
import org.jooq.Row1;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class XjsonbArrayElementsRecord extends TableRecordImpl<XjsonbArrayElementsRecord> implements Record1<JSONB> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.xjsonb_array_elements.xjsonb_array_elements</code>.
     */
    public void setXjsonbArrayElements(JSONB value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.xjsonb_array_elements.xjsonb_array_elements</code>.
     */
    public JSONB getXjsonbArrayElements() {
        return (JSONB) get(0);
    }

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row1<JSONB> fieldsRow() {
        return (Row1) super.fieldsRow();
    }

    @Override
    public Row1<JSONB> valuesRow() {
        return (Row1) super.valuesRow();
    }

    @Override
    public Field<JSONB> field1() {
        return XjsonbArrayElements.XJSONB_ARRAY_ELEMENTS.XJSONB_ARRAY_ELEMENTS_;
    }

    @Override
    public JSONB component1() {
        return getXjsonbArrayElements();
    }

    @Override
    public JSONB value1() {
        return getXjsonbArrayElements();
    }

    @Override
    public XjsonbArrayElementsRecord value1(JSONB value) {
        setXjsonbArrayElements(value);
        return this;
    }

    @Override
    public XjsonbArrayElementsRecord values(JSONB value1) {
        value1(value1);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached XjsonbArrayElementsRecord
     */
    public XjsonbArrayElementsRecord() {
        super(XjsonbArrayElements.XJSONB_ARRAY_ELEMENTS);
    }

    /**
     * Create a detached, initialised XjsonbArrayElementsRecord
     */
    public XjsonbArrayElementsRecord(JSONB xjsonbArrayElements) {
        super(XjsonbArrayElements.XJSONB_ARRAY_ELEMENTS);

        setXjsonbArrayElements(xjsonbArrayElements);
        resetChangedOnNotNull();
    }
}
