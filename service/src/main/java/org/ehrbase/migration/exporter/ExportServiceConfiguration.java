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

import org.ehrbase.migration.config.ExportDatasourceConfiguration;
import org.ehrbase.migration.exporter.v0.ExportServiceV0Imp;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExportServiceConfiguration {

    @Bean
    @ConditionalOnBean(name = "exportDslContext")
    ExportService exportService(
            @Qualifier("exportDslContext") DSLContext context,
            ExportDatasourceConfiguration.TenantHandlingExportConnectionProvider connectionProvider) {
        return new ExportServiceV0Imp(context, connectionProvider);
    }
}
