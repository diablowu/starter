/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask;
import io.spring.initializr.generator.buildsystem.gradle.GradleTask.Invocation;
import io.spring.initializr.generator.buildsystem.gradle.StandardGradlePlugin;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringRestDocsGradleBuildCustomizer}.
 *
 * @author Andy Wilkinson
 */
class SpringRestDocsGradleBuildCustomizerTests {

  private final SpringRestDocsGradleBuildCustomizer customizer =
      new SpringRestDocsGradleBuildCustomizer();

  @Test
  void customizesGradleBuild() {
    GradleBuild build = new GradleBuild();
    this.customizer.customize(build);
    assertThat(build.plugins().values())
        .singleElement()
        .satisfies(
            (plugin) -> {
              assertThat(plugin.getId()).isEqualTo("org.asciidoctor.convert");
              assertThat(((StandardGradlePlugin) plugin).getVersion()).isEqualTo("1.5.8");
            });
    assertThat(build.properties().values())
        .contains(entry("snippetsDir", "file(\"build/generated-snippets\")"));
    GradleTask testTask = build.tasks().get("test");
    assertThat(testTask).isNotNull();
    assertThat(testTask.getInvocations()).hasSize(1);
    Invocation invocation = testTask.getInvocations().get(0);
    assertThat(invocation.getTarget()).isEqualTo("outputs.dir");
    assertThat(invocation.getArguments()).containsExactly("snippetsDir");
    GradleTask asciidoctorTask = build.tasks().get("asciidoctor");
    assertThat(asciidoctorTask).isNotNull();
    assertThat(asciidoctorTask.getInvocations()).hasSize(2);
    Invocation inputsDir = asciidoctorTask.getInvocations().get(0);
    assertThat(inputsDir.getTarget()).isEqualTo("inputs.dir");
    assertThat(inputsDir.getArguments()).containsExactly("snippetsDir");
    Invocation dependsOn = asciidoctorTask.getInvocations().get(1);
    assertThat(dependsOn.getTarget()).isEqualTo("dependsOn");
    assertThat(dependsOn.getArguments()).containsExactly("test");
  }
}
