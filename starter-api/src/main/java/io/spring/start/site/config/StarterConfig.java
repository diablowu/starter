/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.config;

import java.net.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "starter-config")
public class StarterConfig {

  private Boolean fetchBootVersion;
  private URL springBootMetadataUrl;

  public Boolean getFetchBootVersion() {
    return fetchBootVersion;
  }

  public void setFetchBootVersion(Boolean fetchBootVersion) {
    this.fetchBootVersion = fetchBootVersion;
  }

  public URL getSpringBootMetadataUrl() {
    return springBootMetadataUrl;
  }

  public void setSpringBootMetadataUrl(URL springBootMetadataUrl) {
    this.springBootMetadataUrl = springBootMetadataUrl;
  }
}
