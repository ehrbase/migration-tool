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
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EhrStatus;
import org.jooq.Field;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.TableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class EhrStatusRecord extends TableRecordImpl<EhrStatusRecord>
        implements Record11<UUID, String, String, String, String, String, String, String, String, String, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.ehr_status.id</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.ehr_status.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ehr.ehr_status.ref</code>.
     */
    public void setRef(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.ref</code>.
     */
    public String getRef() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ehr.ehr_status.scheme</code>.
     */
    public void setScheme(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.scheme</code>.
     */
    public String getScheme() {
        return (String) get(3);
    }

    /**
     * Setter for <code>ehr.ehr_status.namespace</code>.
     */
    public void setNamespace(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.namespace</code>.
     */
    public String getNamespace() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ehr.ehr_status.type</code>.
     */
    public void setType(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.type</code>.
     */
    public String getType() {
        return (String) get(5);
    }

    /**
     * Setter for <code>ehr.ehr_status.id_value</code>.
     */
    public void setIdValue(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.id_value</code>.
     */
    public String getIdValue() {
        return (String) get(6);
    }

    /**
     * Setter for <code>ehr.ehr_status.issuer</code>.
     */
    public void setIssuer(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.issuer</code>.
     */
    public String getIssuer() {
        return (String) get(7);
    }

    /**
     * Setter for <code>ehr.ehr_status.assigner</code>.
     */
    public void setAssigner(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.assigner</code>.
     */
    public String getAssigner() {
        return (String) get(8);
    }

    /**
     * Setter for <code>ehr.ehr_status.type_name</code>.
     */
    public void setTypeName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.type_name</code>.
     */
    public String getTypeName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>ehr.ehr_status.party</code>.
     */
    public void setParty(UUID value) {
        set(10, value);
    }

    /**
     * Getter for <code>ehr.ehr_status.party</code>.
     */
    public UUID getParty() {
        return (UUID) get(10);
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row11<UUID, String, String, String, String, String, String, String, String, String, UUID> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<UUID, String, String, String, String, String, String, String, String, String, UUID> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return EhrStatus.EHR_STATUS.ID;
    }

    @Override
    public Field<String> field2() {
        return EhrStatus.EHR_STATUS.NAME;
    }

    @Override
    public Field<String> field3() {
        return EhrStatus.EHR_STATUS.REF;
    }

    @Override
    public Field<String> field4() {
        return EhrStatus.EHR_STATUS.SCHEME;
    }

    @Override
    public Field<String> field5() {
        return EhrStatus.EHR_STATUS.NAMESPACE;
    }

    @Override
    public Field<String> field6() {
        return EhrStatus.EHR_STATUS.TYPE;
    }

    @Override
    public Field<String> field7() {
        return EhrStatus.EHR_STATUS.ID_VALUE;
    }

    @Override
    public Field<String> field8() {
        return EhrStatus.EHR_STATUS.ISSUER;
    }

    @Override
    public Field<String> field9() {
        return EhrStatus.EHR_STATUS.ASSIGNER;
    }

    @Override
    public Field<String> field10() {
        return EhrStatus.EHR_STATUS.TYPE_NAME;
    }

    @Override
    public Field<UUID> field11() {
        return EhrStatus.EHR_STATUS.PARTY;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getRef();
    }

    @Override
    public String component4() {
        return getScheme();
    }

    @Override
    public String component5() {
        return getNamespace();
    }

    @Override
    public String component6() {
        return getType();
    }

    @Override
    public String component7() {
        return getIdValue();
    }

    @Override
    public String component8() {
        return getIssuer();
    }

    @Override
    public String component9() {
        return getAssigner();
    }

    @Override
    public String component10() {
        return getTypeName();
    }

    @Override
    public UUID component11() {
        return getParty();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getRef();
    }

    @Override
    public String value4() {
        return getScheme();
    }

    @Override
    public String value5() {
        return getNamespace();
    }

    @Override
    public String value6() {
        return getType();
    }

    @Override
    public String value7() {
        return getIdValue();
    }

    @Override
    public String value8() {
        return getIssuer();
    }

    @Override
    public String value9() {
        return getAssigner();
    }

    @Override
    public String value10() {
        return getTypeName();
    }

    @Override
    public UUID value11() {
        return getParty();
    }

    @Override
    public EhrStatusRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EhrStatusRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public EhrStatusRecord value3(String value) {
        setRef(value);
        return this;
    }

    @Override
    public EhrStatusRecord value4(String value) {
        setScheme(value);
        return this;
    }

    @Override
    public EhrStatusRecord value5(String value) {
        setNamespace(value);
        return this;
    }

    @Override
    public EhrStatusRecord value6(String value) {
        setType(value);
        return this;
    }

    @Override
    public EhrStatusRecord value7(String value) {
        setIdValue(value);
        return this;
    }

    @Override
    public EhrStatusRecord value8(String value) {
        setIssuer(value);
        return this;
    }

    @Override
    public EhrStatusRecord value9(String value) {
        setAssigner(value);
        return this;
    }

    @Override
    public EhrStatusRecord value10(String value) {
        setTypeName(value);
        return this;
    }

    @Override
    public EhrStatusRecord value11(UUID value) {
        setParty(value);
        return this;
    }

    @Override
    public EhrStatusRecord values(
            UUID value1,
            String value2,
            String value3,
            String value4,
            String value5,
            String value6,
            String value7,
            String value8,
            String value9,
            String value10,
            UUID value11) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EhrStatusRecord
     */
    public EhrStatusRecord() {
        super(EhrStatus.EHR_STATUS);
    }

    /**
     * Create a detached, initialised EhrStatusRecord
     */
    public EhrStatusRecord(
            UUID id,
            String name,
            String ref,
            String scheme,
            String namespace,
            String type,
            String idValue,
            String issuer,
            String assigner,
            String typeName,
            UUID party) {
        super(EhrStatus.EHR_STATUS);

        setId(id);
        setName(name);
        setRef(ref);
        setScheme(scheme);
        setNamespace(namespace);
        setType(type);
        setIdValue(idValue);
        setIssuer(issuer);
        setAssigner(assigner);
        setTypeName(typeName);
        setParty(party);
        resetChangedOnNotNull();
    }
}
