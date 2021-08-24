/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.initializr.metadata.InitializrConfiguration;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.start.site.generator.StarterRequestToDescriptionConverter;
import io.spring.start.site.support.CacheableDependencyManagementVersionResolver;
import io.spring.start.site.support.StartInitializrMetadataUpdateStrategy;
import io.spring.start.site.web.StarterController;
import io.spring.start.site.web.StarterRequest;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StarterConfig.class)
public class StarterConfiguration {

  @Bean
  @ConditionalOnProperty(name = "starter-config.fetch-boot-version", havingValue = "true")
  public StartInitializrMetadataUpdateStrategy initializrMetadataUpdateStrategy(
      RestTemplateBuilder restTemplateBuilder,
      ObjectMapper objectMapper,
      InitializrConfiguration cfg,
      StarterConfig starterConfig) {

    // FIXME: update SpringBootMetadataUrl before StartInitializrMetadataUpdateStrategy invoke
    cfg.getEnv().setSpringBootMetadataUrl(starterConfig.getSpringBootMetadataUrl().toString());
    return new StartInitializrMetadataUpdateStrategy(restTemplateBuilder.build(), objectMapper);
  }

  @Bean
  public DependencyManagementVersionResolver dependencyManagementVersionResolver()
      throws IOException {
    return new CacheableDependencyManagementVersionResolver(
        DependencyManagementVersionResolver.withCacheLocation(
            Files.createTempDirectory("version-resolver-cache-")));
  }

  @Bean
  public StarterController starterController(
      InitializrMetadataProvider imp, ApplicationContext ctx) {
    var pgi =
        new ProjectGenerationInvoker<StarterRequest>(
            ctx, new StarterRequestToDescriptionConverter());
    return new StarterController(imp, pgi);
  }
}
