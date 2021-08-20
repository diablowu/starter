/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springcloud;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Resolve Spring Cloud artifact versions using a {@link DependencyManagementVersionResolver}.
 *
 * @author Olga Maciaszek-Sharma
 * @author Stephane Nicoll
 */
class SpringCloudProjectVersionResolver {

  private static final Log logger = LogFactory.getLog(SpringCloudProjectVersionResolver.class);

  private final InitializrMetadata metadata;

  private final DependencyManagementVersionResolver resolver;

  SpringCloudProjectVersionResolver(
      InitializrMetadata metadata, DependencyManagementVersionResolver resolver) {
    this.metadata = metadata;
    this.resolver = resolver;
  }

  /**
   * Resolve the version of a specified artifact that matches the provided Spring Boot version.
   *
   * @param platformVersion the Spring Boot version to check the Spring Cloud Release train version
   *     against
   * @param dependencyId the dependency id of the Spring Cloud artifact in the form of {@code
   *     groupId:artifactId}
   * @return the appropriate project version or {@code null} if the resolution failed
   */
  String resolveVersion(Version platformVersion, String dependencyId) {
    BillOfMaterials bom = this.metadata.getConfiguration().getEnv().getBoms().get("spring-cloud");
    if (bom == null) {
      return null;
    }
    String releaseTrainVersion = bom.resolve(platformVersion).getVersion();
    logger.info(
        "Retrieving version for artifact: "
            + dependencyId
            + " and release train version: "
            + releaseTrainVersion);
    return this.resolver
        .resolve("org.springframework.cloud", "spring-cloud-dependencies", releaseTrainVersion)
        .get(dependencyId);
  }
}
