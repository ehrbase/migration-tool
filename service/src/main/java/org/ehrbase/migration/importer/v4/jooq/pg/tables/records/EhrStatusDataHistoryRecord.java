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

import java.util.UUID;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.EhrStatusDataHistory;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record15;
import org.jooq.Record3;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class EhrStatusDataHistoryRecord extends UpdatableRecordImpl<EhrStatusDataHistoryRecord>
        implements Record15<
                UUID,
                Integer,
                UUID,
                Integer,
                String,
                String,
                String,
                String,
                String,
                String,
                String,
                String,
                Integer,
                JSONB,
                Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>ehr.ehr_status_data_history.vo_id</code>.
     */
    public void setVoId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.vo_id</code>.
     */
    public UUID getVoId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.num</code>.
     */
    public void setNum(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.num</code>.
     */
    public Integer getNum() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.ehr_id</code>.
     */
    public void setEhrId(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.ehr_id</code>.
     */
    public UUID getEhrId() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.citem_num</code>.
     */
    public void setCitemNum(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.citem_num</code>.
     */
    public Integer getCitemNum() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.rm_entity</code>.
     */
    public void setRmEntity(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.rm_entity</code>.
     */
    public String getRmEntity() {
        return (String) get(4);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_concept</code>.
     */
    public void setEntityConcept(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_concept</code>.
     */
    public String getEntityConcept() {
        return (String) get(5);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_name</code>.
     */
    public void setEntityName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_name</code>.
     */
    public String getEntityName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_attribute</code>.
     */
    public void setEntityAttribute(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_attribute</code>.
     */
    public String getEntityAttribute() {
        return (String) get(7);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_path</code>.
     */
    public void setEntityPath(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_path</code>.
     */
    public String getEntityPath() {
        return (String) get(8);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_path_cap</code>.
     */
    public void setEntityPathCap(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_path_cap</code>.
     */
    public String getEntityPathCap() {
        return (String) get(9);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_idx</code>.
     */
    public void setEntityIdx(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_idx</code>.
     */
    public String getEntityIdx() {
        return (String) get(10);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_idx_cap</code>.
     */
    public void setEntityIdxCap(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_idx_cap</code>.
     */
    public String getEntityIdxCap() {
        return (String) get(11);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.entity_idx_len</code>.
     */
    public void setEntityIdxLen(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.entity_idx_len</code>.
     */
    public Integer getEntityIdxLen() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.data</code>.
     */
    public void setData(JSONB value) {
        set(13, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.data</code>.
     */
    public JSONB getData() {
        return (JSONB) get(13);
    }

    /**
     * Setter for <code>ehr.ehr_status_data_history.sys_version</code>.
     */
    public void setSysVersion(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>ehr.ehr_status_data_history.sys_version</code>.
     */
    public Integer getSysVersion() {
        return (Integer) get(14);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<UUID, Integer, Integer> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record15 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row15<
                    UUID,
                    Integer,
                    UUID,
                    Integer,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    Integer,
                    JSONB,
                    Integer>
            fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    @Override
    public Row15<
                    UUID,
                    Integer,
                    UUID,
                    Integer,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    String,
                    Integer,
                    JSONB,
                    Integer>
            valuesRow() {
        return (Row15) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.VO_ID;
    }

    @Override
    public Field<Integer> field2() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.NUM;
    }

    @Override
    public Field<UUID> field3() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.EHR_ID;
    }

    @Override
    public Field<Integer> field4() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.CITEM_NUM;
    }

    @Override
    public Field<String> field5() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.RM_ENTITY;
    }

    @Override
    public Field<String> field6() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_CONCEPT;
    }

    @Override
    public Field<String> field7() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_NAME;
    }

    @Override
    public Field<String> field8() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_ATTRIBUTE;
    }

    @Override
    public Field<String> field9() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_PATH;
    }

    @Override
    public Field<String> field10() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_PATH_CAP;
    }

    @Override
    public Field<String> field11() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_IDX;
    }

    @Override
    public Field<String> field12() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_IDX_CAP;
    }

    @Override
    public Field<Integer> field13() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.ENTITY_IDX_LEN;
    }

    @Override
    public Field<JSONB> field14() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.DATA;
    }

    @Override
    public Field<Integer> field15() {
        return EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY.SYS_VERSION;
    }

    @Override
    public UUID component1() {
        return getVoId();
    }

    @Override
    public Integer component2() {
        return getNum();
    }

    @Override
    public UUID component3() {
        return getEhrId();
    }

    @Override
    public Integer component4() {
        return getCitemNum();
    }

    @Override
    public String component5() {
        return getRmEntity();
    }

    @Override
    public String component6() {
        return getEntityConcept();
    }

    @Override
    public String component7() {
        return getEntityName();
    }

    @Override
    public String component8() {
        return getEntityAttribute();
    }

    @Override
    public String component9() {
        return getEntityPath();
    }

    @Override
    public String component10() {
        return getEntityPathCap();
    }

    @Override
    public String component11() {
        return getEntityIdx();
    }

    @Override
    public String component12() {
        return getEntityIdxCap();
    }

    @Override
    public Integer component13() {
        return getEntityIdxLen();
    }

    @Override
    public JSONB component14() {
        return getData();
    }

    @Override
    public Integer component15() {
        return getSysVersion();
    }

    @Override
    public UUID value1() {
        return getVoId();
    }

    @Override
    public Integer value2() {
        return getNum();
    }

    @Override
    public UUID value3() {
        return getEhrId();
    }

    @Override
    public Integer value4() {
        return getCitemNum();
    }

    @Override
    public String value5() {
        return getRmEntity();
    }

    @Override
    public String value6() {
        return getEntityConcept();
    }

    @Override
    public String value7() {
        return getEntityName();
    }

    @Override
    public String value8() {
        return getEntityAttribute();
    }

    @Override
    public String value9() {
        return getEntityPath();
    }

    @Override
    public String value10() {
        return getEntityPathCap();
    }

    @Override
    public String value11() {
        return getEntityIdx();
    }

    @Override
    public String value12() {
        return getEntityIdxCap();
    }

    @Override
    public Integer value13() {
        return getEntityIdxLen();
    }

    @Override
    public JSONB value14() {
        return getData();
    }

    @Override
    public Integer value15() {
        return getSysVersion();
    }

    @Override
    public EhrStatusDataHistoryRecord value1(UUID value) {
        setVoId(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value2(Integer value) {
        setNum(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value3(UUID value) {
        setEhrId(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value4(Integer value) {
        setCitemNum(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value5(String value) {
        setRmEntity(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value6(String value) {
        setEntityConcept(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value7(String value) {
        setEntityName(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value8(String value) {
        setEntityAttribute(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value9(String value) {
        setEntityPath(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value10(String value) {
        setEntityPathCap(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value11(String value) {
        setEntityIdx(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value12(String value) {
        setEntityIdxCap(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value13(Integer value) {
        setEntityIdxLen(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value14(JSONB value) {
        setData(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord value15(Integer value) {
        setSysVersion(value);
        return this;
    }

    @Override
    public EhrStatusDataHistoryRecord values(
            UUID value1,
            Integer value2,
            UUID value3,
            Integer value4,
            String value5,
            String value6,
            String value7,
            String value8,
            String value9,
            String value10,
            String value11,
            String value12,
            Integer value13,
            JSONB value14,
            Integer value15) {
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
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EhrStatusDataHistoryRecord
     */
    public EhrStatusDataHistoryRecord() {
        super(EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY);
    }

    /**
     * Create a detached, initialised EhrStatusDataHistoryRecord
     */
    public EhrStatusDataHistoryRecord(
            UUID voId,
            Integer num,
            UUID ehrId,
            Integer citemNum,
            String rmEntity,
            String entityConcept,
            String entityName,
            String entityAttribute,
            String entityPath,
            String entityPathCap,
            String entityIdx,
            String entityIdxCap,
            Integer entityIdxLen,
            JSONB data,
            Integer sysVersion) {
        super(EhrStatusDataHistory.EHR_STATUS_DATA_HISTORY);

        setVoId(voId);
        setNum(num);
        setEhrId(ehrId);
        setCitemNum(citemNum);
        setRmEntity(rmEntity);
        setEntityConcept(entityConcept);
        setEntityName(entityName);
        setEntityAttribute(entityAttribute);
        setEntityPath(entityPath);
        setEntityPathCap(entityPathCap);
        setEntityIdx(entityIdx);
        setEntityIdxCap(entityIdxCap);
        setEntityIdxLen(entityIdxLen);
        setData(data);
        setSysVersion(sysVersion);
        resetChangedOnNotNull();
    }
}
