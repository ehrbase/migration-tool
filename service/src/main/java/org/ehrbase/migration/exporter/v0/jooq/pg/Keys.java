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

import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Access;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Attestation;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AttestationRef;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AttestedView;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.AuditDetails;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompoXref;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Composition;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.CompositionHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Concept;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Contribution;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Ehr;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EhrFolder;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EhrFolderHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Entry;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EntryHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EventContext;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.EventContextHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.FlywaySchemaHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Heading;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Identifier;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Language;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Participation;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.ParticipationHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.PartyIdentified;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Plugin;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.SessionLog;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Status;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.StatusHistory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.StoredQuery;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.System;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.TemplateStore;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Tenant;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.TerminologyProvider;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Territory;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.Users;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AccessRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AttestationRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AttestationRefRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AttestedViewRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.AuditDetailsRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.CompoXrefRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.CompositionHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.CompositionRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ConceptRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ContributionRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EhrFolderHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EhrFolderRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EhrRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EntryHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EntryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EventContextHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.EventContextRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.FlywaySchemaHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.HeadingRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.IdentifierRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.LanguageRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ParticipationHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.ParticipationRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.PartyIdentifiedRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.PluginRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.SessionLogRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.StatusHistoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.StatusRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.StoredQueryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.SystemRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TemplateStoreRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TenantRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TerminologyProviderRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.TerritoryRecord;
import org.ehrbase.migration.exporter.v0.jooq.pg.tables.records.UsersRecord;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/**
 * A class modelling foreign key relationships and constraints of tables in ehr.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AccessRecord> ACCESS_PKEY = Internal.createUniqueKey(
            Access.ACCESS,
            DSL.name("access_pkey"),
            new TableField[] {Access.ACCESS.ID, Access.ACCESS.SYS_TENANT},
            true);
    public static final UniqueKey<AttestationRecord> ATTESTATION_PKEY = Internal.createUniqueKey(
            Attestation.ATTESTATION,
            DSL.name("attestation_pkey"),
            new TableField[] {Attestation.ATTESTATION.ID, Attestation.ATTESTATION.SYS_TENANT},
            true);
    public static final UniqueKey<AttestationRefRecord> ATTESTATION_REF_PKEY = Internal.createUniqueKey(
            AttestationRef.ATTESTATION_REF,
            DSL.name("attestation_ref_pkey"),
            new TableField[] {AttestationRef.ATTESTATION_REF.REF, AttestationRef.ATTESTATION_REF.SYS_TENANT},
            true);
    public static final UniqueKey<AttestedViewRecord> ATTESTED_VIEW_PKEY = Internal.createUniqueKey(
            AttestedView.ATTESTED_VIEW,
            DSL.name("attested_view_pkey"),
            new TableField[] {AttestedView.ATTESTED_VIEW.ID, AttestedView.ATTESTED_VIEW.SYS_TENANT},
            true);
    public static final UniqueKey<AuditDetailsRecord> AUDIT_DETAILS_PKEY = Internal.createUniqueKey(
            AuditDetails.AUDIT_DETAILS,
            DSL.name("audit_details_pkey"),
            new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
            true);
    public static final UniqueKey<CompositionRecord> COMPOSITION_PKEY = Internal.createUniqueKey(
            Composition.COMPOSITION,
            DSL.name("composition_pkey"),
            new TableField[] {Composition.COMPOSITION.ID, Composition.COMPOSITION.SYS_TENANT},
            true);
    public static final UniqueKey<ConceptRecord> CONCEPT_PKEY = Internal.createUniqueKey(
            Concept.CONCEPT, DSL.name("concept_pkey"), new TableField[] {Concept.CONCEPT.ID}, true);
    public static final UniqueKey<ContributionRecord> CONTRIBUTION_PKEY = Internal.createUniqueKey(
            Contribution.CONTRIBUTION,
            DSL.name("contribution_pkey"),
            new TableField[] {Contribution.CONTRIBUTION.ID, Contribution.CONTRIBUTION.SYS_TENANT},
            true);
    public static final UniqueKey<EhrRecord> EHR_PKEY = Internal.createUniqueKey(
            Ehr.EHR_, DSL.name("ehr_pkey"), new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT}, true);
    public static final UniqueKey<EhrFolderRecord> EHR_FOLDER_PKEY = Internal.createUniqueKey(
            EhrFolder.EHR_FOLDER,
            DSL.name("ehr_folder_pkey"),
            new TableField[] {EhrFolder.EHR_FOLDER.EHR_ID, EhrFolder.EHR_FOLDER.ID, EhrFolder.EHR_FOLDER.SYS_TENANT},
            true);
    public static final UniqueKey<EhrFolderHistoryRecord> EHR_FOLDER_HISTORY_PKEY = Internal.createUniqueKey(
            EhrFolderHistory.EHR_FOLDER_HISTORY,
            DSL.name("ehr_folder_history_pkey"),
            new TableField[] {
                EhrFolderHistory.EHR_FOLDER_HISTORY.EHR_ID,
                EhrFolderHistory.EHR_FOLDER_HISTORY.ID,
                EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_VERSION,
                EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_TENANT
            },
            true);
    public static final UniqueKey<EntryRecord> ENTRY_COMPOSITION_ID_KEY = Internal.createUniqueKey(
            Entry.ENTRY,
            DSL.name("entry_composition_id_key"),
            new TableField[] {Entry.ENTRY.COMPOSITION_ID, Entry.ENTRY.SYS_TENANT},
            true);
    public static final UniqueKey<EntryRecord> ENTRY_PKEY = Internal.createUniqueKey(
            Entry.ENTRY, DSL.name("entry_pkey"), new TableField[] {Entry.ENTRY.ID, Entry.ENTRY.SYS_TENANT}, true);
    public static final UniqueKey<EventContextRecord> EVENT_CONTEXT_PKEY = Internal.createUniqueKey(
            EventContext.EVENT_CONTEXT,
            DSL.name("event_context_pkey"),
            new TableField[] {EventContext.EVENT_CONTEXT.ID, EventContext.EVENT_CONTEXT.SYS_TENANT},
            true);
    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(
            FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY,
            DSL.name("flyway_schema_history_pk"),
            new TableField[] {FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK},
            true);
    public static final UniqueKey<HeadingRecord> HEADING_PKEY = Internal.createUniqueKey(
            Heading.HEADING,
            DSL.name("heading_pkey"),
            new TableField[] {Heading.HEADING.CODE, Heading.HEADING.SYS_TENANT},
            true);
    public static final UniqueKey<LanguageRecord> LANGUAGE_PKEY = Internal.createUniqueKey(
            Language.LANGUAGE, DSL.name("language_pkey"), new TableField[] {Language.LANGUAGE.CODE}, true);
    public static final UniqueKey<ParticipationRecord> PARTICIPATION_PKEY = Internal.createUniqueKey(
            Participation.PARTICIPATION,
            DSL.name("participation_pkey"),
            new TableField[] {Participation.PARTICIPATION.ID, Participation.PARTICIPATION.SYS_TENANT},
            true);
    public static final UniqueKey<PartyIdentifiedRecord> PARTY_IDENTIFIED_PKEY = Internal.createUniqueKey(
            PartyIdentified.PARTY_IDENTIFIED,
            DSL.name("party_identified_pkey"),
            new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
            true);
    public static final UniqueKey<PluginRecord> PLUGIN_PKEY =
            Internal.createUniqueKey(Plugin.PLUGIN, DSL.name("plugin_pkey"), new TableField[] {Plugin.PLUGIN.ID}, true);
    public static final UniqueKey<SessionLogRecord> SESSION_LOG_PKEY = Internal.createUniqueKey(
            SessionLog.SESSION_LOG,
            DSL.name("session_log_pkey"),
            new TableField[] {SessionLog.SESSION_LOG.ID, SessionLog.SESSION_LOG.SYS_TENANT},
            true);
    public static final UniqueKey<StatusRecord> STATUS_EHR_ID_KEY = Internal.createUniqueKey(
            Status.STATUS,
            DSL.name("status_ehr_id_key"),
            new TableField[] {Status.STATUS.EHR_ID, Status.STATUS.SYS_TENANT},
            true);
    public static final UniqueKey<StatusRecord> STATUS_PKEY = Internal.createUniqueKey(
            Status.STATUS,
            DSL.name("status_pkey"),
            new TableField[] {Status.STATUS.ID, Status.STATUS.SYS_TENANT},
            true);
    public static final UniqueKey<StoredQueryRecord> STORED_QUERY_PKEY = Internal.createUniqueKey(
            StoredQuery.STORED_QUERY,
            DSL.name("stored_query_pkey"),
            new TableField[] {
                StoredQuery.STORED_QUERY.REVERSE_DOMAIN_NAME,
                StoredQuery.STORED_QUERY.SEMANTIC_ID,
                StoredQuery.STORED_QUERY.SEMVER,
                StoredQuery.STORED_QUERY.SYS_TENANT
            },
            true);
    public static final UniqueKey<SystemRecord> SYSTEM_PKEY =
            Internal.createUniqueKey(System.SYSTEM, DSL.name("system_pkey"), new TableField[] {System.SYSTEM.ID}, true);
    public static final UniqueKey<TemplateStoreRecord> TEMPLATE_STORE_PKEY = Internal.createUniqueKey(
            TemplateStore.TEMPLATE_STORE,
            DSL.name("template_store_pkey"),
            new TableField[] {TemplateStore.TEMPLATE_STORE.ID, TemplateStore.TEMPLATE_STORE.SYS_TENANT},
            true);
    public static final UniqueKey<TenantRecord> TENANT_PKEY =
            Internal.createUniqueKey(Tenant.TENANT, DSL.name("tenant_pkey"), new TableField[] {Tenant.TENANT.ID}, true);
    public static final UniqueKey<TenantRecord> TENANT_TENANT_ID_KEY = Internal.createUniqueKey(
            Tenant.TENANT, DSL.name("tenant_tenant_id_key"), new TableField[] {Tenant.TENANT.TENANT_ID}, true);
    public static final UniqueKey<TenantRecord> TENANT_TENANT_NAME_KEY = Internal.createUniqueKey(
            Tenant.TENANT, DSL.name("tenant_tenant_name_key"), new TableField[] {Tenant.TENANT.TENANT_NAME}, true);
    public static final UniqueKey<TerminologyProviderRecord> TERMINOLOGY_PROVIDER_PKEY = Internal.createUniqueKey(
            TerminologyProvider.TERMINOLOGY_PROVIDER,
            DSL.name("terminology_provider_pkey"),
            new TableField[] {
                TerminologyProvider.TERMINOLOGY_PROVIDER.CODE, TerminologyProvider.TERMINOLOGY_PROVIDER.SYS_TENANT
            },
            true);
    public static final UniqueKey<TerritoryRecord> TERRITORY_PKEY = Internal.createUniqueKey(
            Territory.TERRITORY, DSL.name("territory_pkey"), new TableField[] {Territory.TERRITORY.CODE}, true);
    public static final UniqueKey<UsersRecord> USERS_PKEY = Internal.createUniqueKey(
            Users.USERS, DSL.name("users_pkey"), new TableField[] {Users.USERS.USERNAME, Users.USERS.SYS_TENANT}, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AccessRecord, TenantRecord> ACCESS__ACCESS_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Access.ACCESS,
                    DSL.name("access_sys_tenant_fkey"),
                    new TableField[] {Access.ACCESS.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<AttestationRecord, AuditDetailsRecord> ATTESTATION__ATTESTATION_HAS_AUDIT_FKEY =
            Internal.createForeignKey(
                    Attestation.ATTESTATION,
                    DSL.name("attestation_has_audit_fkey"),
                    new TableField[] {Attestation.ATTESTATION.HAS_AUDIT, Attestation.ATTESTATION.SYS_TENANT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<AttestationRecord, AttestationRefRecord> ATTESTATION__ATTESTATION_REFERENCE_FKEY =
            Internal.createForeignKey(
                    Attestation.ATTESTATION,
                    DSL.name("attestation_reference_fkey"),
                    new TableField[] {Attestation.ATTESTATION.REFERENCE, Attestation.ATTESTATION.SYS_TENANT},
                    Keys.ATTESTATION_REF_PKEY,
                    new TableField[] {AttestationRef.ATTESTATION_REF.REF, AttestationRef.ATTESTATION_REF.SYS_TENANT},
                    true);
    public static final ForeignKey<AttestationRecord, TenantRecord> ATTESTATION__ATTESTATION_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Attestation.ATTESTATION,
                    DSL.name("attestation_sys_tenant_fkey"),
                    new TableField[] {Attestation.ATTESTATION.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<AttestationRefRecord, TenantRecord>
            ATTESTATION_REF__ATTESTATION_REF_SYS_TENANT_FKEY = Internal.createForeignKey(
                    AttestationRef.ATTESTATION_REF,
                    DSL.name("attestation_ref_sys_tenant_fkey"),
                    new TableField[] {AttestationRef.ATTESTATION_REF.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<AttestedViewRecord, AttestationRecord>
            ATTESTED_VIEW__ATTESTED_VIEW_ATTESTATION_ID_FKEY = Internal.createForeignKey(
                    AttestedView.ATTESTED_VIEW,
                    DSL.name("attested_view_attestation_id_fkey"),
                    new TableField[] {AttestedView.ATTESTED_VIEW.ATTESTATION_ID, AttestedView.ATTESTED_VIEW.SYS_TENANT},
                    Keys.ATTESTATION_PKEY,
                    new TableField[] {Attestation.ATTESTATION.ID, Attestation.ATTESTATION.SYS_TENANT},
                    true);
    public static final ForeignKey<AttestedViewRecord, TenantRecord> ATTESTED_VIEW__ATTESTED_VIEW_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    AttestedView.ATTESTED_VIEW,
                    DSL.name("attested_view_sys_tenant_fkey"),
                    new TableField[] {AttestedView.ATTESTED_VIEW.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<AuditDetailsRecord, PartyIdentifiedRecord>
            AUDIT_DETAILS__AUDIT_DETAILS_COMMITTER_FKEY = Internal.createForeignKey(
                    AuditDetails.AUDIT_DETAILS,
                    DSL.name("audit_details_committer_fkey"),
                    new TableField[] {AuditDetails.AUDIT_DETAILS.COMMITTER, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<AuditDetailsRecord, TenantRecord> AUDIT_DETAILS__AUDIT_DETAILS_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    AuditDetails.AUDIT_DETAILS,
                    DSL.name("audit_details_sys_tenant_fkey"),
                    new TableField[] {AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<AuditDetailsRecord, SystemRecord> AUDIT_DETAILS__AUDIT_DETAILS_SYSTEM_ID_FKEY =
            Internal.createForeignKey(
                    AuditDetails.AUDIT_DETAILS,
                    DSL.name("audit_details_system_id_fkey"),
                    new TableField[] {AuditDetails.AUDIT_DETAILS.SYSTEM_ID},
                    Keys.SYSTEM_PKEY,
                    new TableField[] {System.SYSTEM.ID},
                    true);
    public static final ForeignKey<CompoXrefRecord, CompositionRecord> COMPO_XREF__COMPO_XREF_CHILD_UUID_FKEY =
            Internal.createForeignKey(
                    CompoXref.COMPO_XREF,
                    DSL.name("compo_xref_child_uuid_fkey"),
                    new TableField[] {CompoXref.COMPO_XREF.CHILD_UUID, CompoXref.COMPO_XREF.SYS_TENANT},
                    Keys.COMPOSITION_PKEY,
                    new TableField[] {Composition.COMPOSITION.ID, Composition.COMPOSITION.SYS_TENANT},
                    true);
    public static final ForeignKey<CompoXrefRecord, CompositionRecord> COMPO_XREF__COMPO_XREF_MASTER_UUID_FKEY =
            Internal.createForeignKey(
                    CompoXref.COMPO_XREF,
                    DSL.name("compo_xref_master_uuid_fkey"),
                    new TableField[] {CompoXref.COMPO_XREF.MASTER_UUID, CompoXref.COMPO_XREF.SYS_TENANT},
                    Keys.COMPOSITION_PKEY,
                    new TableField[] {Composition.COMPOSITION.ID, Composition.COMPOSITION.SYS_TENANT},
                    true);
    public static final ForeignKey<CompoXrefRecord, TenantRecord> COMPO_XREF__COMPO_XREF_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    CompoXref.COMPO_XREF,
                    DSL.name("compo_xref_sys_tenant_fkey"),
                    new TableField[] {CompoXref.COMPO_XREF.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<CompositionRecord, AttestationRefRecord>
            COMPOSITION__COMPOSITION_ATTESTATION_REF_FKEY = Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_attestation_ref_fkey"),
                    new TableField[] {Composition.COMPOSITION.ATTESTATION_REF, Composition.COMPOSITION.SYS_TENANT},
                    Keys.ATTESTATION_REF_PKEY,
                    new TableField[] {AttestationRef.ATTESTATION_REF.REF, AttestationRef.ATTESTATION_REF.SYS_TENANT},
                    true);
    public static final ForeignKey<CompositionRecord, PartyIdentifiedRecord> COMPOSITION__COMPOSITION_COMPOSER_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_composer_fkey"),
                    new TableField[] {Composition.COMPOSITION.COMPOSER, Composition.COMPOSITION.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<CompositionRecord, EhrRecord> COMPOSITION__COMPOSITION_EHR_ID_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_ehr_id_fkey"),
                    new TableField[] {Composition.COMPOSITION.EHR_ID, Composition.COMPOSITION.SYS_TENANT},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT},
                    true);
    public static final ForeignKey<CompositionRecord, AuditDetailsRecord> COMPOSITION__COMPOSITION_HAS_AUDIT_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_has_audit_fkey"),
                    new TableField[] {Composition.COMPOSITION.HAS_AUDIT, Composition.COMPOSITION.SYS_TENANT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<CompositionRecord, ContributionRecord>
            COMPOSITION__COMPOSITION_IN_CONTRIBUTION_FKEY = Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_in_contribution_fkey"),
                    new TableField[] {Composition.COMPOSITION.IN_CONTRIBUTION, Composition.COMPOSITION.SYS_TENANT},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID, Contribution.CONTRIBUTION.SYS_TENANT},
                    true);
    public static final ForeignKey<CompositionRecord, LanguageRecord> COMPOSITION__COMPOSITION_LANGUAGE_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_language_fkey"),
                    new TableField[] {Composition.COMPOSITION.LANGUAGE},
                    Keys.LANGUAGE_PKEY,
                    new TableField[] {Language.LANGUAGE.CODE},
                    true);
    public static final ForeignKey<CompositionRecord, TenantRecord> COMPOSITION__COMPOSITION_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_sys_tenant_fkey"),
                    new TableField[] {Composition.COMPOSITION.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<CompositionRecord, TerritoryRecord> COMPOSITION__COMPOSITION_TERRITORY_FKEY =
            Internal.createForeignKey(
                    Composition.COMPOSITION,
                    DSL.name("composition_territory_fkey"),
                    new TableField[] {Composition.COMPOSITION.TERRITORY},
                    Keys.TERRITORY_PKEY,
                    new TableField[] {Territory.TERRITORY.CODE},
                    true);
    public static final ForeignKey<CompositionHistoryRecord, TenantRecord>
            COMPOSITION_HISTORY__COMPOSITION_HISTORY_SYS_TENANT_FKEY = Internal.createForeignKey(
                    CompositionHistory.COMPOSITION_HISTORY,
                    DSL.name("composition_history_sys_tenant_fkey"),
                    new TableField[] {CompositionHistory.COMPOSITION_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<ConceptRecord, LanguageRecord> CONCEPT__CONCEPT_LANGUAGE_FKEY =
            Internal.createForeignKey(
                    Concept.CONCEPT,
                    DSL.name("concept_language_fkey"),
                    new TableField[] {Concept.CONCEPT.LANGUAGE},
                    Keys.LANGUAGE_PKEY,
                    new TableField[] {Language.LANGUAGE.CODE},
                    true);
    public static final ForeignKey<ContributionRecord, EhrRecord> CONTRIBUTION__CONTRIBUTION_EHR_ID_FKEY =
            Internal.createForeignKey(
                    Contribution.CONTRIBUTION,
                    DSL.name("contribution_ehr_id_fkey"),
                    new TableField[] {Contribution.CONTRIBUTION.EHR_ID, Contribution.CONTRIBUTION.SYS_TENANT},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT},
                    true);
    public static final ForeignKey<ContributionRecord, AuditDetailsRecord> CONTRIBUTION__CONTRIBUTION_HAS_AUDIT_FKEY =
            Internal.createForeignKey(
                    Contribution.CONTRIBUTION,
                    DSL.name("contribution_has_audit_fkey"),
                    new TableField[] {Contribution.CONTRIBUTION.HAS_AUDIT, Contribution.CONTRIBUTION.SYS_TENANT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<ContributionRecord, TenantRecord> CONTRIBUTION__CONTRIBUTION_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Contribution.CONTRIBUTION,
                    DSL.name("contribution_sys_tenant_fkey"),
                    new TableField[] {Contribution.CONTRIBUTION.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<EhrRecord, AccessRecord> EHR__EHR_ACCESS_FKEY = Internal.createForeignKey(
            Ehr.EHR_,
            DSL.name("ehr_access_fkey"),
            new TableField[] {Ehr.EHR_.ACCESS, Ehr.EHR_.SYS_TENANT},
            Keys.ACCESS_PKEY,
            new TableField[] {Access.ACCESS.ID, Access.ACCESS.SYS_TENANT},
            true);
    public static final ForeignKey<EhrRecord, TenantRecord> EHR__EHR_SYS_TENANT_FKEY = Internal.createForeignKey(
            Ehr.EHR_,
            DSL.name("ehr_sys_tenant_fkey"),
            new TableField[] {Ehr.EHR_.SYS_TENANT},
            Keys.TENANT_PKEY,
            new TableField[] {Tenant.TENANT.ID},
            true);
    public static final ForeignKey<EhrRecord, SystemRecord> EHR__EHR_SYSTEM_ID_FKEY = Internal.createForeignKey(
            Ehr.EHR_,
            DSL.name("ehr_system_id_fkey"),
            new TableField[] {Ehr.EHR_.SYSTEM_ID},
            Keys.SYSTEM_PKEY,
            new TableField[] {System.SYSTEM.ID},
            true);
    public static final ForeignKey<EhrFolderRecord, AuditDetailsRecord> EHR_FOLDER__EHR_FOLDER_AUDIT_ID_FKEY =
            Internal.createForeignKey(
                    EhrFolder.EHR_FOLDER,
                    DSL.name("ehr_folder_audit_id_fkey"),
                    new TableField[] {EhrFolder.EHR_FOLDER.AUDIT_ID, EhrFolder.EHR_FOLDER.SYS_TENANT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderRecord, ContributionRecord> EHR_FOLDER__EHR_FOLDER_CONTRIBUTION_ID_FKEY =
            Internal.createForeignKey(
                    EhrFolder.EHR_FOLDER,
                    DSL.name("ehr_folder_contribution_id_fkey"),
                    new TableField[] {EhrFolder.EHR_FOLDER.CONTRIBUTION_ID, EhrFolder.EHR_FOLDER.SYS_TENANT},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID, Contribution.CONTRIBUTION.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderRecord, EhrRecord> EHR_FOLDER__EHR_FOLDER_EHR_ID_FKEY =
            Internal.createForeignKey(
                    EhrFolder.EHR_FOLDER,
                    DSL.name("ehr_folder_ehr_id_fkey"),
                    new TableField[] {EhrFolder.EHR_FOLDER.EHR_ID, EhrFolder.EHR_FOLDER.SYS_TENANT},
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderRecord, TenantRecord> EHR_FOLDER__EHR_FOLDER_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    EhrFolder.EHR_FOLDER,
                    DSL.name("ehr_folder_sys_tenant_fkey"),
                    new TableField[] {EhrFolder.EHR_FOLDER.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<EhrFolderHistoryRecord, AuditDetailsRecord>
            EHR_FOLDER_HISTORY__EHR_FOLDER_HISTORY_AUDIT_ID_FKEY = Internal.createForeignKey(
                    EhrFolderHistory.EHR_FOLDER_HISTORY,
                    DSL.name("ehr_folder_history_audit_id_fkey"),
                    new TableField[] {
                        EhrFolderHistory.EHR_FOLDER_HISTORY.AUDIT_ID, EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_TENANT
                    },
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderHistoryRecord, ContributionRecord>
            EHR_FOLDER_HISTORY__EHR_FOLDER_HISTORY_CONTRIBUTION_ID_FKEY = Internal.createForeignKey(
                    EhrFolderHistory.EHR_FOLDER_HISTORY,
                    DSL.name("ehr_folder_history_contribution_id_fkey"),
                    new TableField[] {
                        EhrFolderHistory.EHR_FOLDER_HISTORY.CONTRIBUTION_ID,
                        EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_TENANT
                    },
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID, Contribution.CONTRIBUTION.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderHistoryRecord, EhrRecord>
            EHR_FOLDER_HISTORY__EHR_FOLDER_HISTORY_EHR_ID_FKEY = Internal.createForeignKey(
                    EhrFolderHistory.EHR_FOLDER_HISTORY,
                    DSL.name("ehr_folder_history_ehr_id_fkey"),
                    new TableField[] {
                        EhrFolderHistory.EHR_FOLDER_HISTORY.EHR_ID, EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_TENANT
                    },
                    Keys.EHR_PKEY,
                    new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT},
                    true);
    public static final ForeignKey<EhrFolderHistoryRecord, TenantRecord>
            EHR_FOLDER_HISTORY__EHR_FOLDER_HISTORY_SYS_TENANT_FKEY = Internal.createForeignKey(
                    EhrFolderHistory.EHR_FOLDER_HISTORY,
                    DSL.name("ehr_folder_history_sys_tenant_fkey"),
                    new TableField[] {EhrFolderHistory.EHR_FOLDER_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<EntryRecord, CompositionRecord> ENTRY__ENTRY_COMPOSITION_ID_FKEY =
            Internal.createForeignKey(
                    Entry.ENTRY,
                    DSL.name("entry_composition_id_fkey"),
                    new TableField[] {Entry.ENTRY.COMPOSITION_ID, Entry.ENTRY.SYS_TENANT},
                    Keys.COMPOSITION_PKEY,
                    new TableField[] {Composition.COMPOSITION.ID, Composition.COMPOSITION.SYS_TENANT},
                    true);
    public static final ForeignKey<EntryRecord, TenantRecord> ENTRY__ENTRY_SYS_TENANT_FKEY = Internal.createForeignKey(
            Entry.ENTRY,
            DSL.name("entry_sys_tenant_fkey"),
            new TableField[] {Entry.ENTRY.SYS_TENANT},
            Keys.TENANT_PKEY,
            new TableField[] {Tenant.TENANT.ID},
            true);
    public static final ForeignKey<EntryHistoryRecord, TenantRecord> ENTRY_HISTORY__ENTRY_HISTORY_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    EntryHistory.ENTRY_HISTORY,
                    DSL.name("entry_history_sys_tenant_fkey"),
                    new TableField[] {EntryHistory.ENTRY_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<EventContextRecord, CompositionRecord>
            EVENT_CONTEXT__EVENT_CONTEXT_COMPOSITION_ID_FKEY = Internal.createForeignKey(
                    EventContext.EVENT_CONTEXT,
                    DSL.name("event_context_composition_id_fkey"),
                    new TableField[] {EventContext.EVENT_CONTEXT.COMPOSITION_ID, EventContext.EVENT_CONTEXT.SYS_TENANT},
                    Keys.COMPOSITION_PKEY,
                    new TableField[] {Composition.COMPOSITION.ID, Composition.COMPOSITION.SYS_TENANT},
                    true);
    public static final ForeignKey<EventContextRecord, PartyIdentifiedRecord>
            EVENT_CONTEXT__EVENT_CONTEXT_FACILITY_FKEY = Internal.createForeignKey(
                    EventContext.EVENT_CONTEXT,
                    DSL.name("event_context_facility_fkey"),
                    new TableField[] {EventContext.EVENT_CONTEXT.FACILITY, EventContext.EVENT_CONTEXT.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<EventContextRecord, TenantRecord> EVENT_CONTEXT__EVENT_CONTEXT_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    EventContext.EVENT_CONTEXT,
                    DSL.name("event_context_sys_tenant_fkey"),
                    new TableField[] {EventContext.EVENT_CONTEXT.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<EventContextHistoryRecord, TenantRecord>
            EVENT_CONTEXT_HISTORY__EVENT_CONTEXT_HISTORY_SYS_TENANT_FKEY = Internal.createForeignKey(
                    EventContextHistory.EVENT_CONTEXT_HISTORY,
                    DSL.name("event_context_history_sys_tenant_fkey"),
                    new TableField[] {EventContextHistory.EVENT_CONTEXT_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<HeadingRecord, TenantRecord> HEADING__HEADING_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Heading.HEADING,
                    DSL.name("heading_sys_tenant_fkey"),
                    new TableField[] {Heading.HEADING.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<IdentifierRecord, PartyIdentifiedRecord> IDENTIFIER__IDENTIFIER_PARTY_FKEY =
            Internal.createForeignKey(
                    Identifier.IDENTIFIER,
                    DSL.name("identifier_party_fkey"),
                    new TableField[] {Identifier.IDENTIFIER.PARTY, Identifier.IDENTIFIER.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<IdentifierRecord, TenantRecord> IDENTIFIER__IDENTIFIER_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Identifier.IDENTIFIER,
                    DSL.name("identifier_sys_tenant_fkey"),
                    new TableField[] {Identifier.IDENTIFIER.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<ParticipationRecord, EventContextRecord>
            PARTICIPATION__PARTICIPATION_EVENT_CONTEXT_FKEY = Internal.createForeignKey(
                    Participation.PARTICIPATION,
                    DSL.name("participation_event_context_fkey"),
                    new TableField[] {Participation.PARTICIPATION.EVENT_CONTEXT, Participation.PARTICIPATION.SYS_TENANT
                    },
                    Keys.EVENT_CONTEXT_PKEY,
                    new TableField[] {EventContext.EVENT_CONTEXT.ID, EventContext.EVENT_CONTEXT.SYS_TENANT},
                    true);
    public static final ForeignKey<ParticipationRecord, PartyIdentifiedRecord>
            PARTICIPATION__PARTICIPATION_PERFORMER_FKEY = Internal.createForeignKey(
                    Participation.PARTICIPATION,
                    DSL.name("participation_performer_fkey"),
                    new TableField[] {Participation.PARTICIPATION.PERFORMER, Participation.PARTICIPATION.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<ParticipationRecord, TenantRecord> PARTICIPATION__PARTICIPATION_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Participation.PARTICIPATION,
                    DSL.name("participation_sys_tenant_fkey"),
                    new TableField[] {Participation.PARTICIPATION.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<ParticipationHistoryRecord, TenantRecord>
            PARTICIPATION_HISTORY__PARTICIPATION_HISTORY_SYS_TENANT_FKEY = Internal.createForeignKey(
                    ParticipationHistory.PARTICIPATION_HISTORY,
                    DSL.name("participation_history_sys_tenant_fkey"),
                    new TableField[] {ParticipationHistory.PARTICIPATION_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<PartyIdentifiedRecord, TenantRecord>
            PARTY_IDENTIFIED__PARTY_IDENTIFIED_SYS_TENANT_FKEY = Internal.createForeignKey(
                    PartyIdentified.PARTY_IDENTIFIED,
                    DSL.name("party_identified_sys_tenant_fkey"),
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<SessionLogRecord, TenantRecord> SESSION_LOG__SESSION_LOG_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    SessionLog.SESSION_LOG,
                    DSL.name("session_log_sys_tenant_fkey"),
                    new TableField[] {SessionLog.SESSION_LOG.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<StatusRecord, AttestationRefRecord> STATUS__STATUS_ATTESTATION_REF_FKEY =
            Internal.createForeignKey(
                    Status.STATUS,
                    DSL.name("status_attestation_ref_fkey"),
                    new TableField[] {Status.STATUS.ATTESTATION_REF, Status.STATUS.SYS_TENANT},
                    Keys.ATTESTATION_REF_PKEY,
                    new TableField[] {AttestationRef.ATTESTATION_REF.REF, AttestationRef.ATTESTATION_REF.SYS_TENANT},
                    true);
    public static final ForeignKey<StatusRecord, EhrRecord> STATUS__STATUS_EHR_ID_FKEY = Internal.createForeignKey(
            Status.STATUS,
            DSL.name("status_ehr_id_fkey"),
            new TableField[] {Status.STATUS.EHR_ID, Status.STATUS.SYS_TENANT},
            Keys.EHR_PKEY,
            new TableField[] {Ehr.EHR_.ID, Ehr.EHR_.SYS_TENANT},
            true);
    public static final ForeignKey<StatusRecord, AuditDetailsRecord> STATUS__STATUS_HAS_AUDIT_FKEY =
            Internal.createForeignKey(
                    Status.STATUS,
                    DSL.name("status_has_audit_fkey"),
                    new TableField[] {Status.STATUS.HAS_AUDIT, Status.STATUS.SYS_TENANT},
                    Keys.AUDIT_DETAILS_PKEY,
                    new TableField[] {AuditDetails.AUDIT_DETAILS.ID, AuditDetails.AUDIT_DETAILS.SYS_TENANT},
                    true);
    public static final ForeignKey<StatusRecord, ContributionRecord> STATUS__STATUS_IN_CONTRIBUTION_FKEY =
            Internal.createForeignKey(
                    Status.STATUS,
                    DSL.name("status_in_contribution_fkey"),
                    new TableField[] {Status.STATUS.IN_CONTRIBUTION, Status.STATUS.SYS_TENANT},
                    Keys.CONTRIBUTION_PKEY,
                    new TableField[] {Contribution.CONTRIBUTION.ID, Contribution.CONTRIBUTION.SYS_TENANT},
                    true);
    public static final ForeignKey<StatusRecord, PartyIdentifiedRecord> STATUS__STATUS_PARTY_FKEY =
            Internal.createForeignKey(
                    Status.STATUS,
                    DSL.name("status_party_fkey"),
                    new TableField[] {Status.STATUS.PARTY, Status.STATUS.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<StatusRecord, TenantRecord> STATUS__STATUS_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    Status.STATUS,
                    DSL.name("status_sys_tenant_fkey"),
                    new TableField[] {Status.STATUS.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<StatusHistoryRecord, TenantRecord> STATUS_HISTORY__STATUS_HISTORY_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    StatusHistory.STATUS_HISTORY,
                    DSL.name("status_history_sys_tenant_fkey"),
                    new TableField[] {StatusHistory.STATUS_HISTORY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<StoredQueryRecord, TenantRecord> STORED_QUERY__STORED_QUERY_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    StoredQuery.STORED_QUERY,
                    DSL.name("stored_query_sys_tenant_fkey"),
                    new TableField[] {StoredQuery.STORED_QUERY.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<TemplateStoreRecord, TenantRecord> TEMPLATE_STORE__TEMPLATE_STORE_SYS_TENANT_FKEY =
            Internal.createForeignKey(
                    TemplateStore.TEMPLATE_STORE,
                    DSL.name("template_store_sys_tenant_fkey"),
                    new TableField[] {TemplateStore.TEMPLATE_STORE.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<TerminologyProviderRecord, TenantRecord>
            TERMINOLOGY_PROVIDER__TERMINOLOGY_PROVIDER_SYS_TENANT_FKEY = Internal.createForeignKey(
                    TerminologyProvider.TERMINOLOGY_PROVIDER,
                    DSL.name("terminology_provider_sys_tenant_fkey"),
                    new TableField[] {TerminologyProvider.TERMINOLOGY_PROVIDER.SYS_TENANT},
                    Keys.TENANT_PKEY,
                    new TableField[] {Tenant.TENANT.ID},
                    true);
    public static final ForeignKey<UsersRecord, PartyIdentifiedRecord> USERS__USERS_PARTY_ID_FKEY =
            Internal.createForeignKey(
                    Users.USERS,
                    DSL.name("users_party_id_fkey"),
                    new TableField[] {Users.USERS.PARTY_ID, Users.USERS.SYS_TENANT},
                    Keys.PARTY_IDENTIFIED_PKEY,
                    new TableField[] {PartyIdentified.PARTY_IDENTIFIED.ID, PartyIdentified.PARTY_IDENTIFIED.SYS_TENANT},
                    true);
    public static final ForeignKey<UsersRecord, TenantRecord> USERS__USERS_SYS_TENANT_FKEY = Internal.createForeignKey(
            Users.USERS,
            DSL.name("users_sys_tenant_fkey"),
            new TableField[] {Users.USERS.SYS_TENANT},
            Keys.TENANT_PKEY,
            new TableField[] {Tenant.TENANT.ID},
            true);
}
