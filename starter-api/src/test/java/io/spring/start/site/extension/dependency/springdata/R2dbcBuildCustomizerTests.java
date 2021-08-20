/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springdata;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests for {@link R2dbcBuildCustomizer}.
 *
 * @author Stephane Nicoll
 */
class R2dbcBuildCustomizerTests extends AbstractExtensionTests {

  @Test
  void r2dbcWithH2() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("h2");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "h2", "r2dbc-h2");
  }

  @Test
  void r2dbcWithMariadb() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("mariadb");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "mariadb", "r2dbc-mariadb");
  }

  @Test
  void r2dbcWithMysql() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("mysql");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "mysql", "r2dbc-mysql");
  }

  @Test
  void r2dbcWithPostgresql() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("postgresql");
    customize(build);
    assertThat(build.dependencies().ids())
        .containsOnly("data-r2dbc", "postgresql", "r2dbc-postgresql");
  }

  @Test
  void r2dbcWithSqlserver() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("sqlserver");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "sqlserver", "r2dbc-mssql");
  }

  @Test
  void r2dbcWithFlywayAddSpringJdbc() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("flyway");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "flyway", "spring-jdbc");
  }

  @ParameterizedTest
  @ValueSource(strings = {"jdbc", "data-jdbc", "data-jpa"})
  void r2dbcWithFlywayAndJdbcStaterDoesNotAddSpringJdbc(String jdbcStarter) {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("flyway");
    build.dependencies().add(jdbcStarter);
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "flyway", jdbcStarter);
  }

  @Test
  void r2dbcWithLiquibaseAddSpringJdbc() {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("liquibase");
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "liquibase", "spring-jdbc");
  }

  @ParameterizedTest
  @ValueSource(strings = {"jdbc", "data-jdbc", "data-jpa"})
  void r2dbcWithLiquibaseAndJdbcStaterDoesNotAddSpringJdbc(String jdbcStarter) {
    Build build = createBuild();
    build.dependencies().add("data-r2dbc");
    build.dependencies().add("liquibase");
    build.dependencies().add(jdbcStarter);
    customize(build);
    assertThat(build.dependencies().ids()).containsOnly("data-r2dbc", "liquibase", jdbcStarter);
  }

  private Build createBuild() {
    InitializrMetadata metadata = getMetadata();
    return new MavenBuild(
        new MetadataBuildItemResolver(
            metadata, Version.parse(metadata.getBootVersions().getDefault().getId())));
  }

  private void customize(Build build) {
    new R2dbcBuildCustomizer().customize(build);
  }
}
