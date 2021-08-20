/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link TestcontainersProjectGenerationConfiguration}.
 *
 * @author Maciej Walkowiak
 * @author Stephane Nicoll
 */
class TestcontainersProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void buildWithOnlyTestContainers() {
    assertThat(generateProject("testcontainers"))
        .mavenBuild()
        .hasBom("org.testcontainers", "testcontainers-bom", "${testcontainers.version}")
        .hasDependency(getDependency("testcontainers"));
  }

  @ParameterizedTest
  @MethodSource("supportedEntriesBuild")
  void buildWithSupportedEntries(String springBootDependencyId, String testcontainersArtifactId) {
    assertThat(generateProject("testcontainers", springBootDependencyId))
        .mavenBuild()
        .hasBom("org.testcontainers", "testcontainers-bom", "${testcontainers.version}")
        .hasDependency(getDependency(springBootDependencyId))
        .hasDependency("org.testcontainers", testcontainersArtifactId, null, "test")
        .hasDependency(getDependency("testcontainers"));
  }

  static Stream<Arguments> supportedEntriesBuild() {
    return Stream.of(
        Arguments.arguments("amqp", "rabbitmq"),
        Arguments.arguments("data-cassandra", "cassandra"),
        Arguments.arguments("data-cassandra-reactive", "cassandra"),
        Arguments.arguments("data-couchbase", "couchbase"),
        Arguments.arguments("data-couchbase-reactive", "couchbase"),
        Arguments.arguments("data-elasticsearch", "elasticsearch"),
        Arguments.arguments("data-mongodb", "mongodb"),
        Arguments.arguments("data-mongodb-reactive", "mongodb"),
        Arguments.arguments("data-neo4j", "neo4j"),
        Arguments.arguments("data-r2dbc", "r2dbc"),
        Arguments.arguments("db2", "db2"),
        Arguments.arguments("kafka", "kafka"),
        Arguments.arguments("kafka-streams", "kafka"),
        Arguments.arguments("mariadb", "mariadb"),
        Arguments.arguments("mysql", "mysql"),
        Arguments.arguments("postgresql", "postgresql"),
        Arguments.arguments("oracle", "oracle-xe"),
        Arguments.arguments("sqlserver", "mssqlserver"));
  }

  @ParameterizedTest
  @MethodSource("supportedEntriesHelpDocument")
  void linkToSupportedEntriesWhenTestContainerIsPresentIsAdded(
      String dependencyId, String docHref) {
    assertHelpDocument("testcontainers", dependencyId)
        .contains("https://www.testcontainers.org/modules/" + docHref);
  }

  @ParameterizedTest
  @MethodSource("supportedEntriesHelpDocument")
  void linkToSupportedEntriesWhenTestContainerIsNotPresentIsNotAdded(
      String dependencyId, String docHref) {
    assertHelpDocument(dependencyId)
        .doesNotContain("https://www.testcontainers.org/modules/" + docHref);
  }

  static Stream<Arguments> supportedEntriesHelpDocument() {
    return Stream.of(
        Arguments.arguments("amqp", "rabbitmq/"),
        Arguments.arguments("data-cassandra", "databases/cassandra/"),
        Arguments.arguments("data-cassandra-reactive", "databases/cassandra/"),
        Arguments.arguments("data-couchbase", "databases/couchbase/"),
        Arguments.arguments("data-couchbase-reactive", "databases/couchbase/"),
        Arguments.arguments("data-elasticsearch", "elasticsearch/"),
        Arguments.arguments("data-mongodb", "databases/mongodb/"),
        Arguments.arguments("data-mongodb-reactive", "databases/mongodb/"),
        Arguments.arguments("data-neo4j", "databases/neo4j/"),
        Arguments.arguments("data-r2dbc", "databases/r2dbc/"),
        Arguments.arguments("db2", "databases/db2"),
        Arguments.arguments("kafka", "kafka/"),
        Arguments.arguments("kafka-streams", "kafka/"),
        Arguments.arguments("mariadb", "databases/mariadb/"),
        Arguments.arguments("mysql", "databases/mysql/"),
        Arguments.arguments("postgresql", "databases/postgres/"),
        Arguments.arguments("oracle", "databases/oraclexe/"),
        Arguments.arguments("sqlserver", "databases/mssqlserver/"));
  }

  @Test
  void linkToSupportedEntriesWhenTwoMatchesArePresentOnlyAddLinkOnce() {
    assertHelpDocument("testcontainers", "data-mongodb", "data-mongodb-reactive")
        .containsOnlyOnce("https://www.testcontainers.org/modules/databases/mongodb/");
  }

  private ProjectStructure generateProject(String... dependencies) {
    ProjectRequest request = createProjectRequest(dependencies);
    request.setType("maven-build");
    return generateProject(request);
  }

  private TextAssert assertHelpDocument(String... dependencyIds) {
    ProjectRequest request = createProjectRequest(dependencyIds);
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md"));
  }
}
