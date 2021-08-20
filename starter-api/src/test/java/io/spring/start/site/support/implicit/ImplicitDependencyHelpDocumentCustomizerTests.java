/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.support.implicit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.documentation.HelpDocument;
import io.spring.start.site.support.implicit.ImplicitDependency.Builder;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImplicitDependencyHelpDocumentCustomizer}.
 *
 * @author Stephane Nicoll
 */
class ImplicitDependencyHelpDocumentCustomizerTests {

  private final HelpDocument document = mock(HelpDocument.class);

  @Test
  void customizerWithMatchingBuildIsInvoked() {
    Consumer<HelpDocument> helpDocumentCustomizer = mockHelpDocumentCustomizer();
    List<ImplicitDependency> dependencies =
        Collections.singletonList(
            new Builder()
                .matchAnyDependencyIds("test")
                .customizeHelpDocument(helpDocumentCustomizer)
                .build());
    Build build = new MavenBuild();
    build.dependencies().add("test", mock(Dependency.class));
    new ImplicitDependencyHelpDocumentCustomizer(dependencies, build).customize(this.document);
    verify(helpDocumentCustomizer).accept(this.document);
  }

  @Test
  void customizerWithNonMatchingBuildIsNotInvoked() {
    Consumer<HelpDocument> helpDocumentCustomizer = mockHelpDocumentCustomizer();
    List<ImplicitDependency> dependencies =
        Collections.singletonList(
            new Builder()
                .matchAnyDependencyIds("test")
                .customizeHelpDocument(helpDocumentCustomizer)
                .build());
    Build build = new MavenBuild();
    build.dependencies().add("another", mock(Dependency.class));
    new ImplicitDependencyHelpDocumentCustomizer(dependencies, build).customize(this.document);
    verifyNoInteractions(helpDocumentCustomizer);
  }

  @SuppressWarnings("unchecked")
  private Consumer<HelpDocument> mockHelpDocumentCustomizer() {
    return mock(Consumer.class);
  }
}
