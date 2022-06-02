package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
}
