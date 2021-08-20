/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project;

import io.spring.initializr.generator.project.ProjectDescriptionCustomizer;
import io.spring.start.site.project.dependency.springcloud.SpringCloudGatewayProjectDescriptionCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for {@link ProjectDescriptionCustomizer}s.
 *
 * @author Madhura Bhave
 */
@Configuration
public class ProjectDescriptionCustomizerConfiguration {

  @Bean
  public JavaVersionProjectDescriptionCustomizer javaVersionProjectDescriptionCustomizer() {
    return new JavaVersionProjectDescriptionCustomizer();
  }

  @Bean
  public GradleDslProjectDescriptionCustomizer gradleDslProjectDescriptionCustomizer() {
    return new GradleDslProjectDescriptionCustomizer();
  }

  @Bean
  public SpringCloudGatewayProjectDescriptionCustomizer
      springCloudGatewayProjectDescriptionCustomizer() {
    return new SpringCloudGatewayProjectDescriptionCustomizer();
  }
}
