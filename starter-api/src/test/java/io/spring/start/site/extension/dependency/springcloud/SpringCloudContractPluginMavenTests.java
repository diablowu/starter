/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * Tests for {@link SpringCloudContractMavenBuildCustomizer}.
 *
 * @author Madhura Bhave
 */
class SpringCloudContractPluginMavenTests
    extends AbstractSpringCloudContractPluginTests<MavenBuild> {

  @Override
  protected BuildCustomizer<MavenBuild> getCustomizer(
      MutableProjectDescription description, SpringCloudProjectVersionResolver resolver) {
    return new SpringCloudContractMavenBuildCustomizer(description, resolver);
  }

  @Override
  protected MavenBuild getBuild() {
    return new MavenBuild();
  }
}
