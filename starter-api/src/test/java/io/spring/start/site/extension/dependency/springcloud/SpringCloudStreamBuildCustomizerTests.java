/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudStreamBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringCloudStreamBuildCustomizerTests extends AbstractExtensionTests {

  private static final Dependency KAFKA_BINDER =
      Dependency.withId(
          "cloud-stream-binder-kafka",
          "org.springframework.cloud",
          "spring-cloud-stream-binder-kafka");

  private static final Dependency KAFKA_STREAMS_BINDER =
      Dependency.withId(
          "cloud-stream-binder-kafka-streams",
          "org.springframework.cloud",
          "spring-cloud-stream-binder-kafka-streams");

  private static final Dependency RABBIT_BINDER =
      Dependency.withId(
          "cloud-stream-binder-rabbit",
          "org.springframework.cloud",
          "spring-cloud-stream-binder-rabbit");

  private static final Dependency SCS_TEST = springCloudStreamTestDependency();

  @Test
  void springCloudStreamWithRabbit() {
    ProjectRequest request = createProjectRequest("cloud-stream", "amqp");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-stream"))
        .hasDependency(getDependency("amqp"))
        .hasDependency(RABBIT_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(SCS_TEST)
        .hasDependenciesSize(6);
  }

  @Test
  void springCloudStreamWithKafka() {
    ProjectRequest request = createProjectRequest("cloud-stream", "kafka");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-stream"))
        .hasDependency(getDependency("kafka"))
        .hasDependency(KAFKA_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(SCS_TEST)
        .hasDependenciesSize(6);
  }

  @Test
  void springCloudStreamWithKafkaStreams() {
    ProjectRequest request = createProjectRequest("cloud-stream", "kafka-streams");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-stream"))
        .hasDependency(getDependency("kafka-streams"))
        .hasDependency(KAFKA_STREAMS_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(SCS_TEST)
        .hasDependenciesSize(5);
  }

  @Test
  void springCloudStreamWithAllBinders() {
    ProjectRequest request = createProjectRequest("cloud-stream", "amqp", "kafka", "kafka-streams");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-stream"))
        .hasDependency(getDependency("amqp"))
        .hasDependency(getDependency("kafka"))
        .hasDependency(getDependency("kafka-streams"))
        .hasDependency(RABBIT_BINDER)
        .hasDependency(KAFKA_BINDER)
        .hasDependency(KAFKA_STREAMS_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(SCS_TEST)
        .hasDependenciesSize(11);
  }

  @Test
  void springCloudBusWithRabbit() {
    ProjectRequest request = createProjectRequest("cloud-bus", "amqp");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-bus"))
        .hasDependency(getDependency("amqp"))
        .hasDependency(RABBIT_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(5);
  }

  @Test
  void springCloudBusWithKafka() {
    ProjectRequest request = createProjectRequest("cloud-bus", "amqp");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-bus"))
        .hasDependency(getDependency("amqp"))
        .hasDependency(RABBIT_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(5);
  }

  @Test
  void springCloudBusWithAllBinders() {
    ProjectRequest request = createProjectRequest("cloud-bus", "amqp", "kafka", "kafka-streams");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-bus"))
        .hasDependency(getDependency("amqp"))
        .hasDependency(getDependency("kafka"))
        .hasDependency(getDependency("kafka-streams"))
        .hasDependency(RABBIT_BINDER)
        .hasDependency(KAFKA_BINDER)
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(9);
  }

  @Test
  void springCloudStreamWithGradleBuildDoesNotAddTestDependency() {
    ProjectRequest request = createProjectRequest("cloud-stream", "amqp");
    assertThat(gradleBuild(request)).doesNotContain("test-binder");
  }

  private static Dependency springCloudStreamTestDependency() {
    Dependency scsTest =
        Dependency.withId(
            "cloud-stream-test",
            "org.springframework.cloud",
            "spring-cloud-stream",
            null,
            Dependency.SCOPE_TEST);
    scsTest.setClassifier("test-binder");
    scsTest.setType("test-jar");
    return scsTest;
  }
}
