/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class GoogleJavaFormatterPluginBuildCustomizer implements BuildCustomizer<MavenBuild> {

  private static final String JAVA_SOURCE_HEADER =
      "/*\n"
          + " * Copyright (c) $today.year Taikang Pension. All rights reserved.\n"
          + " * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.\n"
          + " *\n"
          + " */";

  @Override
  public void customize(MavenBuild build) {

    build
        .plugins()
        .add(
            "com.diffplug.spotless",
            "spotless-maven-plugin",
            pluginBuilder -> {
              pluginBuilder
                  .version("2.12.2")
                  .execution(
                      "auto-apply",
                      executionBuilder -> {
                        executionBuilder.phase("validate").goal("apply");
                      })
                  .configuration(
                      configurationBuilder -> {
                        configurationBuilder.add(
                            "java",
                            s -> {
                              s.add(
                                  "includes",
                                  s1 -> {
                                    s1.add("include", "src/main/java/**/*.java");
                                    s1.add("include", "src/test/java/**/*.java");
                                  });
                              s.add(
                                  "importOrder",
                                  s1 -> {
                                    s1.add("order", "java,javax,,com.taikang");
                                  });

                              s.add("removeUnusedImports", "");

                              s.add(
                                  "googleJavaFormat",
                                  s1 -> {
                                    s1.add("version", "1.11.0");
                                    s1.add("style", "GOOGLE");
                                  });

                              s.add(
                                  "licenseHeader",
                                  s1 -> {
                                    s1.add("content", JAVA_SOURCE_HEADER);
                                  });
                            });
                      });
            });
  }
}
