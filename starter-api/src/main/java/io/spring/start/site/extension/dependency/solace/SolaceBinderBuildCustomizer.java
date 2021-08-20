/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.solace;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InvalidInitializrMetadataException;

/**
 * A {@link BuildCustomizer} that automatically adds {@code spring-cloud-starter-stream-solace} when
 * Solace and Spring Cloud Stream are both selected.
 *
 * @author Stephane Nicoll
 */
class SolaceBinderBuildCustomizer implements BuildCustomizer<Build> {

  private static final String BOM_ID = "solace-spring-cloud";

  private final BillOfMaterials bom;

  SolaceBinderBuildCustomizer(InitializrMetadata metadata, ProjectDescription description) {
    this.bom = resolveBom(metadata, description);
  }

  private static BillOfMaterials resolveBom(
      InitializrMetadata metadata, ProjectDescription description) {
    try {
      return metadata
          .getConfiguration()
          .getEnv()
          .getBoms()
          .get(BOM_ID)
          .resolve(description.getPlatformVersion());
    } catch (InvalidInitializrMetadataException ex) {
      return null;
    }
  }

  @Override
  public void customize(Build build) {
    if (this.bom != null) {
      build.properties().version(this.bom.getVersionProperty(), this.bom.getVersion());
      build.boms().add(BOM_ID);
      build
          .dependencies()
          .add(
              "solace-binder",
              Dependency.withCoordinates(
                  "com.solace.spring.cloud", "spring-cloud-starter-stream-solace"));
      // The low-level API is likely not going to be used in such arrangement
      build.dependencies().remove("solace");
    }
  }
}
