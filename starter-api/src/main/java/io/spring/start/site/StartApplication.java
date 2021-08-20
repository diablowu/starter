/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spring.initializr.versionresolver.DependencyManagementVersionResolver;
import io.spring.start.site.project.ProjectDescriptionCustomizerConfiguration;
import io.spring.start.site.support.CacheableDependencyManagementVersionResolver;
import io.spring.start.site.support.StartInitializrMetadataUpdateStrategy;
import io.spring.start.site.web.HomeController;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Initializr website application.
 *
 * @author Stephane Nicoll
 */
@EnableAutoConfiguration
@SpringBootConfiguration
@Import(ProjectDescriptionCustomizerConfiguration.class)
@EnableCaching
@EnableAsync
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

  @Bean
  public HomeController homeController() {
    return new HomeController();
  }

  @Bean
  public StartInitializrMetadataUpdateStrategy initializrMetadataUpdateStrategy(
      RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
    return new StartInitializrMetadataUpdateStrategy(restTemplateBuilder.build(), objectMapper);
  }

  @Bean
  public DependencyManagementVersionResolver dependencyManagementVersionResolver()
      throws IOException {
    return new CacheableDependencyManagementVersionResolver(
        DependencyManagementVersionResolver.withCacheLocation(
            Files.createTempDirectory("version-resolver-cache-")));
  }
}
