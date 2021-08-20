/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.Execution;
import io.spring.initializr.generator.buildsystem.maven.MavenProfile;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringNativeMavenBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringNativeMavenBuildCustomizerTests {

  @Test
  void versionPropertyIsSetOnDependency() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("2.0.0")));
    new SpringNativeMavenBuildCustomizer().customize(build);
    assertThat(build.properties().versions(VersionProperty::toStandardFormat))
        .singleElement()
        .satisfies(
            (entry) -> {
              assertThat(entry.getKey()).isEqualTo("spring-native.version");
              assertThat(entry.getValue()).isEqualTo("2.0.0");
            });
    assertThat(build.dependencies().get("native").getVersion())
        .isEqualTo(VersionReference.ofProperty("spring-native.version"));
  }

  @Test
  void nativeProfileNotAddedBefore010() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.9.2")));
    new SpringNativeMavenBuildCustomizer().customize(build);
    assertThat(build.profiles().ids()).doesNotContain("native");
  }

  @Test
  void repackageClassifierPropertyNotAddedBefore010() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.9.2")));
    new SpringNativeMavenBuildCustomizer().customize(build);
    assertThat(build.properties().has("repackage.classifier")).isFalse();
  }

  @Test
  void repackageClassifierPropertyAddedAsOf010() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.10.0")));
    new SpringNativeMavenBuildCustomizer().customize(build);
    assertThat(
            build
                .properties()
                .values()
                .filter((entry) -> entry.getKey().equals("repackage.classifier")))
        .singleElement()
        .satisfies((entry) -> assertThat(entry.getValue()).isEqualTo(""));
  }

  @Test
  void nativeProfileHasRepackageClassifierProperty() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.10.0")));
    assertThat(build)
        .satisfies(
            hasNativeProfile(
                (profile) ->
                    assertThat(
                            profile
                                .properties()
                                .values()
                                .filter((entry) -> entry.getKey().equals("repackage.classifier")))
                        .singleElement()
                        .satisfies((entry) -> assertThat(entry.getValue()).isEqualTo("exec"))));
  }

  @Test
  void nativeProfileHasBuildToolsProperty() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.10.0")));
    assertThat(build)
        .satisfies(
            hasNativeProfile(
                (profile) ->
                    assertThat(
                            profile
                                .properties()
                                .versions(VersionProperty::toStandardFormat)
                                .filter(
                                    (entry) -> entry.getKey().equals("native-buildtools.version")))
                        .singleElement()
                        .satisfies((entry) -> assertThat(entry.getValue()).isEqualTo("0.9.1"))));
  }

  @Test
  void nativeProfileHasDependencyToJunitPlatformNative() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.10.0")));
    assertThat(build)
        .satisfies(
            hasNativeProfile(
                (profile) -> {
                  Dependency dependency = profile.dependencies().get("junit-platform-native");
                  assertThat(dependency).isNotNull();
                  assertThat(dependency.getGroupId()).isEqualTo("org.graalvm.buildtools");
                  assertThat(dependency.getArtifactId()).isEqualTo("junit-platform-native");
                  assertThat(dependency.getVersion())
                      .isEqualTo(VersionReference.ofProperty("native-buildtools.version"));
                  assertThat(dependency.getScope()).isEqualTo(DependencyScope.TEST_RUNTIME);
                }));
  }

  @Test
  void nativeProfileHasPluginConfigurationForNativeMavenPlugin() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("0.10.0")));
    assertThat(build)
        .satisfies(
            hasNativeProfile(
                (profile) -> {
                  assertThat(profile.plugins().values())
                      .singleElement()
                      .satisfies(
                          (plugin) -> {
                            assertThat(plugin.getGroupId()).isEqualTo("org.graalvm.buildtools");
                            assertThat(plugin.getArtifactId()).isEqualTo("native-maven-plugin");
                            assertThat(plugin.getVersion())
                                .isEqualTo("${native-buildtools.version}");
                            assertThat(plugin.getExecutions()).hasSize(2);
                            Execution firstExecution = plugin.getExecutions().get(0);
                            assertThat(firstExecution.getId()).isEqualTo("test-native");
                            assertThat(firstExecution.getPhase()).isEqualTo("test");
                            assertThat(firstExecution.getGoals()).containsOnly("test");
                            Execution secondExecution = plugin.getExecutions().get(1);
                            assertThat(secondExecution.getId()).isEqualTo("build-native");
                            assertThat(secondExecution.getPhase()).isEqualTo("package");
                            assertThat(secondExecution.getGoals()).containsOnly("build");
                          });
                }));
  }

  private Consumer<MavenBuild> hasNativeProfile(Consumer<MavenProfile> profile) {
    return (build) -> {
      new SpringNativeMavenBuildCustomizer().customize(build);
      assertThat(build.profiles().ids()).containsOnly("native");
      assertThat(build.profiles().id("native")).satisfies(profile);
    };
  }
}
