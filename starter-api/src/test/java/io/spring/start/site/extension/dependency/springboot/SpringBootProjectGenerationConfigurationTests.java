/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringBootProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class SpringBootProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void gradleWithDevtoolsConfigureBuild() {
    ProjectRequest request = createProjectRequest("devtools");
    request.setBootVersion("2.4.8");
    assertThat(gradleBuild(request))
        .lines()
        .doesNotContain("configurations {")
        .contains("\tdevelopmentOnly 'org.springframework.boot:spring-boot-devtools'");
  }

  @Test
  void gradleWithoutDevtoolsDoesNotCreateDevelopmentOnlyConfiguration() {
    ProjectRequest request = createProjectRequest("web");
    request.setBootVersion("2.4.8");
    assertThat(gradleBuild(request)).doesNotContain("developmentOnly");
  }

  @Test
  void mavenWithDevtoolsIsOptional() {
    ProjectRequest request = createProjectRequest("devtools");
    assertThat(mavenPom(request))
        .hasText("/project/dependencies/dependency[2]/artifactId", "spring-boot-devtools")
        .hasText("/project/dependencies/dependency[2]/optional", "true");
  }

  @Test
  void mavenWithoutDevtoolsDoesNotChangeOptional() {
    ProjectRequest request = createProjectRequest("web");
    assertThat(mavenPom(request)).doesNotContain("optional");
  }
}
