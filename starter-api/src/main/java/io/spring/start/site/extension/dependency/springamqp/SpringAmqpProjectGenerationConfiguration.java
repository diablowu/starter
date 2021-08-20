/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springamqp;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Configuration for generation of projects that depend on Spring AMQP.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency("amqp")
class SpringAmqpProjectGenerationConfiguration {

  @Bean
  SpringRabbitTestBuildCustomizer springAmqpTestBuildCustomizer() {
    return new SpringRabbitTestBuildCustomizer();
  }
}
