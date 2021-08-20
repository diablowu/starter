/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.gradle;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GradleBuildSystemHelpDocumentCustomizer}.
 *
 * @author Jenn Strater
 * @author Andy Wilkinson
 */
class GradleBuildSystemHelpDocumentCustomizerTests extends AbstractExtensionTests {

  @Test
  void linksAddedToHelpDocumentForGradleBuild() {
    assertHelpDocument("gradle-build", "2.5.0")
        .contains(
            "* [Official Gradle documentation](https://docs.gradle.org)",
            "* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)",
            "* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/gradle-plugin/reference/html/)",
            "* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.0/gradle-plugin/reference/html/#build-image)");
  }

  @Test
  void linksNotAddedToHelpDocumentForMavenBuild() {
    assertHelpDocument("maven-build", "2.5.0")
        .doesNotContain(
            "* [Official Gradle documentation](https://docs.gradle.org)",
            "* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)",
            "* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.0/gradle-plugin/reference/html/)");
  }

  private ListAssert<String> assertHelpDocument(String type, String version) {
    ProjectRequest request = createProjectRequest("web");
    request.setType(type);
    request.setBootVersion(version);
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md")).lines();
  }
}
