/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.version.VersionReference;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringNativeGradleBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
public class SpringNativeGroovyDslGradleBuildCustomizerTests
    extends SpringNativeGradleBuildCustomizerTests {

  @Override
  protected SpringNativeGradleBuildCustomizer createCustomizer() {
    return createCustomizer(() -> VersionReference.ofValue("1.0.0"));
  }

  @Test
  @Override
  void gradleBuildCustomizeSpringBootPlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    assertThat(build.tasks().has("bootBuildImage")).isTrue();
  }

  @Test
  void gradleBuildWithJpaConfigureHibernateEnhancePlugin() {
    SpringNativeGradleBuildCustomizer customizer =
        createCustomizer(() -> VersionReference.ofValue("5.4.2.Final"));
    GradleBuild build = createBuild("1.0.0");
    build.dependencies().add("data-jpa", Dependency.withCoordinates("org.hibernate", "hibernate"));
    customizer.customize(build);
    assertThat(build.plugins().has("org.hibernate.orm")).isTrue();
    assertThat(build.getSettings().getPluginMappings())
        .singleElement()
        .satisfies(
            (pluginMapping) -> {
              assertThat(pluginMapping.getId()).isEqualTo("org.hibernate.orm");
              assertThat(pluginMapping.getDependency())
                  .satisfies(
                      (dependency) -> {
                        assertThat(dependency.getGroupId()).isEqualTo("org.hibernate");
                        assertThat(dependency.getArtifactId()).isEqualTo("hibernate-gradle-plugin");
                        assertThat(dependency.getVersion().isProperty()).isFalse();
                        assertThat(dependency.getVersion().getValue()).isEqualTo("5.4.2.Final");
                      });
            });
    assertThat(build.tasks().has("hibernate")).isTrue();
  }

  @Test
  void gradleBuildWithoutJpaDoesNotConfigureHibernateEnhancePlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    assertThat(build.plugins().has("org.hibernate.orm")).isFalse();
    assertThat(build.getSettings().getPluginMappings()).isEmpty();
    assertThat(build.tasks().has("hibernate")).isFalse();
  }

  @Test
  @SuppressWarnings("unchecked")
  void gradleBuildWithoutJpaDoesNotRequireHibernateVersion() {
    Supplier<VersionReference> hibernateVersionSupplier = mock(Supplier.class);
    SpringNativeGradleBuildCustomizer customizer = createCustomizer(hibernateVersionSupplier);
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    verifyNoInteractions(hibernateVersionSupplier);
  }

  private SpringNativeGroovyDslGradleBuildCustomizer createCustomizer(
      Supplier<VersionReference> hibernateVersionSupplier) {
    return new SpringNativeGroovyDslGradleBuildCustomizer(hibernateVersionSupplier);
  }
}
