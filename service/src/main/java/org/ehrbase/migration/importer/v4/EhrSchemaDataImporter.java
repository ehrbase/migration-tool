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
package org.ehrbase.migration.importer.v4;

import static org.ehrbase.migration.MigrationUtils.getPartyProxyUuid;
import static org.ehrbase.migration.importer.v4.jooq.pg.Tables.TEMPLATE_STORE;
import static org.ehrbase.migration.importer.v4.jooq.pg.tables.Users.USERS;

import java.time.ZoneOffset;
import java.util.UUID;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.User;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.TemplateStoreRecord;
import org.ehrbase.migration.importer.v4.jooq.pg.tables.records.UsersRecord;
import org.jooq.DSLContext;

public class EhrSchemaDataImporter {

    private final DSLContext dslContext;

    public EhrSchemaDataImporter(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    protected void write(EhrSchemaData ehrSchemaData) {

        ImportService.executeBulkInsert(
                dslContext, ehrSchemaData.getUsers().stream().map(this::toUserRecord), USERS);

        // Create anonymousUser if not exist
        if (ehrSchemaData.getUsers().stream().noneMatch(u -> u.getUsername().equals(EhrSchemaData.ANONYMOUS_USER))) {
            UsersRecord usersRecord = dslContext.newRecord(USERS);
            usersRecord.setId(UUID.randomUUID());
            usersRecord.setUsername(EhrSchemaData.ANONYMOUS_USER);
            usersRecord.store();
        }

        ImportService.executeBulkInsert(
                dslContext,
                ehrSchemaData.streamTemplates().map(t -> toTemplateRecord(ehrSchemaData.getTemplateUuid(t), t)),
                TEMPLATE_STORE);
    }

    private TemplateStoreRecord toTemplateRecord(UUID id, EhrSchemaData.TemplateData templateData) {

        TemplateStoreRecord templateStoreRecord = dslContext.newRecord(TEMPLATE_STORE);

        templateStoreRecord.setId(id);
        templateStoreRecord.setTemplateId(templateData.templateId());
        templateStoreRecord.setContent(templateData.content());
        templateStoreRecord.setCreationTime(
                templateData.creation().toLocalDateTime().atOffset(ZoneOffset.UTC));

        return templateStoreRecord;
    }

    private UsersRecord toUserRecord(User user) {

        UsersRecord usersRecord = dslContext.newRecord(USERS);

        usersRecord.setUsername(user.getUsername());
        usersRecord.setId(getPartyProxyUuid(user.getPartyIdentified()));
        return usersRecord;
    }
}
