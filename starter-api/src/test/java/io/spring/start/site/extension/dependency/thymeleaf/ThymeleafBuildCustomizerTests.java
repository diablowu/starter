/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.thymeleaf;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.Dependency;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ThymeleafBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class ThymeleafBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void thymeleafWithSpringSecurityAddsExtrasDependency() {
    assertThat(mavenPom(createProjectRequest("thymeleaf", "security")))
        .hasDependency(Dependency.createSpringBootStarter("thymeleaf"))
        .hasDependency(Dependency.createSpringBootStarter("security"))
        .hasDependency(
            Dependency.withId(
                "thymeleaf-extras-spring-security",
                "org.thymeleaf.extras",
                "thymeleaf-extras-springsecurity5"));
  }

  @Test
  void thymeleafWithoutSpringSecurityDoesNotAddExtrasDependency() {
    assertThat(mavenPom(createProjectRequest("thymeleaf", "web")))
        .hasDependency(Dependency.createSpringBootStarter("thymeleaf"))
        .doesNotHaveDependency("org.thymeleaf.extras", "thymeleaf-extras-springsecurity5");
  }
}
