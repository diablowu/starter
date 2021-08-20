/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.kotlin;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link KotlinCoroutinesCustomizer}.
 *
 * @author Stephane Nicoll
 */
class KotlinCoroutinesCustomizerTests extends AbstractExtensionTests {

  @Test
  void kotlinCoroutinesIsAdded() {
    ProjectRequest request = createProjectRequest("webflux");
    request.setBootVersion("2.5.0");
    request.setLanguage("kotlin");
    ProjectStructure project = generateProject(request);
    assertThat(project)
        .mavenBuild()
        .hasDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor");
    assertThat(project)
        .textFile("HELP.md")
        .contains(
            "* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/5.3.7/spring-framework-reference/languages.html#coroutines)");
  }

  @Test
  void kotlinCoroutinesIsNotAddedWithNonKotlinApp() {
    ProjectRequest request = createProjectRequest("webflux");
    request.setBootVersion("2.5.0");
    request.setLanguage("java");
    assertThat(mavenPom(request))
        .doesNotHaveDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor");
  }

  @Test
  void kotlinCoroutinesIsNotAddedWithoutReactiveFacet() {
    ProjectRequest request = createProjectRequest("web");
    request.setBootVersion("2.5.0");
    request.setLanguage("kotlin");
    assertThat(mavenPom(request))
        .doesNotHaveDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor");
  }
}
