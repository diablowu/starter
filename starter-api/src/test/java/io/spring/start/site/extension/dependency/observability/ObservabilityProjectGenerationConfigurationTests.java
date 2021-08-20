/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

/**
 * Tests for {@link ObservabilityProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class ObservabilityProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void testClassWithWavefrontDisablesMetricsExport() {
    ProjectRequest request = createProjectRequest("wavefront");
    ProjectStructure project = generateProject(request);
    assertThat(project)
        .asJvmModule(new JavaLanguage())
        .testSource("com.example.demo", "DemoApplicationTests")
        .contains("import " + TestPropertySource.class.getName())
        .contains(
            "@TestPropertySource(properties = \"management.metrics.export.wavefront.enabled=false\")");
  }

  @Test
  void testClassWithoutWavefrontDoesNotDisableMetricsExport() {
    ProjectRequest request = createProjectRequest("datadog");
    ProjectStructure project = generateProject(request);
    assertThat(project)
        .asJvmModule(new JavaLanguage())
        .testSource("com.example.demo", "DemoApplicationTests")
        .doesNotContain("import " + TestPropertySource.class.getName())
        .doesNotContain(
            "@TestPropertySource(properties = \"management.metrics.export.wavefront.enabled=false\")");
  }
}
