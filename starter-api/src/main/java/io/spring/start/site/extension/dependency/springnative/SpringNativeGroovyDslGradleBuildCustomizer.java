/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;
import java.util.function.Supplier;

/**
 * A {@link BuildCustomizer} that configures Spring Native for Gradle using the Groovy DSL.
 *
 * @author Stephane Nicoll
 */
class SpringNativeGroovyDslGradleBuildCustomizer extends SpringNativeGradleBuildCustomizer {

  private final Supplier<VersionReference> hibernateVersion;

  SpringNativeGroovyDslGradleBuildCustomizer(Supplier<VersionReference> hibernateVersion) {
    this.hibernateVersion = hibernateVersion;
  }

  @Override
  public void customize(GradleBuild build) {
    super.customize(build);

    // Hibernate enhance plugin
    if (build.dependencies().has("data-jpa")) {
      configureHibernateEnhancePlugin(build);
    }
  }

  @Override
  protected void customizeSpringBootPlugin(GradleBuild build) {
    build
        .tasks()
        .customize(
            "bootBuildImage",
            (task) -> {
              task.attribute("builder", "'paketobuildpacks/builder:tiny'");
              task.attribute("environment", "['BP_NATIVE_IMAGE': 'true']");
            });
  }

  private void configureHibernateEnhancePlugin(GradleBuild build) {
    build
        .settings()
        .mapPlugin(
            "org.hibernate.orm",
            Dependency.withCoordinates("org.hibernate", "hibernate-gradle-plugin")
                .version(this.hibernateVersion.get())
                .build());
    build.plugins().add("org.hibernate.orm");
    build
        .tasks()
        .customize(
            "hibernate",
            (task) ->
                task.nested(
                    "enhance",
                    (enhance) -> {
                      enhance.attribute("enableLazyInitialization", "true");
                      enhance.attribute("enableDirtyTracking", "true");
                      enhance.attribute("enableAssociationManagement", "true");
                      enhance.attribute("enableExtendedEnhancement", "false");
                    }));
  }
}
