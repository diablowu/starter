/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GradleDslProjectDescriptionCustomizer}.
 *
 * @author Stephane Nicoll
 */
class GradleDslProjectDescriptionCustomizerTests extends AbstractExtensionTests {

  @Test
  void kotlinDslIsUsedWithCompatibleVersionAndGradleAndKotlin() {
    ProjectRequest request = createProjectRequest();
    request.setType("gradle-project");
    request.setLanguage("kotlin");
    request.setBootVersion("2.3.0.RELEASE");
    ProjectStructure project = generateProject(request);
    assertThat(project).containsFiles("build.gradle.kts").doesNotContainFiles("build.gradle");
  }

  @Test
  void kotlinDslIsNotUsedWithCompatibleVersionAndGradleAndJava() {
    ProjectRequest request = createProjectRequest();
    request.setType("gradle-project");
    request.setLanguage("java");
    request.setBootVersion("2.3.0.RELEASE");
    ProjectStructure project = generateProject(request);
    assertThat(project).containsFiles("build.gradle").doesNotContainFiles("build.gradle.kts");
  }
}
