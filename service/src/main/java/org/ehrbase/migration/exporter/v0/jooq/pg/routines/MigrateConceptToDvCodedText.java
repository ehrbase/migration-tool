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
package org.ehrbase.migration.exporter.v0.jooq.pg.routines;

import java.util.UUID;
import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.udt.records.DvCodedTextRecord;
import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class MigrateConceptToDvCodedText extends AbstractRoutine<DvCodedTextRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter
     * <code>ehr.migrate_concept_to_dv_coded_text.RETURN_VALUE</code>.
     */
    public static final Parameter<DvCodedTextRecord> RETURN_VALUE = Internal.createParameter(
            "RETURN_VALUE",
            org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType(),
            false,
            false);

    /**
     * The parameter
     * <code>ehr.migrate_concept_to_dv_coded_text.concept_id</code>.
     */
    public static final Parameter<UUID> CONCEPT_ID =
            Internal.createParameter("concept_id", SQLDataType.UUID, false, false);

    /**
     * Create a new routine call instance
     */
    public MigrateConceptToDvCodedText() {
        super(
                "migrate_concept_to_dv_coded_text",
                Ehr.EHR,
                org.ehrbase.migration.exporter.v0.jooq.pg.udt.DvCodedText.DV_CODED_TEXT.getDataType());

        setReturnParameter(RETURN_VALUE);
        addInParameter(CONCEPT_ID);
    }

    /**
     * Set the <code>concept_id</code> parameter IN value to the routine
     */
    public void setConceptId(UUID value) {
        setValue(CONCEPT_ID, value);
    }

    /**
     * Set the <code>concept_id</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    public void setConceptId(Field<UUID> field) {
        setField(CONCEPT_ID, field);
    }
}
