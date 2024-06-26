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
import org.ehrbase.migration.importer.v4.jooq.pg.tables.StoredQuery;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class StoredQueryRecord extends UpdatableRecordImpl<StoredQueryRecord>
        implements Record6<String, String, String, String, String, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.stored_query.reverse_domain_name</code>.
     */
    public void setReverseDomainName(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.stored_query.reverse_domain_name</code>.
     */
    public String getReverseDomainName() {
        return (String) get(0);
    }

    /**
     * Setter for <code>ehr.stored_query.semantic_id</code>.
     */
    public void setSemanticId(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.stored_query.semantic_id</code>.
     */
    public String getSemanticId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>ehr.stored_query.semver</code>.
     */
    public void setSemver(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.stored_query.semver</code>.
     */
    public String getSemver() {
        return (String) get(2);
    }

    /**
     * Setter for <code>ehr.stored_query.query_text</code>.
     */
    public void setQueryText(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.stored_query.query_text</code>.
     */
    public String getQueryText() {
        return (String) get(3);
    }

    /**
     * Setter for <code>ehr.stored_query.type</code>.
     */
    public void setType(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.stored_query.type</code>.
     */
    public String getType() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ehr.stored_query.creation_date</code>.
     */
    public void setCreationDate(OffsetDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.stored_query.creation_date</code>.
     */
    public OffsetDateTime getCreationDate() {
        return (OffsetDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<String, String, String> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<String, String, String, String, String, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<String, String, String, String, String, OffsetDateTime> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return StoredQuery.STORED_QUERY.REVERSE_DOMAIN_NAME;
    }

    @Override
    public Field<String> field2() {
        return StoredQuery.STORED_QUERY.SEMANTIC_ID;
    }

    @Override
    public Field<String> field3() {
        return StoredQuery.STORED_QUERY.SEMVER;
    }

    @Override
    public Field<String> field4() {
        return StoredQuery.STORED_QUERY.QUERY_TEXT;
    }

    @Override
    public Field<String> field5() {
        return StoredQuery.STORED_QUERY.TYPE;
    }

    @Override
    public Field<OffsetDateTime> field6() {
        return StoredQuery.STORED_QUERY.CREATION_DATE;
    }

    @Override
    public String component1() {
        return getReverseDomainName();
    }

    @Override
    public String component2() {
        return getSemanticId();
    }

    @Override
    public String component3() {
        return getSemver();
    }

    @Override
    public String component4() {
        return getQueryText();
    }

    @Override
    public String component5() {
        return getType();
    }

    @Override
    public OffsetDateTime component6() {
        return getCreationDate();
    }

    @Override
    public String value1() {
        return getReverseDomainName();
    }

    @Override
    public String value2() {
        return getSemanticId();
    }

    @Override
    public String value3() {
        return getSemver();
    }

    @Override
    public String value4() {
        return getQueryText();
    }

    @Override
    public String value5() {
        return getType();
    }

    @Override
    public OffsetDateTime value6() {
        return getCreationDate();
    }

    @Override
    public StoredQueryRecord value1(String value) {
        setReverseDomainName(value);
        return this;
    }

    @Override
    public StoredQueryRecord value2(String value) {
        setSemanticId(value);
        return this;
    }

    @Override
    public StoredQueryRecord value3(String value) {
        setSemver(value);
        return this;
    }

    @Override
    public StoredQueryRecord value4(String value) {
        setQueryText(value);
        return this;
    }

    @Override
    public StoredQueryRecord value5(String value) {
        setType(value);
        return this;
    }

    @Override
    public StoredQueryRecord value6(OffsetDateTime value) {
        setCreationDate(value);
        return this;
    }

    @Override
    public StoredQueryRecord values(
            String value1, String value2, String value3, String value4, String value5, OffsetDateTime value6) {
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
     * Create a detached StoredQueryRecord
     */
    public StoredQueryRecord() {
        super(StoredQuery.STORED_QUERY);
    }

    /**
     * Create a detached, initialised StoredQueryRecord
     */
    public StoredQueryRecord(
            String reverseDomainName,
            String semanticId,
            String semver,
            String queryText,
            String type,
            OffsetDateTime creationDate) {
        super(StoredQuery.STORED_QUERY);

        setReverseDomainName(reverseDomainName);
        setSemanticId(semanticId);
        setSemver(semver);
        setQueryText(queryText);
        setType(type);
        setCreationDate(creationDate);
        resetChangedOnNotNull();
    }
}
