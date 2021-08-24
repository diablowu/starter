package io.spring.start.site.extension.build.maven;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenPlugin.ConfigurationBuilder;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

public class GoogleJIBPluginBuildCustomizer implements BuildCustomizer<MavenBuild> {

  @Override
  public void customize(MavenBuild build) {

    var profile = build.profiles().id("jib-build");

    profile.properties().property("timestamp", "${maven.build.timestamp}");
    build
        .profiles()
        .id("jib-build")
        .properties()
        .property("maven.build.timestamp.format", "yyyy-MM-dd'T'HH:mm:ss'Z'");
    profile.properties().property("main.class", "${maven.build.timestamp}");
    profile
        .properties()
        .property("image.base", "registry.taikangcloud.com/tkp/base/jib-java-base:11");
    profile.properties().property("image.registry", "registry.taikangcloud.com");
    profile.properties().property("image.ns", "/");
    profile.properties().property("image.tag", "dev");
    profile.properties().property("allowInsecureRegistries", "true");

    profile
        .plugins()
        .add(
            "com.google.cloud.tools",
            "jib-maven-plugin",
            pluginBuilder ->
                pluginBuilder
                    .version("2.5.2")
                    .execution(
                        "auto-package-image",
                        executionBuilder ->
                            executionBuilder
                                .phase("package")
                                .goal("build")
                                .configuration(this::configJIBPlugin)));
  }

  private void configJIBPlugin(ConfigurationBuilder builder) {
    builder
        .add("from", b -> b.add("image", "${image.base}"))
        .add("to", b -> b.add("image", "${image.registry}/${image.ns}:${image.tag}"))
        .add("allowInsecureRegistries", "${allowInsecureRegistries}")
        .add(
            "container",
            b -> {
              b.add("entrypoint", b1 -> b1.add("entry", "/app/entry.sh"))
                  .add("mainClass", "${main.class}")
                  .add("filesModificationTime", "${timestamp}")
                  .add("creationTime", "${timestamp}")
                  .add(
                      "environment",
                      b2 ->
                          b2.add("SERVER_PORT", "8080")
                              .add("LOGGING_LEVEL_ROOT", "WARN")
                              .add("MAIN_CLASS", "${main.class}"))
                  .add("ports", b2 -> b2.add("port", "8080"));
            });
  }
}
