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
package org.ehrbase.migration.exporter.postprocessor;

import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.ehr.EhrStatus;
import java.util.Collections;
import java.util.List;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;

public class EhrStatusPostprocessor implements ExportPostprocessor {
    @Override
    public List<MigrationError> postExport(ExtendedEhr ehr, EhrSchemaData ehrSchemaData, EhrSchema t) {

        ehr.getEhrStatus().originalVersions().forEach(this::updateArchetypeNodeId);

        return Collections.emptyList();
    }

    private void updateArchetypeNodeId(OriginalVersion<EhrStatus> ov) {

        if (!ov.getData().getArchetypeNodeId().startsWith("openEHR-EHR-EHR_STATUS.")) {
            ov.getData().setArchetypeNodeId("openEHR-EHR-EHR_STATUS.generic.v1");
        }
    }
}
