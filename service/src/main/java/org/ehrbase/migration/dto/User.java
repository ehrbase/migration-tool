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

import com.nedap.archie.rm.generic.PartyIdentified;
import java.util.Objects;
import java.util.UUID;

public class User {

    /**
     * Old ehrbase: party id
     */
    private final UUID id;

    private final String username;
    private PartyIdentified partyIdentified;

    public User(UUID id, String username, PartyIdentified partyIdentified) {
        this.id = id;
        this.username = username;
        this.partyIdentified = partyIdentified;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public PartyIdentified getPartyIdentified() {
        return partyIdentified;
    }

    public void setPartyIdentified(PartyIdentified partyIdentified) {
        this.partyIdentified = partyIdentified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.username, that.username)
                && Objects.equals(this.partyIdentified, that.partyIdentified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, partyIdentified);
    }

    @Override
    public String toString() {
        return "User[" + "username=" + username + ", " + "partyIdentified=" + partyIdentified + ']';
    }
}
