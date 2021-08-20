/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.kotlin;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.code.kotlin.KotlinVersionResolver;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.util.function.Function;

/**
 * {@link KotlinVersionResolver} that determines the Kotlin version using the dependency management
 * from the project description's platform version.
 *
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 */
public class ManagedDependenciesKotlinVersionResolver implements KotlinVersionResolver {

  private final DependencyManagementVersionResolver resolver;

  private final Function<ProjectDescription, String> fallback;

  public ManagedDependenciesKotlinVersionResolver(
      DependencyManagementVersionResolver resolver, Function<ProjectDescription, String> fallback) {
    this.resolver = resolver;
    this.fallback = fallback;
  }

  @Override
  public String resolveKotlinVersion(ProjectDescription description) {
    String kotlinVersion =
        this.resolver
            .resolve(
                "org.springframework.boot",
                "spring-boot-dependencies",
                description.getPlatformVersion().toString())
            .get("org.jetbrains.kotlin:kotlin-reflect");
    return (kotlinVersion != null) ? kotlinVersion : this.fallback.apply(description);
  }
}
