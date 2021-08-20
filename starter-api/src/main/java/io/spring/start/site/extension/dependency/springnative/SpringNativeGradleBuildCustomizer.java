/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import org.springframework.core.Ordered;

/**
 * {@link BuildCustomizer} abstraction for Spring Native projects using Gradle.
 *
 * @author Stephane Nicoll
 */
abstract class SpringNativeGradleBuildCustomizer implements BuildCustomizer<GradleBuild>, Ordered {

  @Override
  public void customize(GradleBuild build) {
    String springNativeVersion = build.dependencies().get("native").getVersion().getValue();

    // AOT plugin
    build
        .plugins()
        .add(
            "org.springframework.experimental.aot",
            (plugin) -> plugin.setVersion(springNativeVersion));

    // Native buildtools plugin
    String nativeBuildtoolsVersion =
        SpringNativeBuildtoolsVersionResolver.resolve(springNativeVersion);
    if (nativeBuildtoolsVersion != null) {
      // Gradle plugin is not yet available on the Gradle portal
      build.pluginRepositories().add("maven-central");

      build
          .plugins()
          .add(
              "org.graalvm.buildtools.native",
              (plugin) -> plugin.setVersion(nativeBuildtoolsVersion));
    }

    // The AOT plugin includes the native dependency automatically
    build.dependencies().remove("native");

    // Spring Boot plugin
    customizeSpringBootPlugin(build);

    // Native build
    if (nativeBuildtoolsVersion != null) {
      build
          .tasks()
          .customize(
              "nativeBuild",
              (task) ->
                  task.invoke(
                      "classpath", "\"$buildDir/resources/aot\", \"$buildDir/classes/java/aot\""));
    }
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 10;
  }

  protected abstract void customizeSpringBootPlugin(GradleBuild build);
}
