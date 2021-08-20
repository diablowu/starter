/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.kotlin;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildMetadataResolver;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.util.Map;

/**
 * A project customizer for Kotlin Coroutines.
 *
 * @author Stephane Nicoll
 */
public class KotlinCoroutinesCustomizer {

  private final BuildMetadataResolver buildResolver;

  private final ProjectDescription description;

  private final DependencyManagementVersionResolver versionResolver;

  public KotlinCoroutinesCustomizer(
      InitializrMetadata metadata,
      ProjectDescription description,
      DependencyManagementVersionResolver versionResolver) {
    this.buildResolver = new BuildMetadataResolver(metadata);
    this.description = description;
    this.versionResolver = versionResolver;
  }

  public void customize(Build build) {
    if (hasReactiveFacet(build)) {
      build
          .dependencies()
          .add(
              "kotlinx-coroutines-reactor",
              Dependency.withCoordinates("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor"));
    }
  }

  public void customize(HelpDocument document, Build build) {
    if (hasReactiveFacet(build)) {
      Map<String, String> resolve =
          this.versionResolver.resolve(
              "org.springframework.boot",
              "spring-boot-dependencies",
              this.description.getPlatformVersion().toString());
      String frameworkVersion = resolve.get("org.springframework:spring-core");
      String versionToUse = (frameworkVersion != null) ? frameworkVersion : "current";
      String href =
          String.format(
              "https://docs.spring.io/spring/docs/%s/spring-framework-reference/languages.html#coroutines",
              versionToUse);
      document
          .gettingStarted()
          .addReferenceDocLink(href, "Coroutines section of the Spring Framework Documentation");
    }
  }

  private boolean hasReactiveFacet(Build build) {
    return this.buildResolver.hasFacet(build, "reactive");
  }
}
