package io.spring.start.site.web;

import io.spring.initializr.web.project.ProjectRequest;
import io.spring.start.site.generator.StarterFeature;

public class StarterRequest extends ProjectRequest {

  private StarterFeature starter;

  public StarterFeature getStarter() {
    return starter;
  }

  public void setStarter(StarterFeature starter) {
    this.starter = starter;
  }
}
