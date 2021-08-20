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
 * Tests for {@link ReactorKotlinExtensionsCustomizer}.
 *
 * @author Eddú Meléndez
 */
class ReactorKotlinExtensionTests extends AbstractExtensionTests {

  @Test
  void reactorKotlinExtensionsIsAdded() {
    ProjectRequest request = createProjectRequest("webflux");
    request.setBootVersion("2.5.0");
    request.setLanguage("kotlin");
    ProjectStructure project = generateProject(request);
    assertThat(project)
        .mavenBuild()
        .hasDependency("io.projectreactor.kotlin", "reactor-kotlin-extensions");
  }

  @Test
  void reactorKotlinExtensionsIsNotAddedWithNonKotlinApp() {
    ProjectRequest request = createProjectRequest("webflux");
    request.setBootVersion("2.5.0");
    request.setLanguage("java");
    assertThat(mavenPom(request))
        .doesNotHaveDependency("io.projectreactor.kotlin", "reactor-kotlin-extensions");
  }

  @Test
  void reactorKotlinExtensionsIsNotAddedWithoutReactiveFacet() {
    ProjectRequest request = createProjectRequest("web");
    request.setBootVersion("2.5.0");
    request.setLanguage("kotlin");
    assertThat(mavenPom(request))
        .doesNotHaveDependency("io.projectreactor.kotlin", "reactor-kotlin-extensions");
  }
}
