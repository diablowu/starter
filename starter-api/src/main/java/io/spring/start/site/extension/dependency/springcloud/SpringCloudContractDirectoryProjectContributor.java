/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A {@link ProjectContributor} that creates the {@code contracts} resources directory when Spring
 * Cloud Contract Verifier is requested.
 *
 * @author Eddú Meléndez
 */
public class SpringCloudContractDirectoryProjectContributor implements ProjectContributor {

  @Override
  public void contribute(Path projectRoot) throws IOException {
    Path changelogDirectory = projectRoot.resolve("src/test/resources/contracts");
    Files.createDirectories(changelogDirectory);
  }
}
