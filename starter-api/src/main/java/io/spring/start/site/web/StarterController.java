/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.web;

import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;
import io.spring.initializr.web.project.ProjectGenerationResult;
import io.spring.start.site.generator.StarterFeature;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

  @RequestMapping("/starter.zip")
  public ResponseEntity<byte[]> springZip(StarterRequest request) throws IOException {
    initialize(request, metadataProvider.get());
    ProjectGenerationResult result =
        this.projectGenerationInvoker.invokeProjectStructureGeneration(request);
    Path archive =
        createArchive(
            result,
            "zip",
            ZipArchiveOutputStream::new,
            ZipArchiveEntry::new,
            ZipArchiveEntry::setUnixMode);
    return upload(
        archive, result.getRootDirectory(), generateFileName(request, "zip"), "application/zip");
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

  private <T extends ArchiveEntry> Path createArchive(
      ProjectGenerationResult result,
      String fileExtension,
      Function<OutputStream, ? extends ArchiveOutputStream> archiveOutputStream,
      BiFunction<File, String, T> archiveEntry,
      BiConsumer<T, Integer> setMode)
      throws IOException {
    Path archive =
        this.projectGenerationInvoker.createDistributionFile(
            result.getRootDirectory(), "." + fileExtension);
    String wrapperScript = getWrapperScript(result.getProjectDescription());
    try (ArchiveOutputStream output = archiveOutputStream.apply(Files.newOutputStream(archive))) {
      Files.walk(result.getRootDirectory())
          .filter((path) -> !result.getRootDirectory().equals(path))
          .forEach(
              (path) -> {
                try {
                  String entryName = getEntryName(result.getRootDirectory(), path);
                  T entry = archiveEntry.apply(path.toFile(), entryName);
                  setMode.accept(entry, getUnixMode(wrapperScript, entryName, path));
                  output.putArchiveEntry(entry);
                  if (!Files.isDirectory(path)) {
                    Files.copy(path, output);
                  }
                  output.closeArchiveEntry();
                } catch (IOException ex) {
                  throw new IllegalStateException(ex);
                }
              });
    }
    return archive;
  }

  private String getEntryName(Path root, Path path) {
    String entryName = root.relativize(path).toString().replace('\\', '/');
    if (Files.isDirectory(path)) {
      entryName += "/";
    }
    return entryName;
  }

  private int getUnixMode(String wrapperScript, String entryName, Path path) {
    if (Files.isDirectory(path)) {
      return UnixStat.DIR_FLAG | UnixStat.DEFAULT_DIR_PERM;
    }
    return UnixStat.FILE_FLAG
        | (entryName.equals(wrapperScript) ? 0755 : UnixStat.DEFAULT_FILE_PERM);
  }

  private String generateFileName(StarterRequest request, String extension) {
    String candidate =
        (StringUtils.hasText(request.getArtifactId())
            ? request.getArtifactId()
            : this.metadataProvider.get().getArtifactId().getContent());
    String tmp = candidate.replaceAll(" ", "_");
    try {
      return URLEncoder.encode(tmp, "UTF-8") + "." + extension;
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException("Cannot encode URL", ex);
    }
  }

  private static String getWrapperScript(ProjectDescription description) {
    BuildSystem buildSystem = description.getBuildSystem();
    String script = buildSystem.id().equals(MavenBuildSystem.ID) ? "mvnw" : "gradlew";
    return (description.getBaseDirectory() != null)
        ? description.getBaseDirectory() + "/" + script
        : script;
  }

  private ResponseEntity<byte[]> upload(Path archive, Path dir, String fileName, String contentType)
      throws IOException {
    byte[] bytes = Files.readAllBytes(archive);
    logger.info(String.format("Uploading: %s (%s bytes)", archive, bytes.length));
    ResponseEntity<byte[]> result = createResponseEntity(bytes, contentType, fileName);
    this.projectGenerationInvoker.cleanTempFiles(dir);
    return result;
  }
}
