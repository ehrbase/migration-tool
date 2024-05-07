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
package org.ehrbase.migration.exporter.v0.jooq.pg;

import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Attestation;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AttestedView;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompoXref;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Composition;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompositionHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Concept;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Contribution;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Entry;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EntryHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EventContext;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EventContextHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.FlywaySchemaHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Identifier;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Participation;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.ParticipationHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.PartyIdentified;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Status;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.StatusHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.System;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.TemplateStore;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Territory;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling indexes of tables in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ATTESTATION_REFERENCE_IDX = Internal.createIndex(
            DSL.name("attestation_reference_idx"),
            Attestation.ATTESTATION,
            new OrderField[] {Attestation.ATTESTATION.REFERENCE, Attestation.ATTESTATION.SYS_TENANT},
            false);
    public static final Index ATTESTED_VIEW_ATTESTATION_IDX = Internal.createIndex(
            DSL.name("attested_view_attestation_idx"),
            AttestedView.ATTESTED_VIEW,
            new OrderField[] {AttestedView.ATTESTED_VIEW.ATTESTATION_ID, AttestedView.ATTESTED_VIEW.SYS_TENANT},
            false);
    public static final Index COMPO_XREF_CHILD_IDX = Internal.createIndex(
            DSL.name("compo_xref_child_idx"),
            CompoXref.COMPO_XREF,
            new OrderField[] {CompoXref.COMPO_XREF.CHILD_UUID, CompoXref.COMPO_XREF.SYS_TENANT},
            false);
    public static final Index COMPOSITION_COMPOSER_IDX = Internal.createIndex(
            DSL.name("composition_composer_idx"),
            Composition.COMPOSITION,
            new OrderField[] {Composition.COMPOSITION.COMPOSER, Composition.COMPOSITION.SYS_TENANT},
            false);
    public static final Index COMPOSITION_EHR_IDX = Internal.createIndex(
            DSL.name("composition_ehr_idx"),
            Composition.COMPOSITION,
            new OrderField[] {Composition.COMPOSITION.EHR_ID, Composition.COMPOSITION.SYS_TENANT},
            false);
    public static final Index COMPOSITION_HISTORY_EHR_IDX = Internal.createIndex(
            DSL.name("composition_history_ehr_idx"),
            CompositionHistory.COMPOSITION_HISTORY,
            new OrderField[] {
                CompositionHistory.COMPOSITION_HISTORY.EHR_ID, CompositionHistory.COMPOSITION_HISTORY.SYS_TENANT
            },
            false);
    public static final Index CONTEXT_COMPOSITION_ID_IDX = Internal.createIndex(
            DSL.name("context_composition_id_idx"),
            EventContext.EVENT_CONTEXT,
            new OrderField[] {EventContext.EVENT_CONTEXT.COMPOSITION_ID, EventContext.EVENT_CONTEXT.SYS_TENANT},
            true);
    public static final Index CONTEXT_FACILITY_IDX = Internal.createIndex(
            DSL.name("context_facility_idx"),
            EventContext.EVENT_CONTEXT,
            new OrderField[] {EventContext.EVENT_CONTEXT.FACILITY, EventContext.EVENT_CONTEXT.SYS_TENANT},
            false);
    public static final Index CONTEXT_PARTICIPATION_INDEX = Internal.createIndex(
            DSL.name("context_participation_index"),
            Participation.PARTICIPATION,
            new OrderField[] {Participation.PARTICIPATION.EVENT_CONTEXT, Participation.PARTICIPATION.SYS_TENANT},
            false);
    public static final Index CONTEXT_SETTING_IDX = Internal.createIndex(
            DSL.name("context_setting_idx"),
            EventContext.EVENT_CONTEXT,
            new OrderField[] {EventContext.EVENT_CONTEXT.SETTING, EventContext.EVENT_CONTEXT.SYS_TENANT},
            false);
    public static final Index CONTRIBUTION_EHR_IDX = Internal.createIndex(
            DSL.name("contribution_ehr_idx"),
            Contribution.CONTRIBUTION,
            new OrderField[] {Contribution.CONTRIBUTION.EHR_ID, Contribution.CONTRIBUTION.SYS_TENANT},
            false);
    public static final Index EHR_COMPO_XREF = Internal.createIndex(
            DSL.name("ehr_compo_xref"),
            CompoXref.COMPO_XREF,
            new OrderField[] {CompoXref.COMPO_XREF.MASTER_UUID, CompoXref.COMPO_XREF.SYS_TENANT},
            false);
    public static final Index EHR_COMPOSITION_HISTORY = Internal.createIndex(
            DSL.name("ehr_composition_history"),
            CompositionHistory.COMPOSITION_HISTORY,
            new OrderField[] {
                CompositionHistory.COMPOSITION_HISTORY.ID, CompositionHistory.COMPOSITION_HISTORY.SYS_TENANT
            },
            false);
    public static final Index EHR_CONCEPT_ID_LANGUAGE_IDX = Internal.createIndex(
            DSL.name("ehr_concept_id_language_idx"),
            Concept.CONCEPT,
            new OrderField[] {Concept.CONCEPT.CONCEPTID, Concept.CONCEPT.LANGUAGE},
            false);
    public static final Index EHR_ENTRY_HISTORY = Internal.createIndex(
            DSL.name("ehr_entry_history"),
            EntryHistory.ENTRY_HISTORY,
            new OrderField[] {EntryHistory.ENTRY_HISTORY.ID, EntryHistory.ENTRY_HISTORY.SYS_TENANT},
            false);
    public static final Index EHR_EVENT_CONTEXT_HISTORY = Internal.createIndex(
            DSL.name("ehr_event_context_history"),
            EventContextHistory.EVENT_CONTEXT_HISTORY,
            new OrderField[] {
                EventContextHistory.EVENT_CONTEXT_HISTORY.ID, EventContextHistory.EVENT_CONTEXT_HISTORY.SYS_TENANT
            },
            false);
    public static final Index EHR_IDENTIFIER_PARTY_IDX = Internal.createIndex(
            DSL.name("ehr_identifier_party_idx"),
            Identifier.IDENTIFIER,
            new OrderField[] {Identifier.IDENTIFIER.PARTY, Identifier.IDENTIFIER.SYS_TENANT},
            false);
    public static final Index EHR_PARTICIPATION_HISTORY = Internal.createIndex(
            DSL.name("ehr_participation_history"),
            ParticipationHistory.PARTICIPATION_HISTORY,
            new OrderField[] {
                ParticipationHistory.PARTICIPATION_HISTORY.ID, ParticipationHistory.PARTICIPATION_HISTORY.SYS_TENANT
            },
            false);
    public static final Index EHR_STATUS_HISTORY = Internal.createIndex(
            DSL.name("ehr_status_history"),
            StatusHistory.STATUS_HISTORY,
            new OrderField[] {StatusHistory.STATUS_HISTORY.ID, StatusHistory.STATUS_HISTORY.SYS_TENANT},
            false);
    public static final Index EHR_SYSTEM_SETTINGS_IDX = Internal.createIndex(
            DSL.name("ehr_system_settings_idx"), System.SYSTEM, new OrderField[] {System.SYSTEM.SETTINGS}, true);
    public static final Index EHR_TERRITORY_TWOLETTER_IDX = Internal.createIndex(
            DSL.name("ehr_territory_twoletter_idx"),
            Territory.TERRITORY,
            new OrderField[] {Territory.TERRITORY.TWOLETTER},
            true);
    public static final Index ENTRY_HISTORY_COMPOSITION_IDX = Internal.createIndex(
            DSL.name("entry_history_composition_idx"),
            EntryHistory.ENTRY_HISTORY,
            new OrderField[] {EntryHistory.ENTRY_HISTORY.COMPOSITION_ID, EntryHistory.ENTRY_HISTORY.SYS_TENANT},
            false);
    public static final Index EVENT_CONTEXT_HISTORY_COMPOSITION_IDX = Internal.createIndex(
            DSL.name("event_context_history_composition_idx"),
            EventContextHistory.EVENT_CONTEXT_HISTORY,
            new OrderField[] {
                EventContextHistory.EVENT_CONTEXT_HISTORY.COMPOSITION_ID,
                EventContextHistory.EVENT_CONTEXT_HISTORY.SYS_TENANT
            },
            false);
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex(
            DSL.name("flyway_schema_history_s_idx"),
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            new OrderField[] {FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS},
            false);
    public static final Index IDENTIFIER_VALUE_IDX = Internal.createIndex(
            DSL.name("identifier_value_idx"),
            Identifier.IDENTIFIER,
            new OrderField[] {Identifier.IDENTIFIER.ID_VALUE, Identifier.IDENTIFIER.SYS_TENANT},
            false);
    public static final Index PARTICIPATION_HISTORY_EVENT_CONTEXT_IDX = Internal.createIndex(
            DSL.name("participation_history_event_context_idx"),
            ParticipationHistory.PARTICIPATION_HISTORY,
            new OrderField[] {
                ParticipationHistory.PARTICIPATION_HISTORY.EVENT_CONTEXT,
                ParticipationHistory.PARTICIPATION_HISTORY.SYS_TENANT
            },
            false);
    public static final Index PARTY_IDENTIFIED_NAMESPACE_VALUE_IDX = Internal.createIndex(
            DSL.name("party_identified_namespace_value_idx"),
            PartyIdentified.PARTY_IDENTIFIED,
            new OrderField[] {
                PartyIdentified.PARTY_IDENTIFIED.PARTY_REF_NAMESPACE,
                PartyIdentified.PARTY_IDENTIFIED.PARTY_REF_VALUE,
                PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT
            },
            false);
    public static final Index PARTY_IDENTIFIED_PARTY_REF_IDX = Internal.createIndex(
            DSL.name("party_identified_party_ref_idx"),
            PartyIdentified.PARTY_IDENTIFIED,
            new OrderField[] {
                PartyIdentified.PARTY_IDENTIFIED.PARTY_REF_NAMESPACE,
                PartyIdentified.PARTY_IDENTIFIED.PARTY_REF_SCHEME,
                PartyIdentified.PARTY_IDENTIFIED.PARTY_REF_VALUE,
                PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT
            },
            false);
    public static final Index PARTY_IDENTIFIED_PARTY_TYPE_IDX = Internal.createIndex(
            DSL.name("party_identified_party_type_idx"),
            PartyIdentified.PARTY_IDENTIFIED,
            new OrderField[] {
                PartyIdentified.PARTY_IDENTIFIED.PARTY_TYPE,
                PartyIdentified.PARTY_IDENTIFIED.NAME,
                PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT
            },
            false);
    public static final Index STATUS_HISTORY_EHR_IDX = Internal.createIndex(
            DSL.name("status_history_ehr_idx"),
            StatusHistory.STATUS_HISTORY,
            new OrderField[] {StatusHistory.STATUS_HISTORY.EHR_ID, StatusHistory.STATUS_HISTORY.SYS_TENANT},
            false);
    public static final Index STATUS_PARTY_IDX = Internal.createIndex(
            DSL.name("status_party_idx"),
            Status.STATUS,
            new OrderField[] {Status.STATUS.PARTY, Status.STATUS.SYS_TENANT},
            false);
    public static final Index TEMPLATE_ENTRY_IDX = Internal.createIndex(
            DSL.name("template_entry_idx"),
            Entry.ENTRY,
            new OrderField[] {Entry.ENTRY.TEMPLATE_ID, Entry.ENTRY.SYS_TENANT},
            false);
    public static final Index TEMPLATE_STORE_TEMPLATE_ID = Internal.createIndex(
            DSL.name("template_store_template_id"),
            TemplateStore.TEMPLATE_STORE,
            new OrderField[] {TemplateStore.TEMPLATE_STORE.TEMPLATE_ID, TemplateStore.TEMPLATE_STORE.SYS_TENANT},
            true);
    public static final Index TERRITORY_CODE_INDEX = Internal.createIndex(
            DSL.name("territory_code_index"), Territory.TERRITORY, new OrderField[] {Territory.TERRITORY.CODE}, true);
}
