/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.gradle.GradleBuildSystem;
import io.spring.initializr.generator.language.kotlin.KotlinLanguage;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionCustomizer;

/**
 * A {@link ProjectDescriptionCustomizer} that enables the Kotlin DSL for a Gradle project using
 * Kotlin.
 *
 * @author Stephane Nicoll
 */
public class GradleDslProjectDescriptionCustomizer implements ProjectDescriptionCustomizer {

  @Override
  public void customize(MutableProjectDescription description) {
    if (description.getLanguage() instanceof KotlinLanguage
        && description.getBuildSystem() instanceof GradleBuildSystem) {
      description.setBuildSystem(
          BuildSystem.forIdAndDialect(GradleBuildSystem.ID, GradleBuildSystem.DIALECT_KOTLIN));
    }
  }
}
