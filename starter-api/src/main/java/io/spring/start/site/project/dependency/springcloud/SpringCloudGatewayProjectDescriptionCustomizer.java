/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project.dependency.springcloud;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionCustomizer;
import java.util.Collection;

/**
 * A {@link ProjectDescriptionCustomizer} that checks that Spring Cloud Gateway is not used with
 * Spring MVC as only Spring WebFlux is supported.
 *
 * @author Stephane Nicoll
 */
public class SpringCloudGatewayProjectDescriptionCustomizer
    implements ProjectDescriptionCustomizer {

  @Override
  public void customize(MutableProjectDescription description) {
    Collection<String> dependencyIds = description.getRequestedDependencies().keySet();
    if (dependencyIds.contains("cloud-gateway") && dependencyIds.contains("web")) {
      description.removeDependency("web");
      if (!description.getRequestedDependencies().containsKey("webflux")) {
        description.addDependency(
            "webflux",
            Dependency.withCoordinates("org.springframework.boot", "spring-boot-starter-webflux"));
      }
    }
  }
}
