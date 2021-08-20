/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springbatch;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that automatically adds {@code spring-batch-test} when Spring Batch is
 * selected.
 *
 * @author Tim Riemer
 * @author Madhura Bhave
 */
public class SpringBatchTestBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    build
        .dependencies()
        .add(
            "spring-batch-test",
            Dependency.withCoordinates("org.springframework.batch", "spring-batch-test")
                .scope(DependencyScope.TEST_COMPILE));
  }
}
