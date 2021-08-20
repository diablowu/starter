/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests for {@link JavaVersionProjectDescriptionCustomizer}.
 *
 * @author Stephane Nicoll
 */
class JavaVersionProjectDescriptionCustomizerTests extends AbstractExtensionTests {

  @Test
  void java8IsMandatoryMaven() {
    assertThat(mavenPom(javaProject("1.7", "2.3.0.RELEASE"))).hasProperty("java.version", "1.8");
  }

  @Test
  void java8IsMandatoryGradle() {
    assertThat(gradleBuild(javaProject("1.7", "2.3.0.RELEASE"))).hasSourceCompatibility("1.8");
  }

  @Test
  void javaUnknownVersionIsLeftAsIs() {
    assertThat(mavenPom(javaProject("9999999", "2.3.0.RELEASE")))
        .hasProperty("java.version", "9999999");
  }

  @Test
  void javaInvalidVersionIsLeftAsIs() {
    assertThat(mavenPom(javaProject("${another.version}", "2.3.0.RELEASE")))
        .hasProperty("java.version", "${another.version}");
  }

  @ParameterizedTest(name = "{0} - Java {1} - Spring Boot {2}")
  @MethodSource("supportedMavenParameters")
  void mavenBuildWithSupportedOptionsDoesNotDowngradeJavaVersion(
      String language, String javaVersion, String springBootVersion) {
    assertThat(mavenPom(project(language, javaVersion, springBootVersion)))
        .hasProperty("java.version", javaVersion);
  }

  @ParameterizedTest(name = "{0} - Java {1} - Spring Boot {2}")
  @MethodSource("supportedGradleGroovyParameters")
  void gradleGroovyBuildWithSupportedOptionsDoesNotDowngradeJavaVersion(
      String language, String javaVersion, String springBootVersion) {
    assertThat(gradleBuild(project(language, javaVersion, springBootVersion)))
        .hasSourceCompatibility(javaVersion);
  }

  static Stream<Arguments> supportedMavenParameters() {
    return Stream.concat(
        supportedJavaParameters(),
        Stream.concat(supportedKotlinParameters(), supportedGroovyParameters()));
  }

  static Stream<Arguments> supportedGradleGroovyParameters() {
    return Stream.concat(supportedJavaParameters(), supportedGroovyParameters());
  }

  private static Stream<Arguments> supportedJavaParameters() {
    return Stream.of(
        java("9", "2.3.0.RELEASE"),
        java("10", "2.3.0.RELEASE"),
        java("11", "2.3.0.RELEASE"),
        java("12", "2.3.0.RELEASE"),
        java("13", "2.3.0.RELEASE"),
        java("14", "2.3.0.RELEASE"),
        java("15", "2.3.4.RELEASE"),
        java("16", "2.5.0-RC1"));
  }

  private static Stream<Arguments> supportedKotlinParameters() {
    return Stream.of(
        kotlin("9", "2.3.0.RELEASE"),
        kotlin("10", "2.3.0.RELEASE"),
        kotlin("11", "2.3.0.RELEASE"),
        kotlin("12", "2.3.0.RELEASE"),
        kotlin("13", "2.3.0.RELEASE"),
        kotlin("14", "2.3.0.RELEASE"),
        kotlin("15", "2.3.4.RELEASE"),
        kotlin("16", "2.5.0-RC1"));
  }

  private static Stream<Arguments> supportedGroovyParameters() {
    return Stream.of(
        groovy("9", "2.3.0.RELEASE"),
        groovy("10", "2.3.0.RELEASE"),
        groovy("11", "2.3.0.RELEASE"),
        groovy("12", "2.3.0.RELEASE"),
        groovy("13", "2.3.0.RELEASE"),
        groovy("14", "2.3.0.RELEASE"),
        groovy("15", "2.3.4.RELEASE"),
        groovy("16", "2.5.0-RC1"));
  }

  @ParameterizedTest(name = "{0} - Java {1} - Spring Boot {2}")
  @MethodSource("unsupportedMavenParameters")
  void mavenBuildWithUnsupportedOptionsDowngradesToLts(
      String language, String javaVersion, String springBootVersion) {
    assertThat(mavenPom(project(language, javaVersion, springBootVersion)))
        .hasProperty("java.version", "11");
  }

  @ParameterizedTest(name = "{0} - Java {1} - Spring Boot {2}")
  @MethodSource("unsupportedGradleGroovyParameters")
  void gradleGroovyBuildWithUnsupportedOptionsDowngradesToLts(
      String language, String javaVersion, String springBootVersion) {
    assertThat(gradleBuild(project(language, javaVersion, springBootVersion)))
        .hasSourceCompatibility("11");
  }

  static Stream<Arguments> unsupportedMavenParameters() {
    return Stream.concat(
        unsupportedJavaParameters(),
        Stream.concat(unsupportedKotlinParameters(), unsupportedGroovyParameters()));
  }

  static Stream<Arguments> unsupportedGradleGroovyParameters() {
    return Stream.concat(unsupportedJavaParameters(), unsupportedGroovyParameters());
  }

  private static Stream<Arguments> unsupportedJavaParameters() {
    return Stream.of(java("16", "2.4.3"));
  }

  private static Stream<Arguments> unsupportedKotlinParameters() {
    return Stream.of(kotlin("16", "2.4.3"));
  }

  private static Stream<Arguments> unsupportedGroovyParameters() {
    return Stream.of(groovy("16", "2.4.3"));
  }

  private static Arguments java(String javaVersion, String springBootVersion) {
    return Arguments.of("java", javaVersion, springBootVersion);
  }

  private static Arguments kotlin(String javaVersion, String springBootVersion) {
    return Arguments.of("kotlin", javaVersion, springBootVersion);
  }

  private static Arguments groovy(String javaVersion, String springBootVersion) {
    return Arguments.of("groovy", javaVersion, springBootVersion);
  }

  private ProjectRequest project(String language, String javaVersion, String springBootVersion) {
    ProjectRequest request = createProjectRequest("web");
    request.setLanguage(language);
    request.setJavaVersion(javaVersion);
    request.setBootVersion(springBootVersion);
    return request;
  }

  private ProjectRequest javaProject(String javaVersion, String springBootVersion) {
    return project("java", javaVersion, springBootVersion);
  }
}
