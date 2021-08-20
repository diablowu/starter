/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradlePlugin;
import io.spring.initializr.generator.buildsystem.gradle.StandardGradlePlugin;
import io.spring.initializr.generator.version.VersionReference;
import org.junit.jupiter.api.Test;

/**
 * Shared tests for {@link SpringNativeGradleBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
abstract class SpringNativeGradleBuildCustomizerTests {

  protected abstract SpringNativeGradleBuildCustomizer createCustomizer();

  @Test
  void gradleBuildWithNativeRemoveNativeDependency() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    assertThat(build.dependencies().has("native")).isFalse();
  }

  @Test
  void gradleBuildWithNativeConfigureSpringAotPlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("1.0.0");
    customizer.customize(build);
    GradlePlugin springAotPlugin =
        build
            .plugins()
            .values()
            .filter((plugin) -> plugin.getId().equals("org.springframework.experimental.aot"))
            .findAny()
            .orElse(null);
    assertThat(springAotPlugin).isNotNull();
    assertThat(springAotPlugin)
        .isInstanceOf(StandardGradlePlugin.class)
        .satisfies(
            (plugin) ->
                assertThat(((StandardGradlePlugin) plugin).getVersion()).isEqualTo("1.0.0"));
  }

  @Test
  void gradleBuildWithNative09xDoesNotAddMavenCentral() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("0.9.2");
    customizer.customize(build);
    assertThat(build.pluginRepositories().ids()).doesNotContain("maven-central");
  }

  @Test
  void gradleBuildWithNative010AddMavenCentral() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("0.10.0");
    customizer.customize(build);
    assertThat(build.pluginRepositories().ids()).contains("maven-central");
  }

  @Test
  void gradleBuildWithNative09xDoesNotNativeBuildtoolsPlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("0.9.2");
    customizer.customize(build);
    assertThat(build.plugins().has("org.graalvm.buildtools.native")).isFalse();
  }

  @Test
  void gradleBuildWithNative010AddNativeBuildtoolsPlugin() {
    SpringNativeGradleBuildCustomizer customizer = createCustomizer();
    GradleBuild build = createBuild("0.10.0");
    customizer.customize(build);
    GradlePlugin springAotPlugin =
        build
            .plugins()
            .values()
            .filter((plugin) -> plugin.getId().equals("org.graalvm.buildtools.native"))
            .findAny()
            .orElse(null);
    assertThat(springAotPlugin).isNotNull();
    assertThat(springAotPlugin)
        .isInstanceOf(StandardGradlePlugin.class)
        .satisfies(
            (plugin) ->
                assertThat(((StandardGradlePlugin) plugin).getVersion()).isEqualTo("0.9.1"));
  }

  @Test
  abstract void gradleBuildCustomizeSpringBootPlugin();

  protected GradleBuild createBuild(String springNativeVersion) {
    GradleBuild build = new GradleBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue(springNativeVersion))
                .build());
    return build;
  }
}
