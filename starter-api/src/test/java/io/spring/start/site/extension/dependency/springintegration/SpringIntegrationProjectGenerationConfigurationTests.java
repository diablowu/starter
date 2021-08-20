/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springintegration;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link SpringIntegrationProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 * @author Artem Bilan
 */
class SpringIntegrationProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void buildWithOnlySpringIntegration() {
    Dependency integrationTest = integrationDependency("test");
    integrationTest.setScope(Dependency.SCOPE_TEST);
    assertThat(generateProject("integration"))
        .mavenBuild()
        .hasDependency(getDependency("integration"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(integrationTest);
  }

  @ParameterizedTest
  @MethodSource("supportedEntries")
  void buildWithSupportedEntries(String springBootDependencyId, String integrationModuleId) {
    assertThat(generateProject("integration", springBootDependencyId))
        .mavenBuild()
        .hasDependency(getDependency("integration"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(integrationDependency(integrationModuleId));
  }

  @ParameterizedTest
  @MethodSource("supportedEntries")
  void linkToSupportedEntriesWhenSpringIntegrationIsPresentIsAdded(
      String dependencyId, String moduleId) {
    assertHelpDocument("integration", dependencyId)
        .contains("https://docs.spring.io/spring-integration/reference/html/" + moduleId);
  }

  @ParameterizedTest
  @MethodSource("supportedEntries")
  void linkToSupportedEntriesWhenSpringIntegrationIsNotPresentIsNotAdded(
      String dependencyId, String moduleId) {
    assertHelpDocument(dependencyId)
        .doesNotContain("https://docs.spring.io/spring-integration/reference/html/" + moduleId);
  }

  static Stream<Arguments> supportedEntries() {
    return Stream.of(
        Arguments.arguments("activemq", "jms"),
        Arguments.arguments("artemis", "jms"),
        Arguments.arguments("amqp", "amqp"),
        Arguments.arguments("data-jdbc", "jdbc"),
        Arguments.arguments("jdbc", "jdbc"),
        Arguments.arguments("data-jpa", "jpa"),
        Arguments.arguments("data-mongodb", "mongodb"),
        Arguments.arguments("data-mongodb-reactive", "mongodb"),
        Arguments.arguments("data-r2dbc", "r2dbc"),
        Arguments.arguments("data-redis", "redis"),
        Arguments.arguments("data-redis-reactive", "redis"),
        Arguments.arguments("geode", "gemfire"),
        Arguments.arguments("kafka", "kafka"),
        Arguments.arguments("kafka-streams", "kafka"),
        Arguments.arguments("mail", "mail"),
        Arguments.arguments("rsocket", "rsocket"),
        Arguments.arguments("security", "security"),
        Arguments.arguments("web", "http"),
        Arguments.arguments("webflux", "webflux"),
        Arguments.arguments("websocket", "websocket"),
        Arguments.arguments("websocket", "stomp"),
        Arguments.arguments("web-services", "ws"));
  }

  @Test
  void linkToSupportedEntriesWhenTwoMatchesArePresentOnlyAddLinkOnce() {
    assertHelpDocument("testcontainers", "data-mongodb", "data-mongodb-reactive")
        .containsOnlyOnce("https://www.testcontainers.org/modules/databases/mongodb/");
  }

  private static Dependency integrationDependency(String id) {
    String integrationModule = "spring-integration-" + id;
    return io.spring.initializr.metadata.Dependency.withId(
        integrationModule,
        "org.springframework.integration",
        integrationModule,
        null,
        io.spring.initializr.metadata.Dependency.SCOPE_COMPILE);
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
