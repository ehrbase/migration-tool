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
package org.ehrbase.migration.application;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

/**
 * A postgres testcontainer tailored for the worker service needs. Using this container in a test will setup postgres with the
 * following settings:
 *
 * <pre>
 *   databases:
 *    - "postgres": db for the mappings
 *    - "ehrbase": db for ehrbase
 *    users:
 *    - "postgres": mapping user
 *    - "ehrbase": ehrbase user
 *    password:
 *    - "postgres": mapping password
 *    - "ehrbase": ehrbase password
 * </pre>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EHRbasePostgresNewContainer extends GenericContainer {

    public static final Integer PORT = 5432;
    public static final String CONTAINER_NAME = "new_postgres";
    private static final String IMAGE = "ehrbase/ehrbase-v2-postgres:16.2";
    private static final String POSTGRES_MAPPING_USER = "postgres";
    private static final String POSTGRES_MAPPING_PW = "postgres";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public EHRbasePostgresNewContainer(Network network) {
        super(IMAGE);

        withNetwork(network);

        // Wait 2 times for the given log message cause after the init script was executed this message
        // will be displayed again and only then the postgres is finally ready.
        waitingFor(new LogMessageWaitStrategy()
                .withRegEx(".*database system is ready to accept connections.*\\s")
                .withTimes(2)
                .withStartupTimeout(Duration.of(60, SECONDS)));

        addExposedPort(PORT);

        withNetworkAliases(CONTAINER_NAME);

        withLogConsumer(new Slf4jLogConsumer(log));
    }

    @Override
    protected void configure() {
        addEnv("POSTGRES_USER", POSTGRES_MAPPING_USER);
        addEnv("POSTGRES_PASSWORD", POSTGRES_MAPPING_PW);
        addEnv("EHRBASE_USER_ADMIN", EHRbaseContainerOld.EHRBASE_ADMIN_USER);
        addEnv("EHRBASE_PASSWORD_ADMIN", EHRbaseContainerOld.EHRBASE_ADMIN_USER);
        addEnv("EHRBASE_USER", EHRbaseContainerOld.EHRBASE_USER);
        addEnv("EHRBASE_PASSWORD", EHRbaseContainerOld.EHRBASE_PASSWORD);
    }
}
