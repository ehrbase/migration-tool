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
import org.jooq.Field;
import org.jooq.Parameter;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;

/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class MigrationAuditSystemId extends AbstractRoutine<UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * The parameter <code>ehr.migration_audit_system_id.RETURN_VALUE</code>.
     */
    public static final Parameter<UUID> RETURN_VALUE =
            Internal.createParameter("RETURN_VALUE", SQLDataType.UUID, false, false);

    /**
     * The parameter <code>ehr.migration_audit_system_id.system_id</code>.
     */
    public static final Parameter<UUID> SYSTEM_ID =
            Internal.createParameter("system_id", SQLDataType.UUID, false, false);

    /**
     * Create a new routine call instance
     */
    public MigrationAuditSystemId() {
        super("migration_audit_system_id", Ehr.EHR, SQLDataType.UUID);

        setReturnParameter(RETURN_VALUE);
        addInParameter(SYSTEM_ID);
    }

    /**
     * Set the <code>system_id</code> parameter IN value to the routine
     */
    public void setSystemId(UUID value) {
        setValue(SYSTEM_ID, value);
    }

    /**
     * Set the <code>system_id</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    public void setSystemId(Field<UUID> field) {
        setField(SYSTEM_ID, field);
    }
}
