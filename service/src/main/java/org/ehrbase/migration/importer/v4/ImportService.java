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
package org.ehrbase.migration.importer.v4;

import com.nedap.archie.rm.archetyped.Locatable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.jooq.DSLContext;
import org.jooq.Loader;
import org.jooq.Record;
import org.jooq.Table;

public interface ImportService {

    static <T extends Record> void executeBulkInsert(DSLContext context, Stream<T> recordStream, Table<?> table) {

        try {
            Loader<?> execute = context.loadInto(table)
                    .bulkAfter(500)
                    .loadRecords(recordStream)
                    .fields(table.fields())
                    .execute();

            if (!execute.result().errors().isEmpty()) {
                throw new RuntimeException(execute.result().errors().stream()
                        .map(e -> e.exception().getMessage())
                        .collect(Collectors.joining(";")));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    EhrSchemaImporter createImporter(EhrSchema ehrSchema, String schemaName);

    class VersionedObjectByEhr<L extends Locatable> {

        private final List<ExtendedEhr> extendedEhrs;
        private final Function<ExtendedEhr, List<VersionedObjectData<L>>> extractor;

        public VersionedObjectByEhr(
                List<ExtendedEhr> extendedEhrs, Function<ExtendedEhr, List<VersionedObjectData<L>>> extractor) {
            this.extendedEhrs = extendedEhrs;
            this.extractor = extractor;
        }

        public Stream<Pair<UUID, VersionedObjectData<L>>> streamOriginalVersions() {
            return extendedEhrs.stream().flatMap(e -> Optional.ofNullable(extractor.apply(e)).orElse(List.of()).stream()
                    .map(l -> Pair.of(e.getEhrId(), l)));
        }
    }
}
