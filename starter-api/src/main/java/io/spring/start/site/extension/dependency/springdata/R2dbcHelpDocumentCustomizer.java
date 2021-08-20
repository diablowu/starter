/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springdata;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.initializr.generator.spring.documentation.HelpDocumentCustomizer;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link HelpDocumentCustomizer} that adds a section when R2DBC is selected but no driver was
 * selected.
 *
 * @author Stephane Nicoll
 */
public class R2dbcHelpDocumentCustomizer implements HelpDocumentCustomizer {

  private static final List<String> DRIVERS =
      Arrays.asList("h2", "mariadb", "mysql", "postgresql", "sqlserver");

  private final Build build;

  public R2dbcHelpDocumentCustomizer(Build build) {
    this.build = build;
  }

  @Override
  public void customize(HelpDocument document) {
    if (this.build.dependencies().ids().noneMatch(DRIVERS::contains)) {
      document.addSection(
          (writer) -> {
            writer.println("## Missing R2DBC Driver");
            writer.println();
            writer.println(
                "Make sure to include a [R2DBC Driver](https://r2dbc.io/drivers/) to connect to your database.");
          });
    }
  }
}
