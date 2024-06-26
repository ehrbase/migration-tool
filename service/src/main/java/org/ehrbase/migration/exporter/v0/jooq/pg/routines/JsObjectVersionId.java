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
public class JsObjectVersionId extends AbstractRoutine<JSONB> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.js_object_version_id.RETURN_VALUE</code>.
     */
    public static final Parameter<JSONB> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.JSONB, false, false);

    /**
     * The parameter <code>ehr.js_object_version_id.version_id</code>.
     */
    public static final Parameter<String> VERSION_ID =
            Internal.createParameter("version_id", SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public JsObjectVersionId() {
        super("js_object_version_id", Ehr.EHR, SQLDataType.JSONB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(VERSION_ID);
    }

    /**
     * Set the <code>version_id</code> parameter IN value to the routine
     */
    public void setVersionId(String value) {
        setValue(VERSION_ID, value);
    }

    /**
     * Set the <code>version_id</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    public void setVersionId(Field<String> field) {
        setField(VERSION_ID, field);
    }
}
