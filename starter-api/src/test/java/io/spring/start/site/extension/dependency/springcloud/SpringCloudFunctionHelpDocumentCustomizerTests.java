/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudFunctionHelpDocumentCustomizer}.
 *
 * @author Olga Maciaszek-Sharma
 */
class SpringCloudFunctionHelpDocumentCustomizerTests extends AbstractExtensionTests {

  private static final String AZURE_SECTION_TITLE =
      "## Running Spring Cloud Function applications on Microsoft Azure";

  @Test
  void functionBuildSetupInfoSectionAddedForMaven() {
    ProjectRequest request = createProjectRequest();
    request.setBootVersion("2.4.0");
    request.setType("maven-build");
    request.setDependencies(Arrays.asList("cloud-function", "azure-support"));
    assertThat(generateProject(request)).textFile("HELP.md").contains(AZURE_SECTION_TITLE);
  }

  @Test
  void functionBuildSetupInfoSectionAddedForGradle() {
    ProjectRequest request = createProjectRequest();
    request.setBootVersion("2.4.0");
    request.setType("gradle-build");
    request.setDependencies(Arrays.asList("cloud-function", "azure-support"));
    assertThat(generateProject(request)).textFile("HELP.md").contains(AZURE_SECTION_TITLE);
  }

  @Test
  void functionBuildSetupInfoSectionNotAddedWhenFunctionAndCloudDependenciesAbsent() {
    ProjectRequest request = createProjectRequest();
    assertThat(generateProject(request)).textFile("HELP.md").doesNotContain(AZURE_SECTION_TITLE);
  }
}
