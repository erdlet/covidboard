package de.erdlet.covistat.adapter.database;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class FlywayRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayRunner.class);

    @Resource(name = DatabaseConstants.DATASOURCE_NAME)
    DataSource dataSource;

    @PostConstruct
    public void runFlywayMigration() {
        LOGGER.info("Running FlyWay migration...");

        final Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        LOGGER.info("Flyway constructed. Starting migration...");

        final MigrateResult migrateResult = flyway.migrate();

        if (migrateResult.migrations.isEmpty()) {
            LOGGER.info("Migration ran without warnings. Migrations executed: {}", migrateResult.migrationsExecuted);
            migrateResult.migrations.forEach(mig -> LOGGER.info("{} [{}]", mig.version, mig.description));
        } else {
            LOGGER.warn("Migration ran with warnings. Migrations executed: {}", migrateResult.migrationsExecuted);
            migrateResult.warnings.forEach(warn -> LOGGER.warn("{}", warn));
        }
    }
}
