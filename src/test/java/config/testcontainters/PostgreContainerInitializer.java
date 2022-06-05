package config.testcontainters;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

public final class PostgreContainerInitializer {
    private JdbcDatabaseContainer postgresContainer = new PostgreSQLContainerProvider().newInstance("14.2");

    private HikariDataSource dataSource;

    public void start() {
        Config conf = ConfigFactory.load();
        postgresContainer.start();
        postgresContainer.waitingFor(new HostPortWaitStrategy());
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(postgresContainer.getJdbcUrl());
        hikariConfig.setUsername(postgresContainer.getUsername());
        hikariConfig.setPassword(postgresContainer.getPassword());
        hikariConfig.setMaximumPoolSize(2);
        hikariConfig.setAutoCommit(true);
        dataSource = new HikariDataSource(hikariConfig);
    }

}
