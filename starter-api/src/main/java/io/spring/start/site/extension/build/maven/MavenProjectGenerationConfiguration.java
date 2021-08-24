/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for generation of projects that depend on Maven.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
class MavenProjectGenerationConfiguration {

  @Bean
  MavenBuildSystemHelpDocumentCustomizer mavenBuildSystemHelpDocumentCustomizer(
      ProjectDescription description) {
    return new MavenBuildSystemHelpDocumentCustomizer(description);
  }

  @Bean
  AnnotationProcessorExclusionBuildCustomizer annotationProcessorExclusionBuildCustomizer(
      InitializrMetadata metadata, ProjectDescription description) {
    return new AnnotationProcessorExclusionBuildCustomizer(
        metadata, description.getPlatformVersion());
  }
}
