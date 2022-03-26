package persistence;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;

public abstract class Persistence {
  protected final Jdbi jdbi;

  Persistence() {
    Config conf = ConfigFactory.load();
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setJdbcUrl(conf.getString("mozart.database.url"));
    hikariConfig.setUsername(conf.getString("mozart.database.username"));
    hikariConfig.setPassword(conf.getString("mozart.database.password"));
    hikariConfig.setMaximumPoolSize(conf.getInt("mozart.database.connection-pool"));
    DataSource dataSource = new HikariDataSource(hikariConfig);
    jdbi = Jdbi.create(dataSource);
    jdbi.registerArrayType(int.class, "integer");
  }
}
