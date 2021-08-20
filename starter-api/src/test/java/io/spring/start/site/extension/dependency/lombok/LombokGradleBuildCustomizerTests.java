/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.lombok;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link LombokGradleBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class LombokGradleBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void lombokConfiguredWithCompileOnlyScope() {
    ProjectRequest request = createProjectRequest("lombok");
    assertThat(gradleBuild(request))
        .contains("annotationProcessor 'org.projectlombok:lombok'")
        .contains("compileOnly 'org.projectlombok:lombok'");
  }

  @Test
  void lombokNotAddedIfLombokIsNotSelected() {
    ProjectRequest request = createProjectRequest("web");
    assertThat(gradleBuild(request)).doesNotContain("compileOnly 'org.projectlombok:lombok'");
  }
}
