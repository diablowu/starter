/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringRestDocsProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class SpringRestDocsProjectGenerationConfigurationTests {

  private final ProjectAssetTester projectTester =
      new ProjectAssetTester()
          .withConfiguration(SpringRestDocsProjectGenerationConfiguration.class);

  @Test
  @SuppressWarnings("rawtypes")
  void springRestDocsCustomizerMaven() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setBuildSystem(new MavenBuildSystem());
    description.addDependency("restdocs", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context)
                .getBeans(BuildCustomizer.class)
                .containsKeys("restDocsMavenBuildCustomizer")
                .doesNotContainKeys("restDocsGradleBuildCustomizer"));
  }

  @Test
  @SuppressWarnings("rawtypes")
  void springRestDocsCustomizerGradle() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setBuildSystem(new GradleBuildSystem());
    description.addDependency("restdocs", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context)
                .getBeans(BuildCustomizer.class)
                .containsKeys("restDocsGradleBuildCustomizer")
                .doesNotContainKeys("restDocsMavenBuildCustomizer"));
  }

  @Test
  @SuppressWarnings("rawtypes")
  void springRestDocsNotAppliedIfRestDocsNotSelected() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setBuildSystem(new GradleBuildSystem());
    description.addDependency("web", mock(Dependency.class));
    this.projectTester.configure(
        description, (context) -> assertThat(context).getBeans(BuildCustomizer.class).isEmpty());
  }
}
