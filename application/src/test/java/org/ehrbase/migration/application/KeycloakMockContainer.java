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

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.Base64;
import io.jsonwebtoken.Jwts;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.lifecycle.Startable;

public class KeycloakMockContainer extends GenericContainer<KeycloakMockContainer> {

    public static final String CONTAINER_NAME = "keycloak";

    public static final int PORT = 1080;

    public static final String KEY_ID = UUID.randomUUID().toString();

    public static final KeyPair KEY_PAIR = Jwts.SIG.RS256.keyPair().build();

    private static final String IMAGE = "mockserver/mockserver:5.15.0";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public KeycloakMockContainer(Network network, List<Startable> startables) {
        super(IMAGE);
        withAccessToHost(true);
        withNetwork(network);
        withExposedPorts(PORT);
        withNetworkAliases(CONTAINER_NAME);
        withLogConsumer(new Slf4jLogConsumer(log));
        dependsOn(startables);

        waitingFor(Wait.forLogMessage(".*started on port: %s.*".formatted(PORT), 1));
    }

    @Override
    public void start() {
        super.start();

        MockServerClient mockServerClient = new MockServerClient(getHost(), getMappedPort(PORT));
        try {
            mockServerClient
                    .when(request().withMethod("GET").withPath("/auth/realms/master/protocol/openid-connect/certs"))
                    .respond(certsResponse());
            mockServerClient
                    .when(request().withMethod("GET").withPath("/auth/realms/master/.well-known/openid-configuration"))
                    .respond(infoResponse());
        } catch (Exception e) {
            log.error("Mock response could not be registered", e);
        }
    }

    private HttpResponse certsResponse() throws Exception {
        return response()
                .withStatusCode(200)
                .withBody(new ObjectMapper().writeValueAsString(certResponseBody()), MediaType.APPLICATION_JSON);
    }

    private HttpResponse infoResponse() throws Exception {
        return response()
                .withStatusCode(200)
                .withBody(new ObjectMapper().writeValueAsString(infoResponseBody()), MediaType.APPLICATION_JSON);
    }

    public Map<String, Object> certResponseBody() {
        final RSAPublicKey publicKey = (RSAPublicKey) KEY_PAIR.getPublic();
        final BigInteger modulus = publicKey.getModulus();
        final BigInteger exp = publicKey.getPublicExponent();

        final Map<String, Object> keyInfo = Map.of(
                "kid",
                KEY_ID,
                "kty",
                "RSA",
                "alg",
                "RS256",
                "use",
                "sig",
                "n",
                Base64.encode(modulus).toString(),
                "e",
                Base64.encode(exp).toString());

        return Map.of("keys", List.of(keyInfo));
    }

    public Map<String, Object> infoResponseBody() {

        return Map.of(
                "issuer",
                "http://%s:%s/auth/realms/master"
                        .formatted(KeycloakMockContainer.CONTAINER_NAME, KeycloakMockContainer.PORT),
                "jwks_uri",
                "http://%s:%s/auth/realms/master/protocol/openid-connect/certs"
                        .formatted(KeycloakMockContainer.CONTAINER_NAME, KeycloakMockContainer.PORT));
    }
}
