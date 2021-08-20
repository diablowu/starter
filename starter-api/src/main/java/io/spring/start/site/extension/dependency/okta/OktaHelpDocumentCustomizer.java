/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.okta;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.io.text.MustacheSection;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;
import java.util.Collections;

/**
 * A {@link HelpDocumentCustomizer} that provides some additional getting started instructions for
 * Okta.
 *
 * @author Stephane Nicoll
 */
public class OktaHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private final MustacheTemplateRenderer templateRenderer;

  public OktaHelpDocumentCustomizer(MustacheTemplateRenderer templateRenderer) {
    this.templateRenderer = templateRenderer;
  }

  @Override
  public void customize(HelpDocument document) {
    document.addSection(new MustacheSection(this.templateRenderer, "okta", Collections.emptyMap()));
  }
}
