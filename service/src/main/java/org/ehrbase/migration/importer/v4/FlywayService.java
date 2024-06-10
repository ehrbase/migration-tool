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
package org.ehrbase.migration.importer.v4;

import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class FlywayService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Flyway flyway;

    @Value("${spring.flyway.ehr-location:classpath:db/migration/ehr}")
    private String ehrLocation;

    @Value("${spring.flyway.ext-location:classpath:db/migration/ext}")
    private String extLocation;

    @Value("${import.ehrbase-db-user}")
    private String dbUser;

    public FlywayService(Flyway flyway) {
        this.flyway = flyway;
    }

    public void migrateExtSchema() {
        migrateSchema("ext", extLocation, "1", "extSchema");
    }

    public void migrateEhrSchema(String schema) {
        migrateSchema(schema, ehrLocation, null, "ehrSchema");
    }

    public void migrateSchema(
            @NonNull String schema,
            @NonNull String location,
            @Nullable String baseline,
            @Nullable String schemaPlaceholder) {
        FluentConfiguration config =
                Flyway.configure().dataSource(getDataSource()).schemas(schema).locations(location);

        Optional.ofNullable(baseline).filter(StringUtils::isNoneBlank).ifPresent(b -> config.baselineOnMigrate(true)
                .baselineVersion(b));

        Optional.ofNullable(schemaPlaceholder)
                .filter(StringUtils::isNoneBlank)
                .ifPresent(b -> config.placeholders(Map.of(schemaPlaceholder, schema)));

        config.load().migrate();
    }

    private DataSource getDataSource() {
        return flyway.getConfiguration().getDataSource();
    }

    public void executeStatements(String... statements) {
        logger.warn("DBUser: {}", dbUser);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
        MapSqlParameterSource paramSource = new MapSqlParameterSource("dbUser", dbUser);

        for (String statement : statements) {
            jdbcTemplate.execute(statement, paramSource, ps -> null);
        }
    }
}
