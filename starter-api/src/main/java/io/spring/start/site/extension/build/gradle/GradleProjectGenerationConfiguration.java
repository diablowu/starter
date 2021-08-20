/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.gradle;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.generator.spring.build.gradle.DependencyManagementPluginVersionResolver;
import io.spring.initializr.generator.spring.build.gradle.InitializrDependencyManagementPluginVersionResolver;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for generation of projects that depend on Gradle.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(GradleBuildSystem.ID)
class GradleProjectGenerationConfiguration {

  @Bean
  GradleBuildSystemHelpDocumentCustomizer gradleBuildSystemHelpDocumentCustomizer(
      ProjectDescription description) {
    return new GradleBuildSystemHelpDocumentCustomizer(description);
  }

  @Bean
  DependencyManagementPluginVersionResolver dependencyManagementPluginVersionResolver(
      DependencyManagementVersionResolver versionResolver, InitializrMetadata metadata) {
    return new ManagedDependenciesDependencyManagementPluginVersionResolver(
        versionResolver,
        (description) ->
            new InitializrDependencyManagementPluginVersionResolver(metadata)
                .resolveDependencyManagementPluginVersion(description));
  }
}
