/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.description;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionDiff;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;
import java.util.Objects;

/**
 * A {@link HelpDocumentCustomizer} that adds a warning when the JVM level was changed.
 *
 * @author Stephane Nicoll
 */
public class InvalidJvmVersionHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private final ProjectDescriptionDiff diff;

  private final ProjectDescription description;

  public InvalidJvmVersionHelpDocumentCustomizer(
      ProjectDescriptionDiff diff, ProjectDescription description) {
    this.diff = diff;
    this.description = description;
  }

  @Override
  public void customize(HelpDocument document) {
    this.diff.ifLanguageChanged(
        this.description,
        (original, current) -> {
          String originalJvmVersion = original.jvmVersion();
          String currentJvmVersion = current.jvmVersion();
          if (!Objects.equals(originalJvmVersion, currentJvmVersion)) {
            document
                .getWarnings()
                .addItem(
                    String.format(
                        "The JVM level was changed from '%s' to '%s', review the [JDK Version Range](%s) on the wiki for more details.",
                        originalJvmVersion,
                        currentJvmVersion,
                        "https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range"));
          }
        });
  }
}
