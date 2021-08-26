/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
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
