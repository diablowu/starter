/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.description;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionDiff;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link ProjectGenerationConfiguration} for customizations relevant to the {@link
 * ProjectDescription}.
 *
 * @author Stephane Nicoll
 */
@ProjectGenerationConfiguration
public class DescriptionProjectGenerationConfiguration {

  @Bean
  public InvalidJvmVersionHelpDocumentCustomizer invalidJvmVersionHelpDocumentCustomizer(
      ProjectDescriptionDiff diff, ProjectDescription description) {
    return new InvalidJvmVersionHelpDocumentCustomizer(diff, description);
  }

  @Bean
  public InvalidPackageNameHelpDocumentCustomizer invalidPackageNameHelpDocumentCustomizer(
      ProjectDescriptionDiff diff, ProjectDescription description) {
    return new InvalidPackageNameHelpDocumentCustomizer(diff, description);
  }
}
