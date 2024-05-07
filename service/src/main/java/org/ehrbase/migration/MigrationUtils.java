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
package org.ehrbase.migration;

import com.nedap.archie.rm.generic.PartyProxy;
import com.nedap.archie.rm.support.identification.ObjectId;
import com.nedap.archie.rm.support.identification.ObjectRef;
import java.util.Optional;
import java.util.UUID;

public final class MigrationUtils {
    private MigrationUtils() {}

    public static UUID getPartyProxyUuid(PartyProxy pp) {
        return Optional.ofNullable(pp)
                .map(PartyProxy::getExternalRef)
                .map(ObjectRef::getId)
                .map(ObjectId::getValue)
                .map(name -> {
                    try {
                        return UUID.fromString(name);
                    } catch (IllegalArgumentException e) {
                        // ObjectId may be no UUID
                        return null;
                    }
                })
                .orElse(null);
    }
}
