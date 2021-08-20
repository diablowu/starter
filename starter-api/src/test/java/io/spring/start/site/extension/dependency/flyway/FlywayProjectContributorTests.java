/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.flyway;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FlywayProjectContributor}.
 *
 * @author Stephane Nicoll
 */
class FlywayProjectContributorTests extends AbstractExtensionTests {

  @Test
  void flywayMigrationDirectoryIsCreatedWithFlyway() {
    ProjectRequest request = createProjectRequest("web", "flyway");
    ProjectStructure structure = generateProject(request);
    assertThat(structure.getProjectDirectory().resolve("src/main/resources/db/migration"))
        .exists()
        .isDirectory();
  }

  @Test
  void flywayMigrationDirectoryIsNotCreatedIfFlywayIsNotRequested() {
    ProjectRequest request = createProjectRequest("web");
    ProjectStructure structure = generateProject(request);
    assertThat(structure.getProjectDirectory().resolve("src/main/resources/db")).doesNotExist();
  }
}
