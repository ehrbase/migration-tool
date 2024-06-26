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

import org.ehrbase.migration.exporter.v0.jooq.pg.Ehr;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class JsTermMappings extends AbstractRoutine<JSONB[]> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.js_term_mappings.RETURN_VALUE</code>.
     */
    public static final Parameter<JSONB[]> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.JSONB.array(), false, false);

    /**
     * The parameter <code>ehr.js_term_mappings.mappings</code>.
     */
    public static final Parameter<String[]> MAPPINGS =
            Internal.createParameter("mappings", SQLDataType.CLOB.array(), false, false);

    /**
     * Create a new routine call instance
     */
    public JsTermMappings() {
        super("js_term_mappings", Ehr.EHR, SQLDataType.JSONB.array());

        setReturnParameter(RETURN_VALUE);
        addInParameter(MAPPINGS);
    }

    /**
     * Set the <code>mappings</code> parameter IN value to the routine
     */
    public void setMappings(String[] value) {
        setValue(MAPPINGS, value);
    }

    /**
     * Set the <code>mappings</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    public void setMappings(Field<String[]> field) {
        setField(MAPPINGS, field);
    }
}
