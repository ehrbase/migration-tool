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
public class JsTypedElementValue extends AbstractRoutine<JSONB> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.js_typed_element_value.RETURN_VALUE</code>.
     */
    public static final Parameter<JSONB> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.JSONB, false, false);

    /**
     * The parameter <code>ehr.js_typed_element_value._1</code>.
     */
    public static final Parameter<JSONB> _1 = Internal.createParameter("_1", SQLDataType.JSONB, false, true);

    /**
     * Create a new routine call instance
     */
    public JsTypedElementValue() {
        super("js_typed_element_value", Ehr.EHR, SQLDataType.JSONB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(_1);
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    public void set__1(JSONB value) {
        setValue(_1, value);
    }

    /**
     * Set the <code>_1</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    public void set__1(Field<JSONB> field) {
        setField(_1, field);
    }
}
