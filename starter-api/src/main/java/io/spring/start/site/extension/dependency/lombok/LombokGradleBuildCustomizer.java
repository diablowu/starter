/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.lombok;

import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;

/**
 * Complete the setup for Lombok with Gradle by adding Lombok with {@code compileOnly} scope as
 * well.
 *
 * @author Stephane Nicoll
 */
public class LombokGradleBuildCustomizer implements BuildCustomizer<GradleBuild> {

  private final InitializrMetadata metadata;

  public LombokGradleBuildCustomizer(InitializrMetadata metadata) {
    this.metadata = metadata;
  }

  @Override
  public void customize(GradleBuild build) {
    Dependency lombok = this.metadata.getDependencies().get("lombok");
    build
        .dependencies()
        .add(
            "lombok-compileOnly",
            lombok.getGroupId(),
            lombok.getArtifactId(),
            DependencyScope.COMPILE_ONLY);
  }
}
