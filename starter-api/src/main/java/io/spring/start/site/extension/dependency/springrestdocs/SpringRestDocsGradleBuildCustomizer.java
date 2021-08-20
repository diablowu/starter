/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springrestdocs;

import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * {@link BuildCustomizer Customizer} for a {@link GradleBuild} when the generated project depends
 * on Spring REST Docs.
 *
 * @author Andy Wilkinson
 */
class SpringRestDocsGradleBuildCustomizer implements BuildCustomizer<GradleBuild> {

  @Override
  public void customize(GradleBuild build) {
    build.plugins().add("org.asciidoctor.convert", (plugin) -> plugin.setVersion("1.5.8"));
    build.properties().property("snippetsDir", "file(\"build/generated-snippets\")");
    build.tasks().customize("test", (task) -> task.invoke("outputs.dir", "snippetsDir"));
    build
        .tasks()
        .customize(
            "asciidoctor",
            (task) -> {
              task.invoke("inputs.dir", "snippetsDir");
              task.invoke("dependsOn", "test");
            });
  }
}
