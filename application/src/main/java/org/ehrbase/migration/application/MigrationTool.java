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
package org.ehrbase.migration.application;

import org.ehrbase.migration.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "org.ehrbase.migration")
public class MigrationTool implements CommandLineRunner {

    public enum MigrationMode {
        DB2DB,
        IMPORT,
        EXPORT,
        NONE
    }

    @Value("${mode}")
    private MigrationMode mode;

    @Value("${terminate:true}")
    private boolean terminate;

    private static Logger LOG = LoggerFactory.getLogger(MigrationTool.class);

    private final ConfigurableApplicationContext context;

    private final ProcessService processService;

    public MigrationTool(ProcessService processService, ConfigurableApplicationContext context) {
        this.processService = processService;
        this.context = context;
    }

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        new SpringApplicationBuilder(MigrationTool.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) {

        switch (mode) {
            case DB2DB -> processService.db2db();
            case IMPORT -> processService.importFromFile();
            case EXPORT -> processService.exportToFile();
            case NONE -> LOG.info("Migration mode NONE: Skipping");
        }
        if (terminate) {
            LOG.debug("Shutting down");
            int exitCode = SpringApplication.exit(context);
            LOG.info("APPLICATION FINISHED");
            System.exit(exitCode);
        }
    }
}
