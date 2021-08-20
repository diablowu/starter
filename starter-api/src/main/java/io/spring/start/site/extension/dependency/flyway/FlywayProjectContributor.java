/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.flyway;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@link ProjectContributor} that creates the "db/migration" resources directory when Flyway is
 * requested.
 *
 * @author Stephane Nicoll
 */
public class FlywayProjectContributor implements ProjectContributor {

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Path migrationDirectory = projectRoot.resolve("src/main/resources/db/migration");
    Files.createDirectories(migrationDirectory);
  }
}
