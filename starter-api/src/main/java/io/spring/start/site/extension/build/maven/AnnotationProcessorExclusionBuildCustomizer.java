/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionRange;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link BuildCustomizer} that automatically excludes annotation processors from the repackaged
 * archive if necessary.
 *
 * @author Stephane Nicoll
 */
class AnnotationProcessorExclusionBuildCustomizer implements BuildCustomizer<MavenBuild> {

  private static final VersionRange SPRING_BOOT_2_4_0_M3_0R_LATER =
      VersionParser.DEFAULT.parseRange("2.4.0-M3");

  private static final List<String> KNOWN_ANNOTATION_PROCESSORS =
      Collections.singletonList("configuration-processor");

  private final InitializrMetadata metadata;

  private final boolean hasSmartExclude;

  AnnotationProcessorExclusionBuildCustomizer(
      InitializrMetadata metadata, Version platformVersion) {
    this.metadata = metadata;
    this.hasSmartExclude = SPRING_BOOT_2_4_0_M3_0R_LATER.match(platformVersion);
  }

  @Override
  public void customize(MavenBuild build) {
    if (!build.plugins().has("org.springframework.boot", "spring-boot-maven-plugin")) {
      return;
    }
    List<io.spring.initializr.generator.buildsystem.Dependency> dependencies =
        build
            .dependencies()
            .ids()
            .filter(this::isAnnotationProcessor)
            .filter((id) -> !this.hasSmartExclude || !KNOWN_ANNOTATION_PROCESSORS.contains(id))
            .map((id) -> build.dependencies().get(id))
            .collect(Collectors.toList());
    if (!dependencies.isEmpty()) {
      build
          .plugins()
          .add(
              "org.springframework.boot",
              "spring-boot-maven-plugin",
              (plugin) ->
                  plugin.configuration(
                      (configuration) ->
                          configuration.configure(
                              "excludes",
                              (excludes) -> {
                                for (io.spring.initializr.generator.buildsystem.Dependency
                                    dependency : dependencies) {
                                  excludes.add(
                                      "exclude",
                                      (exclude) -> {
                                        exclude.add("groupId", dependency.getGroupId());
                                        exclude.add("artifactId", dependency.getArtifactId());
                                      });
                                }
                              })));
    }
  }

  @Override
  public int getOrder() {
    return 5;
  }

  private boolean isAnnotationProcessor(String id) {
    Dependency dependency = this.metadata.getDependencies().get(id);
    return (dependency != null)
        && Dependency.SCOPE_ANNOTATION_PROCESSOR.equals(dependency.getScope());
  }
}
