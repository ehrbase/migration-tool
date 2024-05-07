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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Identifier;
import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;

/**
 * specifies an identifier for a party identified, more than one identifier is
 * possible
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class IdentifierRecord extends TableRecordImpl<IdentifierRecord>
        implements Record6<String, String, String, String, UUID, Short> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.identifier.id_value</code>.
     */
    public void setIdValue(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.identifier.id_value</code>.
     */
    public String getIdValue() {
        return (String) get(0);
    }

    /**
     * Setter for <code>ehr.identifier.issuer</code>.
     */
    public void setIssuer(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.identifier.issuer</code>.
     */
    public String getIssuer() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ehr.identifier.assigner</code>.
     */
    public void setAssigner(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.identifier.assigner</code>.
     */
    public String getAssigner() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ehr.identifier.type_name</code>.
     */
    public void setTypeName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.identifier.type_name</code>.
     */
    public String getTypeName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>ehr.identifier.party</code>.
     */
    public void setParty(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.identifier.party</code>.
     */
    public UUID getParty() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>ehr.identifier.sys_tenant</code>.
     */
    public void setSysTenant(Short value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.identifier.sys_tenant</code>.
     */
    public Short getSysTenant() {
        return (Short) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, String, String, String, UUID, Short> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<String, String, String, String, UUID, Short> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Identifier.IDENTIFIER.ID_VALUE;
    }

    @Override
    public Field<String> field2() {
        return Identifier.IDENTIFIER.ISSUER;
    }

    @Override
    public Field<String> field3() {
        return Identifier.IDENTIFIER.ASSIGNER;
    }

    @Override
    public Field<String> field4() {
        return Identifier.IDENTIFIER.TYPE_NAME;
    }

    @Override
    public Field<UUID> field5() {
        return Identifier.IDENTIFIER.PARTY;
    }

    @Override
    public Field<Short> field6() {
        return Identifier.IDENTIFIER.SYS_TENANT;
    }

    @Override
    public String component1() {
        return getIdValue();
    }

    @Override
    public String component2() {
        return getIssuer();
    }

    @Override
    public String component3() {
        return getAssigner();
    }

    @Override
    public String component4() {
        return getTypeName();
    }

    @Override
    public UUID component5() {
        return getParty();
    }

    @Override
    public Short component6() {
        return getSysTenant();
    }

    @Override
    public String value1() {
        return getIdValue();
    }

    @Override
    public String value2() {
        return getIssuer();
    }

    @Override
    public String value3() {
        return getAssigner();
    }

    @Override
    public String value4() {
        return getTypeName();
    }

    @Override
    public UUID value5() {
        return getParty();
    }

    @Override
    public Short value6() {
        return getSysTenant();
    }

    @Override
    public IdentifierRecord value1(String value) {
        setIdValue(value);
        return this;
    }

    @Override
    public IdentifierRecord value2(String value) {
        setIssuer(value);
        return this;
    }

    @Override
    public IdentifierRecord value3(String value) {
        setAssigner(value);
        return this;
    }

    @Override
    public IdentifierRecord value4(String value) {
        setTypeName(value);
        return this;
    }

    @Override
    public IdentifierRecord value5(UUID value) {
        setParty(value);
        return this;
    }

    @Override
    public IdentifierRecord value6(Short value) {
        setSysTenant(value);
        return this;
    }

    @Override
    public IdentifierRecord values(
            String value1, String value2, String value3, String value4, UUID value5, Short value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IdentifierRecord
     */
    public IdentifierRecord() {
        super(Identifier.IDENTIFIER);
    }

    /**
     * Create a detached, initialised IdentifierRecord
     */
    public IdentifierRecord(
            String idValue, String issuer, String assigner, String typeName, UUID party, Short sysTenant) {
        super(Identifier.IDENTIFIER);

        setIdValue(idValue);
        setIssuer(issuer);
        setAssigner(assigner);
        setTypeName(typeName);
        setParty(party);
        setSysTenant(sysTenant);
        resetChangedOnNotNull();
    }
}
