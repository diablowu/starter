/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that registers the necessary {@link MavenRepository} for Spring Native.
 * As the Gradle plugin automatically adds the Spring Native dependency, we need to include the
 * repository manually.
 *
 * @author Stephane Nicoll
 */
class SpringNativeRepositoryBuildCustomizer implements BuildCustomizer<Build> {

  private final MavenRepository mavenRepository;

  SpringNativeRepositoryBuildCustomizer(MavenRepository mavenRepository) {
    this.mavenRepository = mavenRepository;
  }

  @Override
  public void customize(Build build) {
    if (this.mavenRepository != null) {
      build.repositories().add(this.mavenRepository);
      build.pluginRepositories().add(this.mavenRepository);
    }
  }
}
