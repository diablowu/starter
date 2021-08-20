/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.gradle;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionRange;

/**
 * A {@link HelpDocumentCustomizer} that adds reference links for Gradle.
 *
 * @author Jenn Strater
 * @author Stephane Nicoll
 */
class GradleBuildSystemHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private static final VersionRange SPRING_BOOT_2_3_OR_LATER =
      VersionParser.DEFAULT.parseRange("2.3.0.M1");

  private final Version springBootVersion;

  private final boolean buildImageAvailable;

  GradleBuildSystemHelpDocumentCustomizer(ProjectDescription description) {
    this.springBootVersion = description.getPlatformVersion();
    this.buildImageAvailable = SPRING_BOOT_2_3_OR_LATER.match(this.springBootVersion);
  }

  @Override
  public void customize(HelpDocument document) {
    document
        .gettingStarted()
        .addAdditionalLink(
            "https://scans.gradle.com#gradle",
            "Gradle Build Scans – insights for your project's build");
    document
        .gettingStarted()
        .addReferenceDocLink("https://docs.gradle.org", "Official Gradle documentation");
    document
        .gettingStarted()
        .addReferenceDocLink(
            String.format(
                "https://docs.spring.io/spring-boot/docs/%s/gradle-plugin/reference/html/",
                this.springBootVersion),
            "Spring Boot Gradle Plugin Reference Guide");
    if (this.buildImageAvailable) {
      document
          .gettingStarted()
          .addReferenceDocLink(
              String.format(
                  "https://docs.spring.io/spring-boot/docs/%s/gradle-plugin/reference/html/#build-image",
                  this.springBootVersion),
              "Create an OCI image");
    }
  }
}
