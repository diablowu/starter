/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudContractGradleBuildCustomizer}.
 *
 * @author Olga Maciaszek-Sharma
 * @author Madhura Bhave
 * @author Eddú Meléndez
 */
class SpringCloudContractGradleBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void springCloudContractVerifierPluginAddedWhenSCCDependencyPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    assertThat(gradleBuild(projectRequest))
        .containsSubsequence(
            "buildscript {",
            "dependencies {",
            "classpath 'org.springframework.cloud:spring-cloud-contract-gradle-plugin:")
        .contains("apply plugin: 'spring-cloud-contract'");
  }

  @Test
  void springCloudContractVerifierPluginNotAddedWhenSCCDependencyAbsent() {
    ProjectRequest projectRequest = createProjectRequest();
    assertThat(gradleBuild(projectRequest))
        .doesNotContain(
            "buildscript {",
            "classpath 'org.springframework.cloud:spring-cloud-contract-gradle-plugin:")
        .doesNotContain("apply plugin: 'spring-cloud-contract'");
  }

  @Test
  void springCloudContractVerifierPluginForSpringBootWithJUnit5ByDefault() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    projectRequest.setBootVersion("2.4.0");
    assertThat(gradleBuild(projectRequest))
        .containsSubsequence(
            "contracts {",
            "testFramework = org.springframework.cloud.contract.verifier.config.TestFramework.JUNIT5");
  }

  @Test
  void springCloudContractVerifierPlugin2WithNoContractTestConfiguration() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    projectRequest.setBootVersion("2.3.7.RELEASE");
    assertThat(gradleBuild(projectRequest)).doesNotContain("contractTest {");
  }

  @Test
  void springCloudContractVerifierPlugin30ContractTestWithJUnit5ByDefault() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    projectRequest.setBootVersion("2.4.1");
    assertThat(gradleBuild(projectRequest))
        .containsSubsequence("contractTest {", "useJUnitPlatform()");
  }

  @Test
  void springCloudContractVerifierPluginWithTestModeSetWhenWebFluxIsPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier", "webflux");
    assertThat(gradleBuild(projectRequest))
        .containsSubsequence("contracts {", "testMode = 'WebTestClient'");
  }

  @Test
  void springWebTestClientDependencyAddedWhenWebFluxIsPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier", "webflux");
    assertThat(gradleBuild(projectRequest))
        .contains("testImplementation 'io.rest-assured:spring-web-test-client'");
  }
}
