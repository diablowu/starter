/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionDiff;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudGatewayHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringCloudGatewayHelpDocumentCustomizerTests {

  @Test
  void originalWithSpringCloudGatewayAndSpringWebAddsWarning() {
    HelpDocument document = customizeEmptyDocument(createDiff("web", "cloud-gateway"));
    assertThat(document.getWarnings().getItems()).hasSize(1);
    assertThat(document.getWarnings().getItems().get(0))
        .isEqualTo(
            "Spring Cloud Gateway requires Spring WebFlux, your choice of Spring Web has been replaced accordingly.");
  }

  @Test
  void originalWithSpringCloudGatewayDoesNotAddWarning() {
    HelpDocument document = customizeEmptyDocument(createDiff("cloud-gateway"));
    assertThat(document.getWarnings().getItems()).isEmpty();
  }

  @Test
  void originalWithSpringWebOnlyDoesNotAddWarning() {
    HelpDocument document = customizeEmptyDocument(createDiff("web"));
    assertThat(document.getWarnings().getItems()).isEmpty();
  }

  private HelpDocument customizeEmptyDocument(ProjectDescriptionDiff diff) {
    HelpDocument document = new HelpDocument(mock(MustacheTemplateRenderer.class));
    new SpringCloudGatewayHelpDocumentCustomizer(diff).customize(document);
    return document;
  }

  private ProjectDescriptionDiff createDiff(String... dependencies) {
    MutableProjectDescription description = new MutableProjectDescription();
    for (String dependency : dependencies) {
      description.addDependency(dependency, mock(Dependency.class));
    }
    return new ProjectDescriptionDiff(description);
  }
}
