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

import com.nedap.archie.rm.changecontrol.Contribution;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.directory.Folder;
import com.nedap.archie.rm.ehr.EhrStatus;
import java.util.List;
import java.util.UUID;

public class ExtendedEhr {

    private UUID ehrId;
    private List<Contribution> contributions;

    private VersionedObjectData<EhrStatus> ehrStatus;

    private List<VersionedObjectData<Composition>> compositions;

    private UUID directory;

    private List<VersionedObjectData<Folder>> folders;

    private DvDateTime timeCreated;

    public UUID getEhrId() {
        return ehrId;
    }

    public void setEhrId(UUID ehrId) {
        this.ehrId = ehrId;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(List<Contribution> contributions) {
        this.contributions = contributions;
    }

    public VersionedObjectData<EhrStatus> getEhrStatus() {
        return ehrStatus;
    }

    public void setEhrStatus(VersionedObjectData<EhrStatus> ehrStatus) {
        this.ehrStatus = ehrStatus;
    }

    public List<VersionedObjectData<Composition>> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<VersionedObjectData<Composition>> compositions) {
        this.compositions = compositions;
    }

    public List<VersionedObjectData<Folder>> getFolders() {
        return folders;
    }

    public void setFolders(List<VersionedObjectData<Folder>> folders) {
        this.folders = folders;
    }

    public UUID getDirectory() {
        return directory;
    }

    public void setDirectory(UUID directory) {
        this.directory = directory;
    }

    public DvDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(DvDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
