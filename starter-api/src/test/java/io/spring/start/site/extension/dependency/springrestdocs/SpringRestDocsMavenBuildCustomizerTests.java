/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.Configuration;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.Execution;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.Setting;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringRestDocsMavenBuildCustomizer}.
 *
 * @author Andy Wilkinson
 */
class SpringRestDocsMavenBuildCustomizerTests {

  private final SpringRestDocsMavenBuildCustomizer customizer =
      new SpringRestDocsMavenBuildCustomizer();

  @Test
  void customizesGradleBuild() {
    MavenBuild build = new MavenBuild();
    this.customizer.customize(build);
    assertThat(build.plugins().values())
        .singleElement()
        .satisfies(
            (plugin) -> {
              assertThat(plugin.getGroupId()).isEqualTo("org.asciidoctor");
              assertThat(plugin.getArtifactId()).isEqualTo("asciidoctor-maven-plugin");
              assertThat(plugin.getVersion()).isEqualTo("1.5.8");
              assertThat(plugin.getExecutions()).hasSize(1);
              Execution execution = plugin.getExecutions().get(0);
              assertThat(execution.getId()).isEqualTo("generate-docs");
              assertThat(execution.getGoals()).containsExactly("process-asciidoc");
              assertThat(execution.getPhase()).isEqualTo("prepare-package");
              assertThat(execution.getConfiguration()).isNotNull();
              Configuration configuration = execution.getConfiguration();
              assertThat(configuration.getSettings()).hasSize(2);
              Setting backend = configuration.getSettings().get(0);
              assertThat(backend.getName()).isEqualTo("backend");
              assertThat(backend.getValue()).isEqualTo("html");
              Setting doctype = configuration.getSettings().get(1);
              assertThat(doctype.getName()).isEqualTo("doctype");
              assertThat(doctype.getValue()).isEqualTo("book");
              assertThat(plugin.getDependencies()).hasSize(1);
              Dependency dependency = plugin.getDependencies().get(0);
              assertThat(dependency.getGroupId()).isEqualTo("org.springframework.restdocs");
              assertThat(dependency.getArtifactId()).isEqualTo("spring-restdocs-asciidoctor");
              assertThat(dependency.getVersion()).isEqualTo("${spring-restdocs.version}");
            });
  }
}
