/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springboot;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.dependency.devtools.DevToolsGradleBuildCustomizer;
import io.spring.initializr.generator.spring.dependency.devtools.DevToolsMavenBuildCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for customizations relevant for Spring Boot.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
public class SpringBootProjectGenerationConfiguration {

  private static final String DEVTOOLS_ID = "devtools";

  @Bean
  @ConditionalOnRequestedDependency(DEVTOOLS_ID)
  @ConditionalOnBuildSystem(MavenBuildSystem.ID)
  public DevToolsMavenBuildCustomizer devToolsMavenBuildCustomizer() {
    return new DevToolsMavenBuildCustomizer(DEVTOOLS_ID);
  }

  @Bean
  @ConditionalOnRequestedDependency(DEVTOOLS_ID)
  @ConditionalOnBuildSystem(GradleBuildSystem.ID)
  public DevToolsGradleBuildCustomizer devToolsGradleBuildCustomizer(
      ProjectDescription projectDescription) {
    return new DevToolsGradleBuildCustomizer(projectDescription.getPlatformVersion(), DEVTOOLS_ID);
  }
}
