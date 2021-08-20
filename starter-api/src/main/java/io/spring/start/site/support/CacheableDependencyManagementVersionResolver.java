/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.support;

import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;

/**
 * A {@link DependencyManagementVersionResolver} that uses the metadata cache to store dependency
 * management resolution.
 *
 * @author Stephane Nicoll
 */
public class CacheableDependencyManagementVersionResolver
    implements DependencyManagementVersionResolver {

  private final DependencyManagementVersionResolver delegate;

  public CacheableDependencyManagementVersionResolver(
      DependencyManagementVersionResolver delegate) {
    this.delegate = delegate;
  }

  @Override
  @Cacheable("initializr.metadata")
  public Map<String, String> resolve(String groupId, String artifactId, String version) {
    return this.delegate.resolve(groupId, artifactId, version);
  }
}
