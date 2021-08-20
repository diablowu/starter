/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.thymeleaf;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} for Thymeleaf.
 *
 * @author Stephane Nicoll
 */
public class ThymeleafBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    if (build.dependencies().has("security")) {
      build
          .dependencies()
          .add(
              "thymeleaf-extras-spring-security",
              Dependency.withCoordinates(
                  "org.thymeleaf.extras", "thymeleaf-extras-springsecurity5"));
    }
  }
}
