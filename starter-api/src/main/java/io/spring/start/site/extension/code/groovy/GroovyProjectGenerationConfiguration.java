/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.groovy;

import io.spring.initializr.generator.condition.ConditionalOnLanguage;
import io.spring.initializr.generator.language.groovy.GroovyLanguage;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for generation of projects that use the Groovy language.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
@ConditionalOnLanguage(GroovyLanguage.ID)
class GroovyProjectGenerationConfiguration {

  @Bean
  BuildCustomizer<?> groovy3CompatibilityBuildCustomizer(ProjectDescription description) {
    return new Groovy3CompatibilityBuildCustomer(description.getLanguage().jvmVersion());
  }
}
