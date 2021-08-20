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
 * Tests for {@link SpringSecurityRSocketBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringSecurityRSocketBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void securityRSocketIsAddedWithSecurityAndRSocket() {
    ProjectRequest request = createProjectRequest("security", "rsocket");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("security"))
        .hasDependency(Dependency.createSpringBootStarter("rsocket"))
        .hasDependency("org.springframework.security", "spring-security-messaging")
        .hasDependency("org.springframework.security", "spring-security-rsocket");
  }

  @Test
  void securityRSocketIsNotAddedWithRSocketOnly() {
    ProjectRequest request = createProjectRequest("rsocket");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("rsocket"))
        .doesNotHaveDependency("org.springframework.security", "spring-security-messaging")
        .doesNotHaveDependency("org.springframework.security", "spring-security-rsocket");
  }

  @Test
  void securityRSocketIsNotAddedWithSecurityOnly() {
    ProjectRequest request = createProjectRequest("security");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("security"))
        .doesNotHaveDependency("org.springframework.security", "spring-security-messaging")
        .doesNotHaveDependency("org.springframework.security", "spring-security-rsocket");
  }
}
