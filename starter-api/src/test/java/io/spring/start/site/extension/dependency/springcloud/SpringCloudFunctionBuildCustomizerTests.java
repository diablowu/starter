/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudFunctionBuildCustomizer}.
 *
 * @author Dave Syer
 * @author Stephane Nicoll
 */
class SpringCloudFunctionBuildCustomizerTests extends AbstractExtensionTests {

  static final Dependency WEB_ADAPTER =
      Dependency.withId(
          "cloud-function-web", "org.springframework.cloud", "spring-cloud-function-web");

  @Test
  void functionOnly() {
    ProjectRequest request = createProjectRequest("cloud-function");
    assertThat(mavenPom(request))
        .hasDependency(getDependency("cloud-function"))
        .hasDependency(Dependency.createSpringBootStarter("test", Dependency.SCOPE_TEST))
        .hasDependency(Dependency.createSpringBootStarter(""))
        .hasDependenciesSize(3)
        .hasBom("org.springframework.cloud", "spring-cloud-dependencies", "${spring-cloud.version}")
        .hasBomsSize(1);
  }

  @Test
  void web() {
    ProjectRequest request = createProjectRequest("web", "cloud-function");
    BillOfMaterials bom = getBom("spring-cloud", request.getBootVersion());
    assertThat(mavenPom(request))
        .hasDependency(getDependency("web"))
        .hasDependency(WEB_ADAPTER)
        .hasDependenciesSize(3)
        .hasBom("org.springframework.cloud", "spring-cloud-dependencies", "${spring-cloud.version}")
        .hasBomsSize(1)
        .hasProperty("spring-cloud.version", bom.getVersion());
  }

  @Test
  void webflux() {
    ProjectRequest request = createProjectRequest("webflux", "cloud-function");
    BillOfMaterials bom = getBom("spring-cloud", request.getBootVersion());
    assertThat(mavenPom(request))
        .hasDependency(getDependency("webflux"))
        .hasDependency(WEB_ADAPTER)
        .hasDependenciesSize(4)
        .hasBom("org.springframework.cloud", "spring-cloud-dependencies", "${spring-cloud.version}")
        .hasBomsSize(1)
        .hasProperty("spring-cloud.version", bom.getVersion());
  }
}
