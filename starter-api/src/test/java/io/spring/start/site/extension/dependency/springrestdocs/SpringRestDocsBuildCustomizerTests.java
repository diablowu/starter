/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringRestDocsBuildCustomizer}.
 *
 * @author Andy Wilkinson
 */
class SpringRestDocsBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void restDocsWithWebMvc() {
    ProjectRequest request = createProjectRequest("web", "restdocs");
    assertThat(mavenPom(request))
        .hasDependency("org.springframework.restdocs", "spring-restdocs-mockmvc", null, "test")
        .doesNotHaveDependency("org.springframework.restdocs", "spring-restdocs-webtestclient");
  }

  @Test
  void restDocsWithWebFlux() {
    ProjectRequest request = createProjectRequest("webflux", "restdocs");
    assertThat(mavenPom(request))
        .hasDependency(
            "org.springframework.restdocs", "spring-restdocs-webtestclient", null, "test")
        .doesNotHaveDependency("org.springframework.restdocs", "spring-restdocs-mockmvc");
  }

  @Test
  void restDocsWithJersey() {
    ProjectRequest request = createProjectRequest("jersey", "restdocs");
    assertThat(mavenPom(request))
        .hasDependency(
            "org.springframework.restdocs", "spring-restdocs-webtestclient", null, "test")
        .doesNotHaveDependency("org.springframework.restdocs", "spring-restdocs-mockmvc");
  }
}
