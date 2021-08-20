/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springsession;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringSessionBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringSessionBuildCustomizerTests extends AbstractExtensionTests {

  private static final Dependency REDIS =
      Dependency.withId(
          "session-data-redis", "org.springframework.session", "spring-session-data-redis");

  private static final Dependency JDBC =
      Dependency.withId("session-jdbc", "org.springframework.session", "spring-session-jdbc");

  @Test
  void noSessionWithRedis() {
    ProjectRequest request = createProjectRequest("data-redis");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("data-redis"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(2);
  }

  @Test
  void sessionWithNoStore() {
    ProjectRequest request = createProjectRequest("session", "data-jpa");
    assertThat(mavenPom(request))
        .hasDependency("org.springframework.session", "spring-session-core")
        .hasDependency(Dependency.createSpringBootStarter("data-jpa"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(3);
  }

  @Test
  void sessionWithRedis() {
    ProjectRequest request = createProjectRequest("session", "data-redis");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("data-redis"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(REDIS)
        .hasDependenciesSize(3);
  }

  @Test
  void sessionWithRedisReactive() {
    ProjectRequest request = createProjectRequest("session", "data-redis-reactive");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("data-redis-reactive"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(REDIS)
        .hasDependenciesSize(4); // TODO: side effect of `reactor-test`
  }

  @Test
  void sessionWithJdbc() {
    ProjectRequest request = createProjectRequest("session", "jdbc");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("jdbc"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(JDBC)
        .hasDependenciesSize(3);
  }

  @Test
  void sessionWithRedisAndJdbc() {
    ProjectRequest request = createProjectRequest("session", "data-redis", "jdbc");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("data-redis"))
        .hasDependency(Dependency.createSpringBootStarter("jdbc"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(REDIS)
        .hasDependency(JDBC)
        .hasDependenciesSize(5);
  }
}
