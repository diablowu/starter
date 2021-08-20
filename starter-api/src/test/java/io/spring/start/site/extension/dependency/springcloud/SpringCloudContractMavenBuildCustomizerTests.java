/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudContractMavenBuildCustomizer}.
 *
 * @author Olga Maciaszek-Sharma
 * @author Madhura Bhave
 * @author Eddú Meléndez
 */
class SpringCloudContractMavenBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void springCloudContractVerifierPluginAddedWhenSCCDependencyPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    assertThat(mavenPom(projectRequest))
        .hasDependency(getDependency("cloud-contract-verifier"))
        .hasText("/project/build/plugins/plugin[1]/groupId", "org.springframework.cloud")
        .hasText(
            "/project/build/plugins/plugin[1]/artifactId", "spring-cloud-contract-maven-plugin")
        .hasText("/project/build/plugins/plugin[1]/extensions", Boolean.toString(true));
  }

  @Test
  void springCloudContractVerifierPluginNotAddedWhenSCCDependencyAbsent() {
    ProjectRequest projectRequest = createProjectRequest();
    assertThat(mavenPom(projectRequest)).doesNotContain("spring-cloud-contract-maven-plugin");
  }

  @Test
  void springCloudContractVerifierPluginForSpringBootWithJUnit5ByDefault() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier");
    projectRequest.setBootVersion("2.4.0");
    assertThat(mavenPom(projectRequest))
        .hasText(
            "/project/build/plugins/plugin[1]/artifactId", "spring-cloud-contract-maven-plugin")
        .hasText("/project/build/plugins/plugin[1]/configuration/testFramework", "JUNIT5");
  }

  @Test
  void springCloudContractVerifierPluginWithTestModeSetWhenWebFluxIsPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier", "webflux");
    assertThat(mavenPom(projectRequest))
        .hasText(
            "/project/build/plugins/plugin[1]/artifactId", "spring-cloud-contract-maven-plugin")
        .hasText("/project/build/plugins/plugin[1]/configuration/testMode", "WEBTESTCLIENT");
  }

  @Test
  void springWebTestClientDependencyAddedWhenWebFluxIsPresent() {
    ProjectRequest projectRequest = createProjectRequest("cloud-contract-verifier", "webflux");
    Dependency springWebTestClientDep =
        Dependency.withId(
            "rest-assured-spring-web-test-client", "io.rest-assured", "spring-web-test-client");
    springWebTestClientDep.setScope(Dependency.SCOPE_TEST);
    assertThat(mavenPom(projectRequest)).hasDependency(springWebTestClientDep);
  }
}
