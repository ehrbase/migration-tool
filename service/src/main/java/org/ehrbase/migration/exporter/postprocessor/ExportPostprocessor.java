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
package org.ehrbase.migration.exporter.postprocessor;

import java.util.List;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;

public interface ExportPostprocessor {

    record MigrationError(String tenantId, String ehrId, String voId, String message) {}

    List<MigrationError> postExport(ExtendedEhr ehr, EhrSchemaData ehrSchemaData, EhrSchema t);
}
