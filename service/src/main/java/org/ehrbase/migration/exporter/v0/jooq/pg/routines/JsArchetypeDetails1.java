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
public class JsArchetypeDetails1 extends AbstractRoutine<JSONB> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.js_archetype_details.RETURN_VALUE</code>.
     */
    public static final Parameter<JSONB> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.JSONB, false, false);

    /**
     * The parameter <code>ehr.js_archetype_details.archetype_node_id</code>.
     */
    public static final Parameter<String> ARCHETYPE_NODE_ID =
            Internal.createParameter("archetype_node_id", SQLDataType.CLOB, false, false);

    /**
     * The parameter <code>ehr.js_archetype_details.template_id</code>.
     */
    public static final Parameter<String> TEMPLATE_ID =
            Internal.createParameter("template_id", SQLDataType.CLOB, false, false);

    /**
     * Create a new routine call instance
     */
    public JsArchetypeDetails1() {
        super("js_archetype_details", Ehr.EHR, SQLDataType.JSONB);

        setReturnParameter(RETURN_VALUE);
        addInParameter(ARCHETYPE_NODE_ID);
        addInParameter(TEMPLATE_ID);
        setOverloaded(true);
    }

    /**
     * Set the <code>archetype_node_id</code> parameter IN value to the routine
     */
    public void setArchetypeNodeId(String value) {
        setValue(ARCHETYPE_NODE_ID, value);
    }

    /**
     * Set the <code>archetype_node_id</code> parameter to the function to be
     * used with a {@link org.jooq.Select} statement
     */
    public void setArchetypeNodeId(Field<String> field) {
        setField(ARCHETYPE_NODE_ID, field);
    }

    /**
     * Set the <code>template_id</code> parameter IN value to the routine
     */
    public void setTemplateId(String value) {
        setValue(TEMPLATE_ID, value);
    }

    /**
     * Set the <code>template_id</code> parameter to the function to be used
     * with a {@link org.jooq.Select} statement
     */
    public void setTemplateId(Field<String> field) {
        setField(TEMPLATE_ID, field);
    }
}
