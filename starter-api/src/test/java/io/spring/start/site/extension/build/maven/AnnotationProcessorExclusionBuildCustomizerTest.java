/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.maven;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link AnnotationProcessorExclusionBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class AnnotationProcessorExclusionBuildCustomizerTest extends AbstractExtensionTests {

  @Test
  void annotationProcessorsAreExcludedWithoutMetadata() {
    ProjectRequest request = createProjectRequest("lombok", "configuration-processor");
    request.setBootVersion("2.3.0.RELEASE");
    assertThat(mavenPom(request))
        .lines()
        .containsSequence(
            "					<excludes>",
            "						<exclude>",
            "							<groupId>org.projectlombok</groupId>",
            "							<artifactId>lombok</artifactId>",
            "						</exclude>",
            "						<exclude>",
            "							<groupId>org.springframework.boot</groupId>",
            "							<artifactId>spring-boot-configuration-processor</artifactId>",
            "						</exclude>",
            "					</excludes>");
  }

  @Test
  void annotationProcessorsAreExcludedOnlyIfTheyAreNotHandledWithMetadata() {
    ProjectRequest request = createProjectRequest("lombok", "configuration-processor");
    request.setBootVersion("2.4.0");
    assertThat(mavenPom(request))
        .lines()
        .containsSequence(
            "					<excludes>",
            "						<exclude>",
            "							<groupId>org.projectlombok</groupId>",
            "							<artifactId>lombok</artifactId>",
            "						</exclude>",
            "					</excludes>");
  }

  @Test
  void nonAnnotationProcessorsAreIgnored() {
    ProjectRequest request = createProjectRequest("web");
    request.setBootVersion("2.4.0");
    assertThat(mavenPom(request))
        .lines()
        .doesNotContainSequence(
            "						<exclude>",
            "							<groupId>org.springframework.boot</groupId>",
            "							<artifactId>spring-boot-starter-web</artifactId>",
            "						</exclude>");
  }
}
