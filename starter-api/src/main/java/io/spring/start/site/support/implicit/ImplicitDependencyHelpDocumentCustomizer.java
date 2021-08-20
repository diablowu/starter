/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.support.implicit;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;

/**
 * A {@link HelpDocumentCustomizer} that customize the help document if necessary based on {@link
 * ImplicitDependency implicit dependencies}.
 *
 * @author Stephane Nicoll
 */
public class ImplicitDependencyHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private final Iterable<ImplicitDependency> dependencies;

  private final Build build;

  public ImplicitDependencyHelpDocumentCustomizer(
      Iterable<ImplicitDependency> dependencies, Build build) {
    this.dependencies = dependencies;
    this.build = build;
  }

  @Override
  public void customize(HelpDocument document) {
    for (ImplicitDependency dependency : this.dependencies) {
      dependency.customize(document, this.build);
    }
  }
}
