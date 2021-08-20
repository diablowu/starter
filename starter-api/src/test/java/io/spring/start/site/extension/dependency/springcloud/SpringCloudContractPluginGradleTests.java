/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * Tests for {@link SpringCloudContractGradleBuildCustomizer}.
 *
 * @author Madhura Bhave
 */
class SpringCloudContractPluginGradleTests
    extends AbstractSpringCloudContractPluginTests<GradleBuild> {

  @Override
  protected BuildCustomizer<GradleBuild> getCustomizer(
      MutableProjectDescription description, SpringCloudProjectVersionResolver resolver) {
    return new SpringCloudContractGradleBuildCustomizer(description, resolver);
  }

  @Override
  protected GradleBuild getBuild() {
    return new GradleBuild();
  }
}
