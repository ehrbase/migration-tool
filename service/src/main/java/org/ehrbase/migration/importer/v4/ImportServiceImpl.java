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

import jakarta.annotation.PostConstruct;
import org.ehrbase.migration.dto.EhrSchema;
import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ImportServiceImpl implements ImportService {

    protected final FlywayService flywayService;

    protected final ConnectionProvider connectionProvider;

    public ImportServiceImpl(
            FlywayService flywayService, @Qualifier("importConnectionProvider") ConnectionProvider connectionProvider) {
        this.flywayService = flywayService;
        this.connectionProvider = connectionProvider;
    }

    @PostConstruct
    protected void init() {
        flywayService.migrateExtSchema();
    }

    @Override
    public EhrSchemaImporter createImporter(EhrSchema ehrSchema, String schemaName) {
        flywayService.migrateEhrSchema(schemaName);

        return new EhrSchemaImporter(new DefaultDSLContext(createDefaultConfiguration(ehrSchema, schemaName)));
    }

    protected DefaultConfiguration createDefaultConfiguration(EhrSchema ehrSchema, String schemaName) {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.set(connectionProvider);
        defaultConfiguration.set(SQLDialect.POSTGRES);
        return defaultConfiguration;
    }
}
