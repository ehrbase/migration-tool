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

import static org.ehrbase.migration.importer.v4.jooq.pg.tables.Users.USERS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nedap.archie.rm.generic.PartyProxy;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.ehrbase.migration.MigrationUtils;
import org.jooq.DSLContext;

/**
 * Transfer non ehr bound data
 */
public class EhrSchemaData {

    public static final String ANONYMOUS_USER = "anonymousUser";

    public record TemplateData(String templateId, String content, Timestamp creation) {}

    Map<String, TemplateData> templateByTemplateId;

    @JsonIgnore
    Map<TemplateData, UUID> templateUuids = new HashMap<>();

    List<User> users;

    System system;

    // loaded from the import system
    @JsonIgnore
    private UUID defaultUser;

    // loaded from the import system
    @JsonIgnore
    private final Map<UUID, Optional<UUID>> userMap = new HashMap<>();

    public Stream<TemplateData> streamTemplates() {
        return templateByTemplateId.values().stream();
    }

    public TemplateData getTemplateByTemplateId(String templateId) {
        return templateByTemplateId.get(templateId);
    }

    public void setTemplates(List<TemplateData> templates) {
        templateByTemplateId = templates.stream().collect(Collectors.toMap(TemplateData::templateId, t -> t));
    }

    public UUID getTemplateUuid(TemplateData t) {
        return templateUuids.computeIfAbsent(t, tt -> UUID.randomUUID());
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Pair<UUID, PartyProxy> findCommitterUser(PartyProxy partyProxy, DSLContext dslContext) {

        return Optional.ofNullable(partyProxy)
                .map(MigrationUtils::getPartyProxyUuid)
                // does a corresponding user exist?
                .flatMap(u -> userMap.computeIfAbsent(u, id -> dslContext
                        .select(USERS.ID)
                        .from(USERS)
                        .where(USERS.ID.eq(id))
                        .fetchOptional(USERS.ID)))
                // if  a user is found take it.
                .map(u -> Pair.of(u, (PartyProxy) null))
                .orElseGet(() -> Pair.of(findDefaultUser(dslContext), partyProxy));
    }

    private UUID findDefaultUser(DSLContext dslContext) {
        if (defaultUser == null) {
            defaultUser = dslContext
                    .select(USERS.ID)
                    .from(USERS)
                    .where(USERS.USERNAME.eq(ANONYMOUS_USER))
                    .fetchOne(USERS.ID);
        }

        return defaultUser;
    }
}
