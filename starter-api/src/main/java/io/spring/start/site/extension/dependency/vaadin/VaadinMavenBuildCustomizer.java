/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.vaadin;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * A {@link BuildCustomizer} that adds a production profile to enable Vaadin's production mode.
 *
 * @author Stephane Nicoll
 */
class VaadinMavenBuildCustomizer implements BuildCustomizer<MavenBuild> {

  @Override
  public void customize(MavenBuild build) {
    build
        .profiles()
        .id("production")
        .plugins()
        .add(
            "com.vaadin",
            "vaadin-maven-plugin",
            (plugin) ->
                plugin
                    .version("${vaadin.version}")
                    .execution(
                        "frontend",
                        (execution) ->
                            execution
                                .goal("prepare-frontend")
                                .goal("build-frontend")
                                .phase("compile")
                                .configuration(
                                    (configuration) ->
                                        configuration.add("productionMode", "true"))));
  }
}
