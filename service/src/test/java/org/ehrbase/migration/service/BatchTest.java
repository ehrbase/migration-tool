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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class BatchTest {

    @Test
    void page() {
        assertThat(Batch.page(0, 10, 100)).isEqualTo(new Batch(0, 10));
        assertThat(Batch.page(1, 10, 100)).isEqualTo(new Batch(10, 10));
        assertThat(Batch.page(9, 10, 100)).isEqualTo(new Batch(90, 10));
        assertThatThrownBy(() -> Batch.page(-1, 10, 100)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> Batch.page(10, 10, 100)).isInstanceOf(IndexOutOfBoundsException.class);

        assertThat(Batch.page(0, 10, 1)).isEqualTo(new Batch(0, 1));
        assertThat(Batch.page(0, 10, 9)).isEqualTo(new Batch(0, 9));
        assertThat(Batch.page(9, 10, 91)).isEqualTo(new Batch(90, 1));
        assertThat(Batch.page(9, 10, 99)).isEqualTo(new Batch(90, 9));
    }

    @Test
    void stream() {
        assertThat(Batch.stream(1, 0)).isEmpty();
        assertThat(Batch.stream(1, 1)).containsExactly(new Batch(0, 1));
        assertThat(Batch.stream(10, 10)).containsExactly(new Batch(0, 10));
        assertThat(Batch.stream(10, 9)).containsExactly(new Batch(0, 9));
        assertThat(Batch.stream(10, 30)).containsExactly(new Batch(0, 10), new Batch(10, 10), new Batch(20, 10));
        assertThat(Batch.stream(10, 29)).containsExactly(new Batch(0, 10), new Batch(10, 10), new Batch(20, 9));
    }

    @Test
    void streamParallel() {
        List<Integer> pageStarts = IntStream.range(0, 100).mapToObj(i -> 10 * i).toList();
        assertThat(Batch.stream(10, 999).map(Batch::start)).containsExactlyInAnyOrderElementsOf(pageStarts);
    }

    @Test
    void calcEndInclusive() {
        assertThat(new Batch(0, 1).calcEndInclusive()).isEqualTo(0);
        assertThat(new Batch(7, 9).calcEndInclusive()).isEqualTo(15);
        assertThat(new Batch(90, 10).calcEndInclusive()).isEqualTo(99);
    }
}
