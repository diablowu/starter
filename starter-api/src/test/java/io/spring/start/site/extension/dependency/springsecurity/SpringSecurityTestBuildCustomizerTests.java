/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springsecurity;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringSecurityTestBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringSecurityTestBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void securityTestIsAddedWithSecurity() {
    ProjectRequest request = createProjectRequest("security");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("security"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(springSecurityTest())
        .hasDependenciesSize(3);
  }

  @Test
  void securityTestIsNotAddedWithoutSpringSecurity() {
    ProjectRequest request = createProjectRequest("web");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("web"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(2);
  }

  private static Dependency springSecurityTest() {
    Dependency dependency =
        Dependency.withId(
            "spring-security-test", "org.springframework.security", "spring-security-test");
    dependency.setScope(Dependency.SCOPE_TEST);
    return dependency;
  }
}
