package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;

public class DatabaseOps {

  public static ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql)
      throws SQLException {
    DataSource ds = getDataSource(container);
    Statement statement = ds.getConnection().createStatement();
    statement.execute(sql);
    ResultSet resultSet = statement.getResultSet();
    if (resultSet != null) {
      resultSet.next();
    }
    return resultSet;
  }

  private static DataSource getDataSource(JdbcDatabaseContainer<?> container) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(container.getJdbcUrl());
    hikariConfig.setUsername(container.getUsername());
    hikariConfig.setPassword(container.getPassword());
    hikariConfig.setDriverClassName(container.getDriverClassName());
    hikariConfig.setMaximumPoolSize(1);
    return new HikariDataSource(hikariConfig);
  }

  public static void initializeContainerWithEmptyDatabase(JdbcDatabaseContainer<?> container) {
    container.start();
    String parentPath = Path.of(System.getProperty("user.dir")).getParent().toString();
    Path path =
            Path.of(parentPath + "/backend/src/main/resources/queries/database_initialization.sql");
    try {
      performQuery(container, Files.readString(path));
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }
}
