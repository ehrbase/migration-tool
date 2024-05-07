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
package org.ehrbase.migration.exporter.v0;

import com.nedap.archie.rm.datatypes.CodePhrase;
import com.nedap.archie.rm.datavalues.DvCodedText;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;
import com.nedap.archie.rm.support.identification.TerminologyId;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;

public class AuditExporter {

    public static com.nedap.archie.rm.generic.AuditDetails to(AuditDetailsRecord auditDetailsRecord) {
        com.nedap.archie.rm.generic.AuditDetails auditDetails = new com.nedap.archie.rm.generic.AuditDetails();

        auditDetails.setSystemId(auditDetailsRecord.getSystemId().toString());

        auditDetails.setDescription(new DvText(auditDetailsRecord.getDescription()));

        ContributionChangeType contributionChangeType = ContributionChangeType.valueOf(
                auditDetailsRecord.getChangeType().getLiteral().toUpperCase());
        DvCodedText changeType = convert(contributionChangeType);
        auditDetails.setChangeType(changeType);

        Timestamp committed = auditDetailsRecord.getTimeCommitted();
        String tzid = auditDetailsRecord.getTimeCommittedTzid();
        DvDateTime timeCommitted = to(committed, tzid);

        auditDetails.setTimeCommitted(timeCommitted);
        auditDetails.setCommitter(PartyExporter.buildDummy(auditDetailsRecord.getCommitter()));
        return auditDetails;
    }

    protected static DvDateTime to(Timestamp committed, String tzid) {
        return new DvDateTime(committed.toInstant().atZone(ZoneId.of(tzid)).toOffsetDateTime());
    }

    static DvCodedText convert(ContributionChangeType contributionChangeType) {
        DvCodedText changeType = new DvCodedText(
                contributionChangeType.name().toLowerCase(Locale.ROOT),
                new CodePhrase(new TerminologyId("openehr"), Integer.toString(contributionChangeType.getCode())));
        return changeType;
    }

    public enum ContributionChangeType {
        CREATION(249),
        AMENDMENT(250),
        MODIFICATION(251),
        SYNTHESIS(252),
        UNKNOWN(253),
        DELETED(523);
        final int code;

        ContributionChangeType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
