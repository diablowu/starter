/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.spring.build.BuildMetadataResolver;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import org.springframework.util.ObjectUtils;

/**
 * Switches to reactive Spring Cloud circuit breaker resilience4j if reactive facet is present.
 *
 * @author Madhura Bhave
 */
public class SpringCloudCircuitBreakerBuildCustomizer implements BuildCustomizer<Build> {

  private final BuildMetadataResolver buildResolver;

  private final InitializrMetadata metadata;

  private final ProjectDescription description;

  public SpringCloudCircuitBreakerBuildCustomizer(
      InitializrMetadata metadata, ProjectDescription description) {
    this.buildResolver = new BuildMetadataResolver(metadata);
    this.metadata = metadata;
    this.description = description;
  }

  @Override
  public void customize(Build build) {
    DependencyContainer dependencies = build.dependencies();
    if (dependencies.has("cloud-resilience4j") && this.buildResolver.hasFacet(build, "reactive")) {
      build
          .dependencies()
          .add(
              "cloud-resilience4j-reactive",
              "org.springframework.cloud",
              "spring-cloud-starter-circuitbreaker-reactor-resilience4j",
              DependencyScope.COMPILE);
      removeBlockingCloudResilience4j(build);
    }
  }

  private void removeBlockingCloudResilience4j(Build build) {
    Dependency cloudResilience4j = this.metadata.getDependencies().get("cloud-resilience4j");
    if (cloudResilience4j.getBom() != null) {
      BillOfMaterials bom = resolveBom(cloudResilience4j.getBom());
      if (bom != null) {
        build.boms().add(cloudResilience4j.getBom());
        if (bom.getVersionProperty() != null) {
          build.properties().version(bom.getVersionProperty(), bom.getVersion());
        }
        if (!ObjectUtils.isEmpty(bom.getRepositories())) {
          bom.getRepositories().forEach((repository) -> build.repositories().add(repository));
        }
      }
    }
    if (cloudResilience4j.getRepository() != null) {
      build.repositories().add(cloudResilience4j.getRepository());
    }
    build.dependencies().remove("cloud-resilience4j");
  }

  private BillOfMaterials resolveBom(String id) {
    BillOfMaterials bom = this.metadata.getConfiguration().getEnv().getBoms().get(id);
    if (bom != null) {
      return bom.resolve(this.description.getPlatformVersion());
    }
    return null;
  }
}
