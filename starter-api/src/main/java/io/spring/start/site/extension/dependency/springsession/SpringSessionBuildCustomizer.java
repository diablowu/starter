/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springsession;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * {@link BuildCustomizer} for Spring Session that provides explicit handling for the modules
 * introduced in Spring Session 2.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
public class SpringSessionBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    DependencyContainer dependencies = build.dependencies();
    if (dependencies.has("data-redis") || dependencies.has("data-redis-reactive")) {
      dependencies.add(
          "session-data-redis",
          "org.springframework.session",
          "spring-session-data-redis",
          DependencyScope.COMPILE);
      dependencies.remove("session");
    }
    if (dependencies.has("jdbc")) {
      dependencies.add(
          "session-jdbc",
          "org.springframework.session",
          "spring-session-jdbc",
          DependencyScope.COMPILE);
      dependencies.remove("session");
    }
  }
}
