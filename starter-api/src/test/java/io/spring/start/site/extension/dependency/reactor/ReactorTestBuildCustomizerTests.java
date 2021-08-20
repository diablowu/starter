/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ReactorTestBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class ReactorTestBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void reactorTestIsAdded() {
    ProjectRequest request = createProjectRequest("webflux");
    request.setBootVersion("2.5.0");
    Dependency reactorTest = Dependency.withId("reactor-test", "io.projectreactor", "reactor-test");
    reactorTest.setScope(Dependency.SCOPE_TEST);
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("webflux"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(reactorTest)
        .hasDependenciesSize(3);
  }

  @Test
  void reactorTestIsNotAddedWithoutReactiveFacet() {
    ProjectRequest request = createProjectRequest("web");
    request.setBootVersion("2.5.0");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("web"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(2);
  }
}
