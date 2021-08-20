/*
 * Copyright (c) 2012-2019 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension;

import io.spring.initializr.generator.test.buildsystem.gradle.GroovyDslGradleBuildAssert;
import io.spring.initializr.generator.test.buildsystem.maven.MavenBuildAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.BillOfMaterials;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.project.DefaultProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectGenerationResult;
import io.spring.initializr.web.project.ProjectRequest;
import io.spring.initializr.web.project.WebProjectRequest;
import java.util.Arrays;
import org.assertj.core.api.AssertProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Base test class for extensions.
 *
 * @author Stephane Nicoll
 */
@SpringBootTest
public abstract class AbstractExtensionTests {

  @Autowired private ApplicationContext applicationContext;

  @Autowired private InitializrMetadataProvider metadataProvider;

  private ProjectGenerationInvoker<ProjectRequest> invoker;

  private ProjectGenerationInvoker<ProjectRequest> getInvoker() {
    if (this.invoker == null) {
      this.invoker =
          new ProjectGenerationInvoker<>(
              this.applicationContext, new DefaultProjectRequestToDescriptionConverter());
    }
    return this.invoker;
  }

  protected InitializrMetadata getMetadata() {
    return this.metadataProvider.get();
  }

  protected Dependency getDependency(String id) {
    return getMetadata().getDependencies().get(id);
  }

  protected BillOfMaterials getBom(String id, String version) {
    BillOfMaterials bom = getMetadata().getConfiguration().getEnv().getBoms().get(id);
    return bom.resolve(Version.parse(version));
  }

  protected AssertProvider<MavenBuildAssert> mavenPom(ProjectRequest request) {
    request.setType("maven-build");
    String content = new String(getInvoker().invokeBuildGeneration(request));
    return () -> new MavenBuildAssert(content);
  }

  protected AssertProvider<GroovyDslGradleBuildAssert> gradleBuild(ProjectRequest request) {
    request.setType("gradle-build");
    String content = new String(getInvoker().invokeBuildGeneration(request));
    return () -> new GroovyDslGradleBuildAssert(content);
  }

  protected ProjectStructure generateProject(ProjectRequest request) {
    ProjectGenerationResult result = getInvoker().invokeProjectStructureGeneration(request);
    return new ProjectStructure(result.getRootDirectory());
  }

  protected ProjectRequest createProjectRequest(String... styles) {
    WebProjectRequest request = new WebProjectRequest();
    request.initialize(this.metadataProvider.get());
    request.getDependencies().addAll(Arrays.asList(styles));
    return request;
  }
}
