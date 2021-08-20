/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springsecurity;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that provides Spring Security's RSocket support when both RSocket and
 * Spring Security are selected.
 *
 * @author Stephane Nicoll
 */
public class SpringSecurityRSocketBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    if (build.dependencies().has("rsocket")) {
      build
          .dependencies()
          .add(
              "security-rsocket",
              Dependency.withCoordinates(
                  "org.springframework.security", "spring-security-rsocket"));
      build
          .dependencies()
          .add(
              "security-messaging",
              Dependency.withCoordinates(
                  "org.springframework.security", "spring-security-messaging"));
    }
  }
}
