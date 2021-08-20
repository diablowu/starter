/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springamqp;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that automatically adds {@code spring-rabbit-test} when Spring for
 * RabbitMQ is selected.
 *
 * @author Stephane Nicoll
 */
class SpringRabbitTestBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    build
        .dependencies()
        .add(
            "spring-rabbit-test",
            Dependency.withCoordinates("org.springframework.amqp", "spring-rabbit-test")
                .scope(DependencyScope.TEST_COMPILE));
  }
}
