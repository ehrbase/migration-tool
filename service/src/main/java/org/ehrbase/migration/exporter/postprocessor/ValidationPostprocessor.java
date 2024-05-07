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
package org.ehrbase.migration.exporter.postprocessor;

import com.nedap.archie.query.RMPathQuery;
import com.nedap.archie.rm.changecontrol.OriginalVersion;
import com.nedap.archie.rm.composition.Composition;
import com.nedap.archie.rmobjectvalidator.APathQueryCache;
import com.nedap.archie.rmobjectvalidator.RMObjectValidator;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.xmlbeans.XmlException;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.migration.dto.VersionedObjectData;
import org.ehrbase.openehr.sdk.validation.CompositionValidator;
import org.ehrbase.openehr.sdk.validation.ConstraintViolationException;
import org.ehrbase.openehr.sdk.validation.ValidationException;
import org.ehrbase.openehr.sdk.webtemplate.model.WebTemplate;
import org.ehrbase.openehr.sdk.webtemplate.templateprovider.TemplateProvider;
import org.openehr.schemas.v1.OPERATIONALTEMPLATE;

/**
 * Validate all {@link Composition}
 */
public class ValidationPostprocessor implements ExportPostprocessor {

    private final Map<String, RMPathQuery> rmPathQueryCache = new ConcurrentHashMap<>();

    private final Map<String, OPERATIONALTEMPLATE> templateCache = new ConcurrentHashMap<>();

    private final ThreadLocal<CompositionValidator> compositionValidator;

    public ValidationPostprocessor() {
        compositionValidator = ThreadLocal.withInitial(this::createCompositionValidator);
    }

    TemplateProvider createTemplateProvider(EhrSchemaData ehrSchemaData) {
        return templateId -> Optional.of(ehrSchemaData.getTemplateByTemplateId(templateId))
                .map(td -> templateCache.computeIfAbsent(td.templateId(), s -> {
                    try {
                        return org.openehr.schemas.v1.TemplateDocument.Factory.parse(td.content())
                                .getTemplate();
                    } catch (XmlException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    @Override
    public List<MigrationError> postExport(ExtendedEhr ehr, EhrSchemaData ehrSchemaData, EhrSchema t) {

        List<MigrationError> migrationErrorList = new ArrayList<>();

        if (ehr.getCompositions() != null) {
            TemplateProvider templateProvider = createTemplateProvider(ehrSchemaData);

            ehr.getCompositions().stream()
                    .map(VersionedObjectData::originalVersions)
                    .flatMap(Collection::stream)
                    .map(OriginalVersion::getData)
                    .filter(Objects::nonNull)
                    .forEach(d -> validate(ehr.getEhrId().toString(), d, templateProvider, migrationErrorList));
        }

        return migrationErrorList;
    }

    private void validate(
            String ehrId,
            Composition composition,
            TemplateProvider templateProvider,
            List<MigrationError> migrationErrorList) {
        try {
            check(composition, templateProvider);
        } catch (ValidationException v) {

            migrationErrorList.add(new MigrationError(
                    null, ehrId, composition.getUid().getValue(), "Template Validation for composition failed"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void check(String templateID, Composition composition, TemplateProvider templateProvider) throws Exception {
        WebTemplate webTemplate;
        try {
            webTemplate = templateProvider.buildIntrospect(templateID).orElseThrow();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e.getMessage());
        }

        // Validate the composition based on WebTemplate
        var constraintViolations = compositionValidator.get().validate(composition, webTemplate);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    public void check(Composition composition, TemplateProvider templateProvider) throws Exception {
        // check if this composition is valid for processing
        if (composition.getName() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: name");
        }
        if (composition.getArchetypeNodeId() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: archetype_node_id");
        }
        if (composition.getLanguage() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: language");
        }
        if (composition.getCategory() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: category");
        }
        if (composition.getComposer() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: composer");
        }
        if (composition.getArchetypeDetails() == null) {
            throw new IllegalArgumentException("Composition missing mandatory attribute: archetype details");
        }
        if (composition.getArchetypeDetails().getTemplateId() == null) {
            throw new IllegalArgumentException(
                    "Composition missing mandatory attribute: archetype details/template_id");
        }

        check(composition.getArchetypeDetails().getTemplateId().getValue(), composition, templateProvider);
    }

    private CompositionValidator createCompositionValidator() {
        APathQueryCache delegator = new APathQueryCache() {
            @Override
            public RMPathQuery getApathQuery(String query) {
                return rmPathQueryCache.computeIfAbsent(query, RMPathQuery::new);
            }
        };
        CompositionValidator validator = new CompositionValidator();

        setSharedAPathQueryCache(validator, delegator);
        return validator;
    }

    private static void setSharedAPathQueryCache(CompositionValidator validator, APathQueryCache delegator) {
        if (delegator == null) {
            return;
        }
        try {
            // as RMObjectValidator.queryCache is hard-coded, it is replaced via reflection
            Field queryCacheField = RMObjectValidator.class.getDeclaredField("queryCache");
            queryCacheField.setAccessible(true);
            queryCacheField.set(validator.getRmObjectValidator(), delegator);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Failed to inject shared RMPathQuery cache", e);
        }
    }
}
