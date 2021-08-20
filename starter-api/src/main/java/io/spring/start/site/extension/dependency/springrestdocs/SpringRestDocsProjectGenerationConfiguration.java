/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for generation of projects that depend on Spring REST
 * Docs.
 *
 * @author Andy Wilkinson
 */
@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("restdocs")
public class SpringRestDocsProjectGenerationConfiguration {

  @Bean
  public SpringRestDocsBuildCustomizer springRestDocsBuildCustomizer() {
    return new SpringRestDocsBuildCustomizer();
  }

  @Bean
  @ConditionalOnBuildSystem(GradleBuildSystem.ID)
  public SpringRestDocsGradleBuildCustomizer restDocsGradleBuildCustomizer() {
    return new SpringRestDocsGradleBuildCustomizer();
  }

  @Bean
  @ConditionalOnBuildSystem(MavenBuildSystem.ID)
  public SpringRestDocsMavenBuildCustomizer restDocsMavenBuildCustomizer() {
    return new SpringRestDocsMavenBuildCustomizer();
  }
}
