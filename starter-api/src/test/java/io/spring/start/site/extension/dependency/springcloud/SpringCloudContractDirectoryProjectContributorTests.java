/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudContractDirectoryProjectContributor}.
 *
 * @author Eddú Meléndez
 */
class SpringCloudContractDirectoryProjectContributorTests extends AbstractExtensionTests {

  @Test
  void contractsDirectoryIsCreatedWithSpringCloudContractVerifier() {
    ProjectRequest request = createProjectRequest("web", "cloud-contract-verifier");
    ProjectStructure structure = generateProject(request);
    assertThat(structure.getProjectDirectory().resolve("src/test/resources/contracts"))
        .exists()
        .isDirectory();
  }

  @Test
  void contractsDirectoryIsNotCreatedIfSpringCloudContractVerifierIsNotRequested() {
    ProjectRequest request = createProjectRequest("web");
    ProjectStructure structure = generateProject(request);
    assertThat(structure.getProjectDirectory().resolve("src/test/resources/contracts"))
        .doesNotExist();
  }
}
