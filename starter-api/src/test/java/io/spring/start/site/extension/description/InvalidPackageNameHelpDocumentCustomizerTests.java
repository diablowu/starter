/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
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
 * Tests for {@link InvalidPackageNameHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class InvalidPackageNameHelpDocumentCustomizerTests extends AbstractExtensionTests {

  @Test
  void warningAddedWithInvalidPackageName() {
    assertHelpDocument("com.my-invalid-package")
        .lines()
        .containsSubsequence(
            "# Read Me First",
            "* The original package name 'com.my-invalid-package' is invalid and this project uses 'com.myinvalidpackage' instead.");
  }

  @Test
  void warningNotAddedWithValidPackageName() {
    assertHelpDocument("com.example.valid").doesNotContain("# Read Me First");
  }

  private TextAssert assertHelpDocument(String packageName) {
    ProjectRequest request = createProjectRequest("web");
    request.setPackageName(packageName);
    ProjectStructure project = generateProject(request);
    return new TextAssert(project.getProjectDirectory().resolve("HELP.md"));
  }
}
