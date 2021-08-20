/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.kotlin;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.build.BuildMetadataResolver;
import io.spring.initializr.metadata.InitializrMetadata;

/**
 * A {@link BuildCustomizer} that automatically adds "reactor-kotlin-extensions" when a dependency
 * with the {@code reactive} facet is selected.
 *
 * @author Eddú Meléndez
 */
class ReactorKotlinExtensionsCustomizer implements BuildCustomizer<Build> {

  private final BuildMetadataResolver buildResolver;

  ReactorKotlinExtensionsCustomizer(InitializrMetadata metadata) {
    this.buildResolver = new BuildMetadataResolver(metadata);
  }

  @Override
  public void customize(Build build) {
    if (this.buildResolver.hasFacet(build, "reactive")) {
      build
          .dependencies()
          .add(
              "reactor-kotlin-extensions",
              Dependency.withCoordinates("io.projectreactor.kotlin", "reactor-kotlin-extensions"));
    }
  }
}
