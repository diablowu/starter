/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.gradle;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.gradle.DependencyManagementPluginVersionResolver;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.util.function.Function;

/**
 * {@link DependencyManagementPluginVersionResolver} that determines the dependency management
 * plugin version using the dependency management from the project description's Boot version.
 *
 * @author Stephane Nicoll
 */
public class ManagedDependenciesDependencyManagementPluginVersionResolver
    implements DependencyManagementPluginVersionResolver {

  private final DependencyManagementVersionResolver resolver;

  private final Function<ProjectDescription, String> fallback;

  public ManagedDependenciesDependencyManagementPluginVersionResolver(
      DependencyManagementVersionResolver resolver, Function<ProjectDescription, String> fallback) {
    this.resolver = resolver;
    this.fallback = fallback;
  }

  @Override
  public String resolveDependencyManagementPluginVersion(ProjectDescription description) {
    String pluginVersion =
        this.resolver
            .resolve(
                "org.springframework.boot",
                "spring-boot-dependencies",
                description.getPlatformVersion().toString())
            .get("io.spring.gradle:dependency-management-plugin");
    return (pluginVersion != null) ? pluginVersion : this.fallback.apply(description);
  }
}
