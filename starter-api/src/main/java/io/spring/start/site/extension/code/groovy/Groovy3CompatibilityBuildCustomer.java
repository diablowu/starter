/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.groovy;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

/**
 * Groovy 3 {@link BuildCustomizer} to support more recent JVM versions.
 *
 * @author Stephane Nicoll
 */
public class Groovy3CompatibilityBuildCustomer implements BuildCustomizer<Build> {

  private final String jvmVersion;

  public Groovy3CompatibilityBuildCustomer(String jvmVersion) {
    this.jvmVersion = jvmVersion;
  }

  @Override
  public void customize(Build build) {
    Integer javaGeneration = determineJavaGeneration(this.jvmVersion);
    if (javaGeneration != null && javaGeneration >= 14) {
      build.properties().version("groovy.version", "3.0.6");
    }
  }

  private static Integer determineJavaGeneration(String javaVersion) {
    try {
      return Integer.parseInt(javaVersion);
    } catch (NumberFormatException ex) {
      return null;
    }
  }
}
