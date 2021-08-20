/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springamqp;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringRabbitTestBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringRabbitTestBuildCustomizerTests {

  @Test
  void customizeAddsSpringRabbitTest() {
    Build build = new MavenBuild();
    new SpringRabbitTestBuildCustomizer().customize(build);
    assertThat(build.dependencies().ids()).containsOnly("spring-rabbit-test");
    Dependency springRabbitTest = build.dependencies().get("spring-rabbit-test");
    assertThat(springRabbitTest.getGroupId()).isEqualTo("org.springframework.amqp");
    assertThat(springRabbitTest.getArtifactId()).isEqualTo("spring-rabbit-test");
    assertThat(springRabbitTest.getScope()).isEqualTo(DependencyScope.TEST_COMPILE);
  }
}
