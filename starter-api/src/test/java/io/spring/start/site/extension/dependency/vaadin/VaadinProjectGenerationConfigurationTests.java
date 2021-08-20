/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.vaadin;

import static org.assertj.core.api.Assertions.assertThat;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.extension.AbstractExtensionTests;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link VaadinProjectGenerationConfiguration}.
 *
 * @author Stephane Nicoll
 */
class VaadinProjectGenerationConfigurationTests extends AbstractExtensionTests {

  @Test
  void mavenBuildWithVaadinAddProductionProfile() {
    assertThat(mavenPom(createProjectRequest("vaadin", "data-jpa")))
        .hasProfile("production")
        .lines()
        .containsSequence(
            "		<profile>",
            "			<id>production</id>",
            "			<build>",
            "				<plugins>",
            "					<plugin>",
            "						<groupId>com.vaadin</groupId>",
            "						<artifactId>vaadin-maven-plugin</artifactId>",
            "						<version>${vaadin.version}</version>",
            "						<executions>",
            "							<execution>",
            "								<id>frontend</id>",
            "								<phase>compile</phase>",
            "								<goals>",
            "									<goal>prepare-frontend</goal>",
            "									<goal>build-frontend</goal>",
            "								</goals>",
            "								<configuration>",
            "									<productionMode>true</productionMode>",
            "								</configuration>",
            "							</execution>",
            "						</executions>",
            "					</plugin>",
            "				</plugins>",
            "			</build>",
            "		</profile>");
  }

  @Test
  void mavenBuildWithoutVaadinDoesNotAddProductionProfile() {
    assertThat(mavenPom(createProjectRequest("data-jpa"))).doesNotHaveProfile("production");
  }

  @Test
  void gradleBuildWithVaadinAddPlugin() {
    assertThat(gradleBuild(createProjectRequest("vaadin", "data-jpa")))
        .hasPlugin("com.vaadin", "0.14.6.0");
  }

  @Test
  void gradleBuildWithoutVaadinDoesNotAddPlugin() {
    assertThat(gradleBuild(createProjectRequest("data-jpa"))).doesNotContain("id 'com.vaadin'");
  }

  @Test
  void gitIgnoreWithVaadinIgnoreNodeModules() {
    assertThat(generateProject(createProjectRequest("vaadin", "data-jpa")))
        .textFile(".gitignore")
        .contains("node_modules");
  }

  @Test
  void gitIgnoreWithoutVaadinDoesNotIgnoreNodeModules() {
    assertThat(generateProject(createProjectRequest("data-jpa")))
        .textFile(".gitignore")
        .doesNotContain("node_modules");
  }

  @Override
  protected ProjectRequest createProjectRequest(String... styles) {
    ProjectRequest request = super.createProjectRequest(styles);
    request.setBootVersion("2.4.6");
    return request;
  }
}
