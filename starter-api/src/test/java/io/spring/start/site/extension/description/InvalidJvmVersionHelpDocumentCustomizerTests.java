/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.description;

import io.spring.initializr.generator.test.io.TextAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link InvalidJvmVersionHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class InvalidJvmVersionHelpDocumentCustomizerTests extends AbstractExtensionTests {

  @Test
  void warningAddedWithUnsupportedCombination() {
    assertHelpDocument("2.4.0", "16")
        .lines()
        .containsSubsequence(
            "# Read Me First",
            "* The JVM level was changed from '16' to '11', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.");
  }

  @Test
  void warningNotAddedWithCompatibleVersion() {
    assertHelpDocument("2.4.8", "11").doesNotContain("# Read Me First");
  }

  private TextAssert assertHelpDocument(String platformVersion, String jvmVersion) {
    ProjectRequest request = createProjectRequest("web");
    request.setType("gradle-project");
    request.setBootVersion(platformVersion);
    request.setJavaVersion(jvmVersion);
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md"));
  }
}
