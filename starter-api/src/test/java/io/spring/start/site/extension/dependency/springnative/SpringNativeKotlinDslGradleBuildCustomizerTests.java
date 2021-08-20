/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringNativeKotlinDslGradleBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
public class SpringNativeKotlinDslGradleBuildCustomizerTests
    extends SpringNativeGradleBuildCustomizerTests {

  @Override
  protected SpringNativeGradleBuildCustomizer createCustomizer() {
    return new SpringNativeKotlinDslGradleBuildCustomizer();
  }

  @Test
  @Override
  void gradleBuildCustomizeSpringBootPlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    assertThat(build.tasks().has("BootBuildImage")).isTrue();
    assertThat(build.tasks().importedTypes())
        .contains("org.springframework.boot.gradle.tasks.bundling.BootBuildImage");
  }

  @Test
  void gradleBuildWithJpaDoesNotAddHibernateEnhancePlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    build.dependencies().add("data-jpa", Dependency.withCoordinates("org.hibernate", "hibernate"));
    customizer.customize(build);
    assertThat(build.plugins().has("org.hibernate.orm")).isFalse();
    assertThat(build.getSettings().getPluginMappings()).isEmpty();
    assertThat(build.tasks().has("EnhanceTask")).isFalse();
  }
}
