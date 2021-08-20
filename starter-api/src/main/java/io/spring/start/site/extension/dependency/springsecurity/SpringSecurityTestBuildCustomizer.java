/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springsecurity;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that automatically adds {@code spring-security-test} when Spring
 * Security is selected.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
public class SpringSecurityTestBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    build
        .dependencies()
        .add(
            "security-test",
            Dependency.withCoordinates("org.springframework.security", "spring-security-test")
                .scope(DependencyScope.TEST_COMPILE));
  }
}
