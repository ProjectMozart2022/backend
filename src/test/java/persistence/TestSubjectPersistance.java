package persistence;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class TestSubjectPersistance {
  private static final PostgreSQLContainer databaseContainer =
      new PostgreSQLContainer(DockerImageName.parse("postgres:alpine3.14"));
}
