/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.generator;

public class StarterFeature {
  private boolean useParentModule;
  private boolean useJIB;
  private boolean useDocker;
  private boolean useHelm;
  private boolean useGitLabCI;

  private String dockerImageNameSpace;

  public boolean isUseParentModule() {
    return useParentModule;
  }

  public void setUseParentModule(boolean useParentModule) {
    this.useParentModule = useParentModule;
  }

  public boolean isUseJIB() {
    return useJIB;
  }

  public void setUseJIB(boolean useJIB) {
    this.useJIB = useJIB;
  }

  public boolean isUseDocker() {
    return useDocker;
  }

  public void setUseDocker(boolean useDocker) {
    this.useDocker = useDocker;
  }

  public boolean isUseHelm() {
    return useHelm;
  }

  public void setUseHelm(boolean useHelm) {
    this.useHelm = useHelm;
  }

  public boolean isUseGitLabCI() {
    return useGitLabCI;
  }

  public void setUseGitLabCI(boolean useGitLabCI) {
    this.useGitLabCI = useGitLabCI;
  }

  public String getDockerImageNameSpace() {
    return dockerImageNameSpace;
  }

  public void setDockerImageNameSpace(String dockerImageNameSpace) {
    this.dockerImageNameSpace = dockerImageNameSpace;
  }
}
