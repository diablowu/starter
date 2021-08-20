/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springamqp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.test.project.ProjectAssetTester;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringAmqpProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class SpringAmqpProjectGenerationConfigurationTests {

  private final ProjectAssetTester projectTester =
      new ProjectAssetTester().withConfiguration(SpringAmqpProjectGenerationConfiguration.class);

  @Test
  void springAmqpTestWithAmqp() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.addDependency("amqp", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context)
                .getBeans(BuildCustomizer.class)
                .containsKeys("springAmqpTestBuildCustomizer"));
  }

  @Test
  void springAmqpTestWithoutAmqp() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.addDependency("another", mock(Dependency.class));
    this.projectTester.configure(
        description,
        (context) ->
            assertThat(context)
                .getBeans(BuildCustomizer.class)
                .doesNotContainKeys("springAmqpTestBuildCustomizer"));
  }
}
