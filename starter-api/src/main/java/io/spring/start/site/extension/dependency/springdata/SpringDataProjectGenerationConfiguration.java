/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springdata;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for generation of projects that depend on Spring Data.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
public class SpringDataProjectGenerationConfiguration {

  @Bean
  @ConditionalOnRequestedDependency("data-r2dbc")
  public R2dbcBuildCustomizer r2dbcBuildCustomizer() {
    return new R2dbcBuildCustomizer();
  }

  @Bean
  @ConditionalOnRequestedDependency("data-r2dbc")
  public R2dbcHelpDocumentCustomizer r2dbcHelpDocumentCustomizer(Build build) {
    return new R2dbcHelpDocumentCustomizer(build);
  }
}
