/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.testcontainers;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.start.site.support.implicit.ImplicitDependency;
import io.spring.start.site.support.implicit.ImplicitDependencyBuildCustomizer;
import io.spring.start.site.support.implicit.ImplicitDependencyHelpDocumentCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for generation of projects that depend on Testcontainers.
 *
 * @author Maciej Walkowiak
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
public class TestcontainersProjectGenerationConfiguration {

  private final Iterable<ImplicitDependency> dependencies;

  public TestcontainersProjectGenerationConfiguration() {
    this.dependencies = TestcontainersModuleRegistry.create();
  }

  @Bean
  @ConditionalOnRequestedDependency("testcontainers")
  public ImplicitDependencyBuildCustomizer testContainersBuildCustomizer() {
    return new ImplicitDependencyBuildCustomizer(this.dependencies);
  }

  @Bean
  @ConditionalOnRequestedDependency("testcontainers")
  public ImplicitDependencyHelpDocumentCustomizer testcontainersHelpCustomizer(Build build) {
    return new ImplicitDependencyHelpDocumentCustomizer(this.dependencies, build);
  }
}
