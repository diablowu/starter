/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springkafka;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that automatically adds "spring-kafka-test" when kafka is selected.
 *
 * @author Wonwoo Lee
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
public class SpringKafkaBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    if (build.dependencies().has("kafka")) {
      build
          .dependencies()
          .add(
              "spring-kafka-test",
              Dependency.withCoordinates("org.springframework.kafka", "spring-kafka-test")
                  .scope(DependencyScope.TEST_COMPILE));
    }
  }
}
