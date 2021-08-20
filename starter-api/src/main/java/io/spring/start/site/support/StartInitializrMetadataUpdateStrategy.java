/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.web.support.InitializrMetadataUpdateStrategy;
import io.spring.initializr.web.support.SaganInitializrMetadataUpdateStrategy;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.client.RestTemplate;

/**
 * An {@link InitializrMetadataUpdateStrategy} that performs additional filtering of versions
 * available on spring.io.
 *
 * @author Stephane Nicoll
 */
public class StartInitializrMetadataUpdateStrategy extends SaganInitializrMetadataUpdateStrategy {

  public StartInitializrMetadataUpdateStrategy(
      RestTemplate restTemplate, ObjectMapper objectMapper) {
    super(restTemplate, objectMapper);
  }

  @Override
  protected List<DefaultMetadataElement> fetchSpringBootVersions(String url) {
    List<DefaultMetadataElement> versions = super.fetchSpringBootVersions(url);
    return (versions != null)
        ? versions.stream().filter(this::isCompatibleVersion).collect(Collectors.toList())
        : null;
  }

  private boolean isCompatibleVersion(DefaultMetadataElement versionMetadata) {
    Version version = Version.parse(versionMetadata.getId());
    return (version.getMajor() >= 2 && version.getMinor() > 3);
  }
}
