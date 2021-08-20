/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Tests for {@link SpringCloudProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
@SpringBootTest
class SpringCloudProjectGenerationConfigurationTests {

  @Autowired private ApplicationContext applicationContext;

  @Autowired private InitializrMetadataProvider metadataProvider;

  private final ProjectAssetTester projectTester =
      new ProjectAssetTester()
          .withContextInitializer((context) -> context.setParent(this.applicationContext))
          .withBean(InitializrMetadata.class, () -> this.metadataProvider.get())
          .withBean(Build.class, MavenBuild::new)
          .withConfiguration(SpringCloudProjectGenerationConfiguration.class);

  @Test
  void springCloudContractGradleBuildCustomizerWithGroovyDsl() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setBuildSystem(
        BuildSystem.forIdAndDialect(GradleBuildSystem.ID, GradleBuildSystem.DIALECT_GROOVY));
    description.addDependency("cloud-contract-verifier", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context).hasSingleBean(SpringCloudContractGradleBuildCustomizer.class));
  }

  @Test
  void springCloudContractGradleBuildCustomizerWithKotlinDsl() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setBuildSystem(
        BuildSystem.forIdAndDialect(GradleBuildSystem.ID, GradleBuildSystem.DIALECT_KOTLIN));
    description.addDependency("cloud-contract-verifier", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context).doesNotHaveBean(SpringCloudContractGradleBuildCustomizer.class));
  }
}
