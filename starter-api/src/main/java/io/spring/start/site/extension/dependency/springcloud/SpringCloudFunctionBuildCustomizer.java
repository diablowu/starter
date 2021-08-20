/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.util.ObjectUtils;

/**
 * Determine the appropriate Spring Cloud function dependency according to the messaging and/or
 * platform dependencies requested.
 *
 * @author Dave Syer
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
class SpringCloudFunctionBuildCustomizer implements BuildCustomizer<Build> {

  private final InitializrMetadata metadata;

  private final ProjectDescription description;

  SpringCloudFunctionBuildCustomizer(InitializrMetadata metadata, ProjectDescription description) {
    this.metadata = metadata;
    this.description = description;
  }

  @Override
  public void customize(Build build) {
    DependencyContainer dependencies = build.dependencies();
    if (dependencies.has("cloud-function")) {
      if (dependencies.has("web")) {
        dependencies.add(
            "cloud-function-web",
            "org.springframework.cloud",
            "spring-cloud-function-web",
            DependencyScope.COMPILE);
        removeCloudFunction(build);
      }
      if (dependencies.has("webflux")) {
        dependencies.add(
            "cloud-function-web",
            "org.springframework.cloud",
            "spring-cloud-function-web",
            DependencyScope.COMPILE);
        removeCloudFunction(build);
      }
    }
  }

  /*
   * Remove the Spring Cloud Function artifact, making sure that any metadata
   * information is kept.
   */
  private void removeCloudFunction(Build build) {
    Dependency cloudFunction = this.metadata.getDependencies().get("cloud-function");
    // We should make sure whatever metadata this entry convey isn't lost
    // This is a workaround until we provide a feature to deal with this automatically
    if (cloudFunction.getBom() != null) {
      BillOfMaterials bom = resolveBom(cloudFunction.getBom());
      if (bom != null) {
        build.boms().add(cloudFunction.getBom());
        if (bom.getVersionProperty() != null) {
          build.properties().version(bom.getVersionProperty(), bom.getVersion());
        }
        if (!ObjectUtils.isEmpty(bom.getRepositories())) {
          bom.getRepositories().forEach((repository) -> build.repositories().add(repository));
        }
      }
    }
    if (cloudFunction.getRepository() != null) {
      build.repositories().add(cloudFunction.getRepository());
    }
    build.dependencies().remove("cloud-function");
  }

  private BillOfMaterials resolveBom(String id) {
    BillOfMaterials bom = this.metadata.getConfiguration().getEnv().getBoms().get(id);
    if (bom != null) {
      return bom.resolve(this.description.getPlatformVersion());
    }
    return null;
  }
}
