package io.spring.start.site.generator;

import io.spring.initializr.generator.project.MutableProjectDescription;

public class StarterProjectDescription extends MutableProjectDescription {

  private StarterFeature starter;

  public StarterFeature getStarter() {
    return starter;
  }

  public void setStarter(StarterFeature starter) {
    this.starter = starter;
  }
}
