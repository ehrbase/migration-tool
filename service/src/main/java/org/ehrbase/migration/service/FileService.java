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
package org.ehrbase.migration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.io.IOUtils;
import org.ehrbase.migration.dto.EhrSchema;
import org.ehrbase.migration.dto.EhrSchemaData;
import org.ehrbase.migration.dto.ExtendedEhr;
import org.ehrbase.openehr.sdk.serialisation.jsonencoding.ArchieObjectMapperProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private static final ObjectMapper OM =
            ArchieObjectMapperProvider.getObjectMapper().copy();

    public static final String TENANTS_ZIP = "tenants.zip";
    public static final String EHR_SCHEMA_DATA_ZIP = "ehr_schema_data.zip";
    private static final String EHR_SCHEMA_DATA_JSON = "ehr_schema_data.json";

    @Value("${export.folder}")
    private String exportFolder;

    @Value("${import.folder}")
    private String importFolder;

    public void write(ProcessService.EhrBatch batch, EhrSchema t) {

        File base = Path.of(exportFolder, t.tenantId()).toFile();
        base.mkdirs();

        try (ZipFile zipFile = new ZipFile(new File(
                base,
                "ehrs_%s_%s.zip".formatted(batch.batch().start(), batch.batch().calcEndInclusive())))) {

            batch.ehrs().forEach(ehr -> addToZip(ehr, zipFile, ehr.getEhrId() + ".json"));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    public void write(EhrSchemaData ehrSchemaData, EhrSchema t) {
        File base = Path.of(exportFolder, t.tenantId()).toFile();
        base.mkdirs();

        try (ZipFile zipFile = new ZipFile(new File(base, EHR_SCHEMA_DATA_ZIP))) {
            addToZip(ehrSchemaData, zipFile, EHR_SCHEMA_DATA_JSON);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    public void write(List<EhrSchema> tenants) {
        File base = Path.of(exportFolder).toFile();
        base.mkdirs();

        try (ZipFile zipFile = new ZipFile(new File(base, TENANTS_ZIP))) {

            tenants.forEach(t -> addToZip(t, zipFile, t.tenantId() + ".json"));

        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    private static void addToZip(Object other, ZipFile zipFile, String fileNameInZip) {

        ZipParameters zp = new ZipParameters();
        zp.setFileNameInZip(fileNameInZip);

        try {
            zipFile.addStream(IOUtils.toInputStream(OM.writeValueAsString(other), StandardCharsets.UTF_8), zp);
        } catch (JsonProcessingException | ZipException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    public List<ExtendedEhr> readEhrs(String file, EhrSchema t) {
        File base = Path.of(importFolder, t.tenantId()).toFile();

        List<ExtendedEhr> ehrs;
        try (ZipFile zipFile = new ZipFile(new File(base, file))) {

            ehrs = read(zipFile, ExtendedEhr.class);

        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
        return ehrs;
    }

    public EhrSchemaData readEhrSchemaData(EhrSchema t) {
        File base = Path.of(importFolder, t.tenantId()).toFile();

        try (ZipFile zipFile = new ZipFile(new File(base, EHR_SCHEMA_DATA_ZIP))) {
            return read(zipFile, EhrSchemaData.class).get(0);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    public List<String> readEhrFileNames(EhrSchema t) {
        File base = Path.of(importFolder, t.tenantId()).toFile();
        try (Stream<Path> pathStream = Files.list(base.toPath())) {
            return pathStream
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(p -> p.contains("ehr"))
                    .toList();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    public List<EhrSchema> readTenants() {
        Path base = Path.of(importFolder);

        try (ZipFile zipFile = new ZipFile(base.resolve(TENANTS_ZIP).toFile())) {
            return read(zipFile, EhrSchema.class);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex.getMessage(), ex);
        }
    }

    private static <T> List<T> read(ZipFile zipFile, Class<T> tClass) throws ZipException {

        List<T> tenants = new ArrayList<>();
        zipFile.getFileHeaders().forEach(h -> {
            try { // XXX close or not?
                T e = OM.readValue(zipFile.getInputStream(h), tClass);
                tenants.add(e);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex.getMessage(), ex);
            }
        });

        return tenants;
    }
}
