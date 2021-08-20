/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.vaadin;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.scm.git.GitIgnoreCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for generation of projects that depend on Vaadin.
 *
 * @author Stephane Nicoll
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnRequestedDependency("vaadin")
class VaadinProjectGenerationConfiguration {

  @Bean
  @ConditionalOnBuildSystem(MavenBuildSystem.ID)
  VaadinMavenBuildCustomizer vaadinMavenBuildCustomizer() {
    return new VaadinMavenBuildCustomizer();
  }

  @Bean
  @ConditionalOnBuildSystem(GradleBuildSystem.ID)
  BuildCustomizer<GradleBuild> vaadinGradleBuildCustomizer() {
    return (build) -> build.plugins().add("com.vaadin", (plugin) -> plugin.setVersion("0.14.6.0"));
  }

  @Bean
  GitIgnoreCustomizer vaadinGitIgnoreCustomizer() {
    return (gitignore) -> gitignore.getGeneral().add("node_modules");
  }
}
