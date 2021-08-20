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
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImplicitDependency}.
 *
 * @author Stephane Nicoll
 */
class ImplicitDependencyTests {

  @Test
  void customizeBuildWhenPredicateIsTrueInvokesConsumer() {
    Consumer<Build> buildCustomizer = mockBuildCustomizer();
    ImplicitDependency dependency =
        new Builder().match((build) -> true).customizeBuild(buildCustomizer).build();
    Build build = mock(Build.class);
    dependency.customize(build);
    verify(buildCustomizer).accept(build);
  }

  @Test
  void customizeBuildWhenPredicateIsFalseDoesNotInvokeConsumer() {
    Consumer<Build> buildCustomizer = mockBuildCustomizer();
    ImplicitDependency dependency =
        new Builder().match((build) -> false).customizeBuild(buildCustomizer).build();
    Build build = mock(Build.class);
    dependency.customize(build);
    verifyNoInteractions(buildCustomizer);
  }

  @Test
  void customizeBuildWhenPredicateIsTrueAndNoBuildConsumer() {
    ImplicitDependency dependency = new Builder().match((build) -> false).build();
    Build build = mock(Build.class);
    dependency.customize(build);
    verifyNoInteractions(build);
  }

  @Test
  void customizeBuildWithMatchingDependencyInvokesConsumer() {
    Consumer<Build> buildCustomizer = mockBuildCustomizer();
    ImplicitDependency dependency =
        new Builder()
            .matchAnyDependencyIds("test", "another")
            .customizeBuild(buildCustomizer)
            .build();
    Build build = new MavenBuild();
    build.dependencies().add("another", mock(Dependency.class));
    dependency.customize(build);
    verify(buildCustomizer).accept(build);
  }

  @Test
  void customizeBuildWithNoMatchingDependencyDoesNotInvokeConsumer() {
    Consumer<Build> buildCustomizer = mockBuildCustomizer();
    ImplicitDependency dependency =
        new Builder()
            .matchAnyDependencyIds("test", "another")
            .customizeBuild(buildCustomizer)
            .build();
    Build build = new MavenBuild();
    build.dependencies().add("no-match", mock(Dependency.class));
    dependency.customize(build);
    verifyNoInteractions(buildCustomizer);
  }

  @Test
  void customizeHelpDocumentWhenPredicateIsTrueInvokesConsumer() {
    Consumer<HelpDocument> buildCustomizer = mockHelpDocumentCustomizer();
    ImplicitDependency dependency =
        new Builder().match((build) -> true).customizeHelpDocument(buildCustomizer).build();
    HelpDocument helpDocument = mock(HelpDocument.class);
    Build build = mock(Build.class);
    dependency.customize(helpDocument, build);
    verify(buildCustomizer).accept(helpDocument);
  }

  @Test
  void customizeHelpDocumentWhenPredicateIsFalseDoesNotInvokeConsumer() {
    Consumer<HelpDocument> buildCustomizer = mockHelpDocumentCustomizer();
    ImplicitDependency dependency =
        new Builder().match((build) -> false).customizeHelpDocument(buildCustomizer).build();
    HelpDocument helpDocument = mock(HelpDocument.class);
    Build build = mock(Build.class);
    dependency.customize(helpDocument, build);
    verifyNoInteractions(buildCustomizer);
  }

  @Test
  void customizeHelpDocumentWhenPredicateIsTrueAndNoHelpDocumentConsumer() {
    ImplicitDependency dependency = new Builder().match((build) -> true).build();
    HelpDocument helpDocument = mock(HelpDocument.class);
    Build build = mock(Build.class);
    dependency.customize(helpDocument, build);
    verifyNoInteractions(helpDocument);
  }

  @SuppressWarnings("unchecked")
  private Consumer<Build> mockBuildCustomizer() {
    return mock(Consumer.class);
  }

  @SuppressWarnings("unchecked")
  private Consumer<HelpDocument> mockHelpDocumentCustomizer() {
    return mock(Consumer.class);
  }
}
