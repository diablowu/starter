/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springdata;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyContainer;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import java.util.Arrays;
import java.util.List;

/**
 * A {@link BuildCustomizer} for R2DBC that adds the necessary extra dependencies based on the
 * selected driver, and make sure that {@code spring-jdbc} is available if Flyway or Liquibase is
 * selected.
 *
 * @author Stephane Nicoll
 */
public class R2dbcBuildCustomizer implements BuildCustomizer<Build> {

  private static final List<String> JDBC_DEPENDENCY_IDS =
      Arrays.asList("jdbc", "data-jdbc", "data-jpa");

  @Override
  public void customize(Build build) {
    if (build.dependencies().has("h2")) {
      addManagedDriver(build.dependencies(), "io.r2dbc", "r2dbc-h2");
    }
    if (build.dependencies().has("mariadb")) {
      addManagedDriver(build.dependencies(), "org.mariadb", "r2dbc-mariadb");
    }
    if (build.dependencies().has("mysql")) {
      addManagedDriver(build.dependencies(), "dev.miku", "r2dbc-mysql");
    }
    if (build.dependencies().has("postgresql")) {
      addManagedDriver(build.dependencies(), "io.r2dbc", "r2dbc-postgresql");
    }
    if (build.dependencies().has("sqlserver")) {
      addManagedDriver(build.dependencies(), "io.r2dbc", "r2dbc-mssql");
    }
    if (build.dependencies().has("flyway") || build.dependencies().has("liquibase")) {
      addSpringJdbcIfNecessary(build);
    }
  }

  private void addManagedDriver(
      DependencyContainer dependencies, String groupId, String artifactId) {
    dependencies.add(
        artifactId, Dependency.withCoordinates(groupId, artifactId).scope(DependencyScope.RUNTIME));
  }

  private void addSpringJdbcIfNecessary(Build build) {
    boolean hasSpringJdbc = build.dependencies().ids().anyMatch(JDBC_DEPENDENCY_IDS::contains);
    if (!hasSpringJdbc) {
      build
          .dependencies()
          .add("spring-jdbc", Dependency.withCoordinates("org.springframework", "spring-jdbc"));
    }
  }
}
