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
package org.ehrbase.migration.importer.v4;

import static org.ehrbase.migration.importer.v4.ImportService.executeBulkInsert;
import static org.ehrbase.migration.importer.v4.jooq.pg.tables.Ehr.EHR_;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.EhrRecord;
import org.jooq.DSLContext;

public class EhrImporter {

    private final DSLContext dslContext;

    public EhrImporter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public void writeEhr(List<ExtendedEhr> ehrs) {

        Stream<EhrRecord> records = ehrs.stream().map(this::to);

        executeBulkInsert(dslContext, records, EHR_);
    }

    private EhrRecord to(ExtendedEhr extendedEhr) {

        EhrRecord ehrRecord = dslContext.newRecord(EHR_);

        ehrRecord.setId(extendedEhr.getEhrId());
        ehrRecord.setCreationDate(
                OffsetDateTime.from(extendedEhr.getTimeCreated().getValue()));

        return ehrRecord;
    }
}
