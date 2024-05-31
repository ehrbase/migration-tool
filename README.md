# EHRbase 2 Migration Tool

EHRbase 2 comes with a completely overhauled data structure that cannot be automatically migrated 
by deploying the new version over an older data structure.

This migration tool can be used to migrate the data from an existing EHRbase 0.x.x database 
into an empty database prepared for EHRbase 2.

## Prerequisites

If the source database is not yet on version 0.32.0, it will be migrated to that version.
Hence, it is recommended to use a clone of that database.

Before running the migration tool, make sure that the System ID used by EHRbase is consistent.
In the `ehr.system` table from the source database there should be exactly one entry with `system.description = 'DEFAULT RUNNING SYSTEM'`.
`system.settings` must match the `SERVER_NODENAME` environment variable (or spring configuration setting) provided to EHRbase.

The target database needs to be prepared according to the instructions in the [EHRbase 2.0.0 README](https://github.com/ehrbase/ehrbase/tree/v2.0.0?tab=readme-ov-file#1-setup-database).

## Performing the migration

The migration can be run directly from the source code, or the provided docker image. As a naming convention an `export`
datasource represents an EHRbase 0.x.x database where an `import` datasource represents an EHRbase 2 database.

The properties must be adjusted according to the set-up:

### Spring boot
```
mvn package

java -jar application/target/migration-tool.jar \
-Dmode=DB2DB \
-Dspring.datasource.import.url=jdbc:postgresql://localhost:5432/ehrbase_new \
-Dspring.datasource.import.username=ehrbase \
-Dspring.datasource.import.password=ehrbase \
-Dspring.datasource.export.url=jdbc:postgresql://localhost:5432/ehrbase_old \
-Dspring.datasource.export.username=ehrbase \
-Dspring.datasource.export.password=ehrbase \
-Dimport.ehrbase-db-user=ehrbase_restricted
```

### Docker
```
mvn verify

docker run ehrbase/migration-tool:1.1.0 \
-e mode=DB2DB \
-e spring.datasource.import.url=jdbc:postgresql://localhost:5432/ehrbase_new \
-e spring.datasource.import.username=ehrbase \
-e spring.datasource.import.password=ehrbase \
-e spring.datasource.export.url=jdbc:postgresql://localhost:5432/ehrbase_old \
-e spring.datasource.export.username=ehrbase \
-e spring.datasource.export.password=ehrbase \
-e import.ehrbase-db-user=ehrbase_restricted
```

In case the migration fails, the target database should be dropped and recreated.

Should the migration be run against an already migrated database, 
the migration will fail with an error, mentioning already existing users.
