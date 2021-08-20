/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.observability;

import io.spring.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import java.util.Arrays;
import java.util.List;

/**
 * Add the actuator dependency if necessary if an observability library has been selected.
 *
 * @author Stephane Nicoll
 */
class ObservabilityBuildCustomizer implements BuildCustomizer<Build> {

  private static final List<String> MICROMETER_REGISTRY_IDS =
      Arrays.asList("datadog", "graphite", "influx", "new-relic");

  @Override
  public void customize(Build build) {
    if (!build.dependencies().has("actuator")
        && build.dependencies().ids().anyMatch(MICROMETER_REGISTRY_IDS::contains)) {
      build.dependencies().add("actuator");
    }
  }
}
