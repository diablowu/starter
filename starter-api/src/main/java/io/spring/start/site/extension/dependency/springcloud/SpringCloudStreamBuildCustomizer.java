/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * Determine the appropriate Spring Cloud stream dependency to use based on the selected integration
 * technology.
 *
 * <p>Does not replace the integration technology jar by the relevant binder. If more than one tech
 * is selected, it is far more easier to remove the unnecessary binder jar than to figure out the
 * name of the tech jar to add to keep support for that technology.
 *
 * @author Stephane Nicoll
 * @author Madhura Bhave
 */
class SpringCloudStreamBuildCustomizer implements BuildCustomizer<Build> {

  @Override
  public void customize(Build build) {
    if (hasDependency("cloud-stream", build) || hasDependency("cloud-bus", build)) {
      if (hasDependency("amqp", build)) {
        build
            .dependencies()
            .add(
                "cloud-stream-binder-rabbit",
                "org.springframework.cloud",
                "spring-cloud-stream-binder-rabbit",
                DependencyScope.COMPILE);
      }
      if (hasDependency("kafka", build)) {
        build
            .dependencies()
            .add(
                "cloud-stream-binder-kafka",
                "org.springframework.cloud",
                "spring-cloud-stream-binder-kafka",
                DependencyScope.COMPILE);
      }
    }
    // Spring Cloud Stream specific
    if (hasDependency("cloud-stream", build)) {
      if (hasDependency("kafka-streams", build)) {
        build
            .dependencies()
            .add(
                "cloud-stream-binder-kafka-streams",
                "org.springframework.cloud",
                "spring-cloud-stream-binder-kafka-streams",
                DependencyScope.COMPILE);
      }
      // TODO: https://github.com/spring-io/initializr/issues/1159
      if (build instanceof MavenBuild) {
        build
            .dependencies()
            .add(
                "cloud-stream-test",
                Dependency.withCoordinates("org.springframework.cloud", "spring-cloud-stream")
                    .classifier("test-binder")
                    .type("test-jar")
                    .scope(DependencyScope.TEST_COMPILE));
      }
    }
  }

  protected boolean hasDependency(String id, Build build) {
    return build.dependencies().has(id);
  }
}
