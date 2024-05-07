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
package org.ehrbase.migration.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jooq.ConnectionProvider;
import org.jooq.impl.DataSourceConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImportDatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.import")
    public DataSourceProperties importDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource importDataSource(
            @Qualifier("importDataSourceProperties") DataSourceProperties importDataSourceProperties) {
        HikariDataSource ds = (HikariDataSource)
                importDataSourceProperties.initializeDataSourceBuilder().build();
        ds.setPoolName("HikariImportPool");
        return ds;
    }

    @Bean
    public ConnectionProvider importConnectionProvider(@Qualifier("importDataSource") DataSource importDataSource) {
        HikariDataSource ds = (HikariDataSource) importDataSource;
        return new DataSourceConnectionProvider(ds);
    }
}
