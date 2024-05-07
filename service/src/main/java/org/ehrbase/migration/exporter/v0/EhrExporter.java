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
package org.ehrbase.migration.exporter.v0;

import java.util.List;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EhrRecord;
import org.jooq.DSLContext;

class EhrExporter {

    private final DSLContext dslContext;

    public EhrExporter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    protected List<ExtendedEhr> findEhrs(int start, int count) {
        List<ExtendedEhr> ehrs =
                dslContext.selectFrom(Ehr.EHR_).orderBy(Ehr.EHR_.ID).offset(start).limit(count).fetch().stream()
                        .map(EhrExporter::to)
                        .toList();
        if (ehrs.size() != count) {
            throw new IllegalStateException(
                    "%s EHRs were requested, but %s were returned".formatted(count, ehrs.size()));
        }
        return ehrs;
    }

    private static ExtendedEhr to(EhrRecord record) {
        ExtendedEhr extendedEhr = new ExtendedEhr();
        extendedEhr.setEhrId(record.getId());
        extendedEhr.setTimeCreated(AuditExporter.to(record.getDateCreated(), record.getDateCreatedTzid()));

        return extendedEhr;
    }
}
