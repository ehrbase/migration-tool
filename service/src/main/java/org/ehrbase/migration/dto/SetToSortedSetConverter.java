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
package org.ehrbase.migration.dto;

import static org.ehrbase.migration.exporter.v0.CompositionExporter.ORIGINAL_VERSION_COMPARATOR;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.nedap.archie.rm.archetyped.Locatable;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * During deserialize we need to restore the ordering
 */
public class SetToSortedSetConverter<T extends Locatable>
        extends StdConverter<Map<UUID, Set<OriginalVersion<T>>>, Map<UUID, SortedSet<OriginalVersion<T>>>> {

    @Override
    public Map<UUID, SortedSet<OriginalVersion<T>>> convert(Map<UUID, Set<OriginalVersion<T>>> value) {
        return value.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> toSortedSet(e.getValue())));
    }

    private SortedSet<OriginalVersion<T>> toSortedSet(Set<OriginalVersion<T>> value) {
        TreeSet<OriginalVersion<T>> versions = new TreeSet<>(ORIGINAL_VERSION_COMPARATOR);
        versions.addAll(value);
        return versions;
    }
}
