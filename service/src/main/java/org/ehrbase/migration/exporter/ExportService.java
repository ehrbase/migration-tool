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
package org.ehrbase.migration.exporter;

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.dto.VersionedObjectData;

/**
 * The ExportService is stateful (currentTenant).
 */
public interface ExportService {

    List<EhrSchema> getTenants();

    int getEhrCount();

    List<ExtendedEhr> findEhrs(int start, int count);

    void setCurrentTenant(EhrSchema ehrSchema);

    Map<UUID, List<VersionedObjectData<Composition>>> findCompositions(Collection<UUID> uuids, EhrSchema tenant);

    Map<UUID, List<VersionedObjectData<EhrStatus>>> findStatus(Collection<UUID> uuids, EhrSchema tenant);

    Map<UUID, List<VersionedObjectData<Folder>>> findFolder(Collection<UUID> uuids, EhrSchema tenant);

    Map<UUID, List<Contribution>> findContributions(Collection<UUID> ehrIds);

    EhrSchemaData findEhrSchemaData();

    void postProcess(ExtendedEhr ehr);
}
