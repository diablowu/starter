/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.observability;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.language.Annotation;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.code.TestApplicationTypeCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for generation of projects that use observability.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
class ObservabilityProjectGenerationConfiguration {

  @Bean
  ObservabilityBuildCustomizer observabilityBuildCustomizer() {
    return new ObservabilityBuildCustomizer();
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnRequestedDependency("wavefront")
  static class Wavefront {

    @Bean
    WavefrontHelpDocumentCustomizer wavefrontHelpDocumentCustomizer(Build build) {
      return new WavefrontHelpDocumentCustomizer(build);
    }

    @Bean
    TestApplicationTypeCustomizer<?> wavefrontTestApplicationTypeCustomizer() {
      return (typeDeclaration) ->
          typeDeclaration.annotate(
              Annotation.name(
                  "org.springframework.test.context.TestPropertySource",
                  (ann) ->
                      ann.attribute(
                          "properties",
                          String.class,
                          "management.metrics.export.wavefront.enabled=false")));
    }
  }
}
