/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.MavenRepository;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.version.VersionReference;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringNativeRepositoryBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringNativeRepositoryBuildCustomizerTests {

  @Test
  void mavenRepositoryIsRegisteredWhenMavenRepositoryIsSet() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("2.0.0")));
    MavenRepository testRepository =
        MavenRepository.withIdAndUrl("test", "https://repo.example.com").name("Test repo").build();
    new SpringNativeRepositoryBuildCustomizer(testRepository).customize(build);
    Consumer<MavenRepository> validateMavenRepository =
        (repository) -> {
          assertThat(repository.getName()).isEqualTo("Test repo");
          assertThat(repository.getUrl()).isEqualTo("https://repo.example.com");
        };
    assertThat(build.repositories().ids()).containsOnly("test");
    assertThat(build.repositories().get("test")).satisfies(validateMavenRepository);
    assertThat(build.pluginRepositories().ids()).containsOnly("test");
    assertThat(build.pluginRepositories().get("test")).satisfies(validateMavenRepository);
  }

  @Test
  void mavenRepositoryIsNotRegisteredWhenMavenRepositoryIsNull() {
    MavenBuild build = new MavenBuild();
    build
        .dependencies()
        .add(
            "native",
            Dependency.withCoordinates("com.example", "native")
                .version(VersionReference.ofValue("2.0.0")));
    new SpringNativeRepositoryBuildCustomizer(null).customize(build);
    assertThat(build.repositories().isEmpty()).isTrue();
    assertThat(build.pluginRepositories().isEmpty()).isTrue();
  }
}
