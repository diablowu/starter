package io.spring.start.site.web;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.start.site.generator.StarterFeature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/starter")
public class StarterController {

  private static final Log logger = LogFactory.getLog(ProjectGenerationController.class);

  private final InitializrMetadataProvider metadataProvider;

  private final ProjectGenerationInvoker<StarterRequest> projectGenerationInvoker;

  public StarterController(
      InitializrMetadataProvider metadataProvider,
      ProjectGenerationInvoker<StarterRequest> projectGenerationInvoker) {
    this.metadataProvider = metadataProvider;
    this.projectGenerationInvoker = projectGenerationInvoker;
  }

  @RequestMapping(path = {"/pom", "/pom.xml"})
  public ResponseEntity<byte[]> pom(StarterRequest request) {
    request.setType("maven-build");
    initialize(request, metadataProvider.get());
    byte[] mavenPom = this.projectGenerationInvoker.invokeBuildGeneration(request);
    return createResponseEntity(mavenPom, "application/octet-stream", "pom.xml");
  }

  private ResponseEntity<byte[]> createResponseEntity(
      byte[] content, String contentType, String fileName) {
    String contentDispositionValue = "attachment; filename=\"" + fileName + "\"";
    return ResponseEntity.ok()
        .header("Content-Type", contentType)
        .header("Content-Disposition", contentDispositionValue)
        .body(content);
  }

  public void initialize(StarterRequest request, InitializrMetadata metadata) {

    if (request.getStarter() == null) {
      request.setStarter(new StarterFeature());
    }

    BeanWrapperImpl bean = new BeanWrapperImpl(request);
    metadata
        .defaults()
        .forEach(
            (key, value) -> {
              if (bean.isWritableProperty(key)) {
                // We want to be able to infer a package name if none has been
                // explicitly set
                if (!key.equals("packageName")) {
                  bean.setPropertyValue(key, value);
                }
              }
            });
  }
}
