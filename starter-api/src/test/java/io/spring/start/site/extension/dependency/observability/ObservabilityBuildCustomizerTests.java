/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ObservabilityBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class ObservabilityBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void actuatorIsAddedWithDataDog() {
    assertThat(generateProject("datadog")).mavenBuild().hasDependency(getDependency("actuator"));
  }

  @Test
  void actuatorIsAddedWithInflux() {
    assertThat(generateProject("influx")).mavenBuild().hasDependency(getDependency("actuator"));
  }

  @Test
  void actuatorIsAddedWithGraphite() {
    assertThat(generateProject("graphite")).mavenBuild().hasDependency(getDependency("actuator"));
  }

  @Test
  void actuatorIsAddedWithNewRelic() {
    assertThat(generateProject("new-relic")).mavenBuild().hasDependency(getDependency("actuator"));
  }

  @Test
  void actuatorIsNotAddedWithWavefrontStarter() {
    Dependency actuator = getDependency("actuator");
    assertThat(generateProject("wavefront"))
        .mavenBuild()
        .doesNotHaveDependency(actuator.getGroupId(), actuator.getArtifactId());
  }

  private ProjectStructure generateProject(String... dependencies) {
    ProjectRequest request = createProjectRequest(dependencies);
    request.setType("maven-build");
    return generateProject(request);
  }
}
