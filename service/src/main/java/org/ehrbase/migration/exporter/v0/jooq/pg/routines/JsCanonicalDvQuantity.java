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
import org.jooq.JSON;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class JsCanonicalDvQuantity extends AbstractRoutine<JSON> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.js_canonical_dv_quantity.RETURN_VALUE</code>.
     */
    public static final Parameter<JSON> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.JSON, false, false);

    /**
     * The parameter <code>ehr.js_canonical_dv_quantity.magnitude</code>.
     */
    public static final Parameter<Double> MAGNITUDE =
            Internal.createParameter("magnitude", SQLDataType.DOUBLE, false, false);

    /**
     * The parameter <code>ehr.js_canonical_dv_quantity.units</code>.
     */
    public static final Parameter<String> UNITS = Internal.createParameter("units", SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>ehr.js_canonical_dv_quantity._precision</code>.
     */
    public static final Parameter<Integer> _PRECISION =
            Internal.createParameter("_precision", SQLDataType.INTEGER, false, false);

    /**
     * The parameter <code>ehr.js_canonical_dv_quantity.accuracy_percent</code>.
     */
    public static final Parameter<Boolean> ACCURACY_PERCENT =
            Internal.createParameter("accuracy_percent", SQLDataType.BOOLEAN, false, false);

    /**
     * Create a new routine call instance
     */
    public JsCanonicalDvQuantity() {
        super("js_canonical_dv_quantity", Ehr.EHR, SQLDataType.JSON);

        setReturnParameter(RETURN_VALUE);
        addInParameter(MAGNITUDE);
        addInParameter(UNITS);
        addInParameter(_PRECISION);
        addInParameter(ACCURACY_PERCENT);
    }

    /**
     * Set the <code>magnitude</code> parameter IN value to the routine
     */
    public void setMagnitude(Double value) {
        setValue(MAGNITUDE, value);
    }

    /**
     * Set the <code>magnitude</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    public void setMagnitude(Field<Double> field) {
        setField(MAGNITUDE, field);
    }

    /**
     * Set the <code>units</code> parameter IN value to the routine
     */
    public void setUnits(String value) {
        setValue(UNITS, value);
    }

    /**
     * Set the <code>units</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    public void setUnits(Field<String> field) {
        setField(UNITS, field);
    }

    /**
     * Set the <code>_precision</code> parameter IN value to the routine
     */
    public void set_Precision(Integer value) {
        setValue(_PRECISION, value);
    }

    /**
     * Set the <code>_precision</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    public void set_Precision(Field<Integer> field) {
        setField(_PRECISION, field);
    }

    /**
     * Set the <code>accuracy_percent</code> parameter IN value to the routine
     */
    public void setAccuracyPercent(Boolean value) {
        setValue(ACCURACY_PERCENT, value);
    }

    /**
     * Set the <code>accuracy_percent</code> parameter to the function to be
     * used with a {@link org.jooq.Select} statement
     */
    public void setAccuracyPercent(Field<Boolean> field) {
        setField(ACCURACY_PERCENT, field);
    }
}
