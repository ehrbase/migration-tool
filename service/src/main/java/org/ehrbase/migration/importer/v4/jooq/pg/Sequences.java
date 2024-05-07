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
package org.ehrbase.migration.importer.v4.jooq.pg;

import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;

/**
 * Convenience access to all sequences in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Sequences {

    /**
     * The sequence <code>ehr.tenant_id_seq</code>
     */
    public static final Sequence<Long> TENANT_ID_SEQ = Internal.createSequence(
            "tenant_id_seq", Ehr.EHR, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
