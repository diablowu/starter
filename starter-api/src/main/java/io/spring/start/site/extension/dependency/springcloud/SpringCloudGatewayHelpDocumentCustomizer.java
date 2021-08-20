/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.ProjectDescriptionDiff;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;
import java.util.Map;

/**
 * A {@link HelpDocumentCustomizer} that adds a warning if Spring Cloud Gateway is used with Spring
 * MVC.
 *
 * @author Stephane Nicoll
 */
public class SpringCloudGatewayHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private final ProjectDescriptionDiff diff;

  public SpringCloudGatewayHelpDocumentCustomizer(ProjectDescriptionDiff diff) {
    this.diff = diff;
  }

  @Override
  public void customize(HelpDocument document) {
    Map<String, Dependency> originalDependencies =
        this.diff.getOriginal().getRequestedDependencies();
    if (originalDependencies.containsKey("cloud-gateway")
        && originalDependencies.containsKey("web")) {
      document
          .getWarnings()
          .addItem(
              "Spring Cloud Gateway requires Spring WebFlux, your choice of Spring Web has been replaced accordingly.");
    }
  }
}
