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
package org.ehrbase.migration.service;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Batch(int start, int size) {
    /**
     *
     * @param page 0-indexed page nr
     * @param batchSize (regular) size of batches
     * @param fullSize aximum number of batches
     * @return
     */
    static Batch page(int page, int batchSize, int fullSize) {
        if (page < 0 || batchSize < 1) {
            throw new IndexOutOfBoundsException();
        }
        int start = page * batchSize;
        if (fullSize < start) {
            throw new IndexOutOfBoundsException();
        }
        if (start + batchSize > fullSize) {
            int remainingSize = fullSize - start;
            if (remainingSize < 1) {
                throw new IndexOutOfBoundsException();
            }
            return new Batch(start, remainingSize);
        } else {
            return new Batch(start, batchSize);
        }
    }

    static Stream<Batch> stream(int batchSize, int fullSize) {
        int fullBatches = fullSize / batchSize;

        int remainderCount = fullSize % batchSize;
        Optional<Batch> remainderBatch =
                Optional.of(remainderCount).filter(c -> c > 0).map(c -> new Batch(batchSize * fullBatches, c));

        return Stream.concat(
                IntStream.range(0, fullBatches).mapToObj(i -> Batch.page(i, batchSize, fullSize)),
                remainderBatch.stream());
    }

    public int calcEndInclusive() {
        return start + size - 1;
    }

    @Override
    public String toString() {
        return "%d - %d".formatted(start, calcEndInclusive());
    }
}
