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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.ehrbase.migration.dto.EhrSchema;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("spring.datasource.export.enable")
public class ExportDatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.export")
    public DataSourceProperties exportDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource exportDataSource() {
        HikariDataSource ds = (HikariDataSource)
                exportDataSourceProperties().initializeDataSourceBuilder().build();
        ds.setPoolName("HikariExportPool");
        ds.setReadOnly(true);
        return ds;
    }

    /**
     * Takes care of setting the tenant for old ehrbase
     */
    public class TenantHandlingExportConnectionProvider implements ConnectionProvider {

        private Set<Connection> handledConnections = ConcurrentHashMap.newKeySet();

        private final ConnectionProvider delegate;
        private volatile String tenantId;

        public TenantHandlingExportConnectionProvider(ConnectionProvider delegate) {
            this.delegate = delegate;
        }

        public void setTenant(EhrSchema ehrSchema) {
            this.tenantId = Integer.toString(ehrSchema.id());
            handledConnections.clear();
        }

        @Override
        public Connection acquire() throws DataAccessException {
            Connection connection = delegate.acquire();
            if (tenantId != null && !handledConnections.contains(connection)) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("select set_config('ehrbase.current_tenant', '%s', false)".formatted(tenantId));
                } catch (SQLException e) {
                    throw new DataAccessException(e.getMessage(), e);
                }
                handledConnections.add(connection);
            }
            return connection;
        }

        @Override
        public void release(Connection connection) throws DataAccessException {
            delegate.release(connection);
        }
    }

    @Bean
    public TenantHandlingExportConnectionProvider exportConnectionProvider(
            @Qualifier("exportDataSource") DataSource dataSource) {
        return new TenantHandlingExportConnectionProvider(new DataSourceConnectionProvider(dataSource));
    }

    @Bean
    public DSLContext exportDslContext(@Qualifier("exportConnectionProvider") ConnectionProvider connectionProvider) {
        DefaultConfiguration defaultConfiguration = new DefaultConfiguration();
        defaultConfiguration.set(connectionProvider);
        defaultConfiguration.set(SQLDialect.POSTGRES);
        return new DefaultDSLContext(defaultConfiguration);
    }
}
