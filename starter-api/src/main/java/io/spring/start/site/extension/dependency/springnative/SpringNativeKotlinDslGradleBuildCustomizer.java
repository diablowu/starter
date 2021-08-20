/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that configures Spring Native for Gradle using the Kotlin DSL.
 *
 * @author Stephane Nicoll
 */
class SpringNativeKotlinDslGradleBuildCustomizer extends SpringNativeGradleBuildCustomizer {

  private static final String BOOT_BUILD_IMAGE_TASK =
      "org.springframework.boot.gradle.tasks.bundling.BootBuildImage";

  @Override
  protected void customizeSpringBootPlugin(GradleBuild build) {
    build
        .tasks()
        .customizeWithType(
            BOOT_BUILD_IMAGE_TASK,
            (task) -> {
              task.attribute("builder", "\"paketobuildpacks/builder:tiny\"");
              task.attribute(
                  "environment",
                  String.format("mapOf(\"%s\" to \"%s\")", "BP_NATIVE_IMAGE", "true"));
            });
  }
}
