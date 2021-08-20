/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.build.gradle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.nio.file.Path;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link ManagedDependenciesDependencyManagementPluginVersionResolver}.
 *
 * @author Stephane Nicoll
 */
class ManagedDependenciesDependencyManagementPluginVersionResolverTests {

  private static DependencyManagementVersionResolver dependencyManagementVersionResolver;

  @BeforeAll
  static void setup(@TempDir Path cacheLocation) {
    dependencyManagementVersionResolver =
        DependencyManagementVersionResolver.withCacheLocation(cacheLocation);
  }

  @Test
  @SuppressWarnings("unchecked")
  void dependencyManagementPluginVersionCanBeResolved() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setPlatformVersion(Version.parse("2.1.8.RELEASE"));
    Function<ProjectDescription, String> fallback = mock(Function.class);
    String version =
        new ManagedDependenciesDependencyManagementPluginVersionResolver(
                dependencyManagementVersionResolver, fallback)
            .resolveDependencyManagementPluginVersion(description);
    assertThat(version).isEqualTo("1.0.8.RELEASE");
    verifyNoInteractions(fallback);
  }

  @Test
  @SuppressWarnings("unchecked")
  void dependencyManagementPluginVersionUsesFallbackIfNecessary() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setPlatformVersion(Version.parse("2.1.7.RELEASE"));
    Function<ProjectDescription, String> fallback = mock(Function.class);
    given(fallback.apply(description)).willReturn("1.0.0.RELEASE");
    String version =
        new ManagedDependenciesDependencyManagementPluginVersionResolver(
                dependencyManagementVersionResolver, fallback)
            .resolveDependencyManagementPluginVersion(description);
    assertThat(version).isEqualTo("1.0.0.RELEASE");
    verify(fallback).apply(description);
  }
}
