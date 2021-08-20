/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.okta;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link OktaHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class OktaHelpDocumentCustomizerTests extends AbstractExtensionTests {

  @Test
  void oktaSectionWithOktaDependencyIsPresent() {
    assertHelpDocument("okta").contains("## OAuth 2.0 and OIDC with Okta");
  }

  @Test
  void oktaSectionWithoutOktaDependencyIsMissing() {
    assertHelpDocument("web", "actuator").doesNotContain("## OAuth 2.0 and OIDC with Okta");
  }

  private TextAssert assertHelpDocument(String... dependencies) {
    ProjectRequest request = createProjectRequest(dependencies);
    request.setBootVersion("2.4.6");
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md"));
  }
}
