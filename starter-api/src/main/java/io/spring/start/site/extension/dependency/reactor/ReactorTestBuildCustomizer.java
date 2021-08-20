/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.reactor;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.build.BuildMetadataResolver;
import io.spring.initializr.metadata.InitializrMetadata;

/**
 * A {@link BuildCustomizer} that automatically adds "reactor-test" when a dependency with the
 * {@code reactive} facet is selected.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
public class ReactorTestBuildCustomizer implements BuildCustomizer<Build> {

  private final BuildMetadataResolver buildResolver;

  public ReactorTestBuildCustomizer(InitializrMetadata metadata) {
    this.buildResolver = new BuildMetadataResolver(metadata);
  }

  @Override
  public void customize(Build build) {
    if (this.buildResolver.hasFacet(build, "reactive")) {
      build
          .dependencies()
          .add(
              "reactor-test",
              Dependency.withCoordinates("io.projectreactor", "reactor-test")
                  .scope(DependencyScope.TEST_COMPILE));
    }
  }
}
