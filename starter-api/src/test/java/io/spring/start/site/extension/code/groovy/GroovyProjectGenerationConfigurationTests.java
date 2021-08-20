/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.groovy;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GroovyProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class GroovyProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void groovyProjectWithJava8() {
    ProjectRequest request = groovyProjectRequest();
    request.setJavaVersion("1.8");
    assertThat(mavenPom(request)).doesNotHaveProperty("groovy.version");
  }

  @Test
  void groovyProjectWithJava14() {
    ProjectRequest request = groovyProjectRequest();
    request.setJavaVersion("14");
    assertThat(mavenPom(request)).hasProperty("groovy.version", "3.0.6");
  }

  @Test
  void groovyProjectWithJava15() {
    ProjectRequest request = groovyProjectRequest();
    request.setJavaVersion("15");
    assertThat(mavenPom(request)).hasProperty("groovy.version", "3.0.6");
  }

  private ProjectRequest groovyProjectRequest() {
    ProjectRequest request = createProjectRequest();
    request.setLanguage("groovy");
    request.setBootVersion("2.4.8");
    return request;
  }
}
