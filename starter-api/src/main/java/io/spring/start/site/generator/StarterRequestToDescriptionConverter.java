/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.generator;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.web.project.DefaultProjectRequestToDescriptionConverter;
import io.spring.initializr.web.project.ProjectRequestToDescriptionConverter;
import io.spring.start.site.web.StarterRequest;

public class StarterRequestToDescriptionConverter
    implements ProjectRequestToDescriptionConverter<StarterRequest> {

  @Override
  public ProjectDescription convert(StarterRequest request, InitializrMetadata metadata) {
    var dc = new DefaultProjectRequestToDescriptionConverter();
    StarterProjectDescription spd = new StarterProjectDescription();
    dc.convert(request, spd, metadata);
    spd.setStarter(request.getStarter());
    return spd;
  }
}
