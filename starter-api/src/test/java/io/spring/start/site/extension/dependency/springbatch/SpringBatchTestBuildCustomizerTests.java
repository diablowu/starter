/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springbatch;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringBatchTestBuildCustomizer}.
 *
 * @author Tim Riemer
 */
class SpringBatchTestBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void batchTestIsAddedWithBatch() {
    ProjectRequest request = createProjectRequest("batch");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("batch"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(springBatchTest())
        .hasDependenciesSize(3);
  }

  @Test
  void batchTestIsNotAddedWithoutSpringBatch() {
    ProjectRequest request = createProjectRequest("web");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("web"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(2);
  }

  private static Dependency springBatchTest() {
    Dependency dependency =
        Dependency.withId("spring-batch-test", "org.springframework.batch", "spring-batch-test");
    dependency.setScope(Dependency.SCOPE_TEST);
    return dependency;
  }
}
