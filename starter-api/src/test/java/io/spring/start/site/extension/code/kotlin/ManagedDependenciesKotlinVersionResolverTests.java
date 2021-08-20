/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.code.kotlin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.nio.file.Path;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link ManagedDependenciesKotlinVersionResolver}.
 *
 * @author Andy Wilkinson
 */
class ManagedDependenciesKotlinVersionResolverTests {

  @Test
  @SuppressWarnings("unchecked")
  void kotlinVersionCanBeResolved(@TempDir Path temp) {
    MutableProjectDescription description = new MutableProjectDescription();
    description.setPlatformVersion(Version.parse("2.5.0"));
    Function<ProjectDescription, String> fallback = mock(Function.class);
    String version =
        new ManagedDependenciesKotlinVersionResolver(
                DependencyManagementVersionResolver.withCacheLocation(temp), fallback)
            .resolveKotlinVersion(description);
    assertThat(version).isEqualTo("1.5.0");
    verifyNoInteractions(fallback);
  }
}
