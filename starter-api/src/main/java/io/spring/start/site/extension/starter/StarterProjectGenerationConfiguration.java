package io.spring.start.site.extension.starter;

import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.condition.ConditionalOnBuildSystem;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.start.site.extension.build.maven.GoogleJIBPluginBuildCustomizer;
import io.spring.start.site.extension.build.maven.GoogleJavaFormatterPluginBuildCustomizer;
import io.spring.start.site.generator.ConditionalOnStarterFeature;
import org.springframework.context.annotation.Bean;

@ProjectGenerationConfiguration
@ConditionalOnBuildSystem(MavenBuildSystem.ID)
public class StarterProjectGenerationConfiguration {

  @Bean
  GoogleJavaFormatterPluginBuildCustomizer googleJavaFormatterPluginBuildCustomizer() {
    return new GoogleJavaFormatterPluginBuildCustomizer();
  }

  @Bean
  @ConditionalOnStarterFeature("useJIB")
  GoogleJIBPluginBuildCustomizer googleJIBPluginBuildCustomizer() {
    return new GoogleJIBPluginBuildCustomizer();
  }
}
