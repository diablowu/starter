/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenProfile;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionProperty;
import io.spring.initializr.generator.version.VersionReference;
import org.springframework.core.Ordered;

/**
 * A {@link BuildCustomizer} that configures Spring Native for Maven.
 *
 * @author Stephane Nicoll
 */
class SpringNativeMavenBuildCustomizer implements BuildCustomizer<MavenBuild>, Ordered {

  @Override
  public void customize(MavenBuild build) {
    Dependency dependency = build.dependencies().get("native");
    String springNativeVersion = dependency.getVersion().getValue();

    // Native build tools
    String nativeBuildToolsVersion =
        SpringNativeBuildtoolsVersionResolver.resolve(springNativeVersion);
    if (nativeBuildToolsVersion != null) {
      build.properties().property("repackage.classifier", "");
    }

    // Expose a property
    build.properties().version(VersionProperty.of("spring-native.version"), springNativeVersion);

    // Update dependency to reuse the property
    build
        .dependencies()
        .add(
            "native",
            Dependency.from(dependency)
                .version(VersionReference.ofProperty("spring-native.version")));

    // AOT plugin
    build
        .plugins()
        .add(
            "org.springframework.experimental",
            "spring-aot-maven-plugin",
            (plugin) ->
                plugin
                    .version("${spring-native.version}")
                    .execution("test-generate", (execution) -> execution.goal("test-generate"))
                    .execution("generate", (execution) -> execution.goal("generate")));

    // Spring Boot plugin
    build
        .plugins()
        .add(
            "org.springframework.boot",
            "spring-boot-maven-plugin",
            (plugin) ->
                plugin.configuration(
                    (configuration) -> {
                      if (nativeBuildToolsVersion != null) {
                        configuration.add("classifier", "${repackage.classifier}");
                      }
                      configuration.add(
                          "image",
                          (image) -> {
                            image.add("builder", "paketobuildpacks/builder:tiny");
                            image.add("env", (env) -> env.add("BP_NATIVE_IMAGE", "true"));
                          });
                    }));

    if (build.dependencies().has("data-jpa")) {
      configureHibernateEnhancePlugin(build);
    }

    if (nativeBuildToolsVersion != null) {
      configureNativeProfile(build, nativeBuildToolsVersion);
    }
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 10;
  }

  private void configureHibernateEnhancePlugin(MavenBuild build) {
    build
        .plugins()
        .add(
            "org.hibernate.orm.tooling",
            "hibernate-enhance-maven-plugin",
            (plugin) ->
                plugin
                    .version("${hibernate.version}")
                    .execution(
                        "enhance",
                        (execution) ->
                            execution
                                .goal("enhance")
                                .configuration(
                                    (configuration) ->
                                        configuration
                                            .add("failOnError", "true")
                                            .add("enableLazyInitialization", "true")
                                            .add("enableDirtyTracking", "true")
                                            .add("enableAssociationManagement", "true")
                                            .add("enableExtendedEnhancement", "false"))));
  }

  private void configureNativeProfile(MavenBuild build, String nativeBuildToolsVersion) {
    MavenProfile profile = build.profiles().id("native");
    profile.properties().version("native-buildtools.version", nativeBuildToolsVersion);
    profile.properties().property("repackage.classifier", "exec");
    profile
        .dependencies()
        .add(
            "junit-platform-native",
            Dependency.withCoordinates("org.graalvm.buildtools", "junit-platform-native")
                .version(VersionReference.ofProperty("native-buildtools.version"))
                .scope(DependencyScope.TEST_RUNTIME));
    profile
        .plugins()
        .add(
            "org.graalvm.buildtools",
            "native-maven-plugin",
            (plugin) -> {
              plugin.version("${native-buildtools.version}");
              plugin.execution("test-native", (execution) -> execution.goal("test").phase("test"));
              plugin.execution(
                  "build-native", (execution) -> execution.goal("build").phase("package"));
            });
  }
}
