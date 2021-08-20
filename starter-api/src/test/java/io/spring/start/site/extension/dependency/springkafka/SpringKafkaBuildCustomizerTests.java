/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springkafka;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringKafkaBuildCustomizer}.
 *
 * @author Wonwoo Lee
 * @author Stephane Nicoll
 */
class SpringKafkaBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void springKafkaTestIsAdded() {
    ProjectRequest request = createProjectRequest("kafka");
    Dependency kafkaTest =
        Dependency.withId(
            "spring-kafka-test",
            "org.springframework.kafka",
            "spring-kafka-test",
            null,
            Dependency.SCOPE_TEST);
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(kafkaTest)
        .hasDependenciesSize(4);
  }

  @Test
  void springKafkaTestIsNotAddedWithoutKafka() {
    ProjectRequest request = createProjectRequest("web");
    assertThat(mavenPom(request))
        .hasDependency(Dependency.createSpringBootStarter("web"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependenciesSize(2);
  }
}
