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

import org.ehrbase.migration.exporter.v0.jooq.pg.tables.TerminologyProvider;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * openEHR identified terminology provider
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TerminologyProviderRecord extends UpdatableRecordImpl<TerminologyProviderRecord>
        implements Record4<String, String, String, Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.terminology_provider.code</code>.
     */
    public void setCode(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.terminology_provider.code</code>.
     */
    public String getCode() {
        return (String) get(0);
    }

    /**
     * Setter for <code>ehr.terminology_provider.source</code>.
     */
    public void setSource(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.terminology_provider.source</code>.
     */
    public String getSource() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ehr.terminology_provider.authority</code>.
     */
    public void setAuthority(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.terminology_provider.authority</code>.
     */
    public String getAuthority() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ehr.terminology_provider.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.terminology_provider.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<String, Short> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, String, String, Short> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, String, String, Short> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return TerminologyProvider.TERMINOLOGY_PROVIDER.CODE;
    }

    @Override
    public Field<String> field2() {
        return TerminologyProvider.TERMINOLOGY_PROVIDER.SOURCE;
    }

    @Override
    public Field<String> field3() {
        return TerminologyProvider.TERMINOLOGY_PROVIDER.AUTHORITY;
    }

    @Override
    public Field<Short> field4() {
        return TerminologyProvider.TERMINOLOGY_PROVIDER.SYS_TENANT;
    }

    @Override
    public String component1() {
        return getCode();
    }

    @Override
    public String component2() {
        return getSource();
    }

    @Override
    public String component3() {
        return getAuthority();
    }

    @Override
    public Short component4() {
        return getSysTenant();
    }

    @Override
    public String value1() {
        return getCode();
    }

    @Override
    public String value2() {
        return getSource();
    }

    @Override
    public String value3() {
        return getAuthority();
    }

    @Override
    public Short value4() {
        return getSysTenant();
    }

    @Override
    public TerminologyProviderRecord value1(String value) {
        setCode(value);
        return this;
    }

    @Override
    public TerminologyProviderRecord value2(String value) {
        setSource(value);
        return this;
    }

    @Override
    public TerminologyProviderRecord value3(String value) {
        setAuthority(value);
        return this;
    }

    @Override
    public TerminologyProviderRecord value4(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public TerminologyProviderRecord values(String value1, String value2, String value3, Short value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TerminologyProviderRecord
     */
    public TerminologyProviderRecord() {
        super(TerminologyProvider.TERMINOLOGY_PROVIDER);
    }

    /**
     * Create a detached, initialised TerminologyProviderRecord
     */
    public TerminologyProviderRecord(String code, String source, String authority, Short sysTenant) {
        super(TerminologyProvider.TERMINOLOGY_PROVIDER);

        setCode(code);
        setSource(source);
        setAuthority(authority);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
