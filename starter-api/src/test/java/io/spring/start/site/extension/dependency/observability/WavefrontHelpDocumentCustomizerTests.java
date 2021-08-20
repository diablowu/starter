/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.observability;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WavefrontHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class WavefrontHelpDocumentCustomizerTests extends AbstractExtensionTests {

  @Test
  void wavefrontAddGeneralSection() {
    assertHelpDocument("2.4.8", "wavefront")
        .contains(
            "## Observability with Wavefront",
            "",
            "If you don't have a Wavefront account, the starter will create a freemium account for you.",
            "The URL to access the Wavefront Service dashboard is logged on startup.");
  }

  @Test
  void wavefrontWithoutWebApplicationDoesNotAddActuatorSection() {
    assertHelpDocument("2.4.8", "wavefront")
        .doesNotContain(
            "You can also access your dashboard using the `/actuator/wavefront` endpoint.");
  }

  @Test
  void wavefrontWithWebApplicationAddActuatorSection() {
    assertHelpDocument("2.4.8", "wavefront", "web")
        .contains("You can also access your dashboard using the `/actuator/wavefront` endpoint.");
  }

  @Test
  void wavefrontWithoutSleuthAddTracingNote() {
    assertHelpDocument("2.4.8", "wavefront")
        .contains(
            "Finally, you can opt-in for distributed tracing by adding the Spring Cloud Sleuth starter.");
  }

  @Test
  void wavefrontWithSleuthDoesNotAddTracingNote() {
    assertHelpDocument("2.4.8", "wavefront", "cloud-starter-sleuth")
        .doesNotContain(
            "Finally, you can opt-in for distributed tracing by adding the Spring Cloud Sleuth starter.");
  }

  private ListAssert<String> assertHelpDocument(String version, String... dependencies) {
    ProjectRequest request = createProjectRequest(dependencies);
    request.setBootVersion(version);
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md")).lines();
  }
}
