/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.project.dependency.springcloud;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.MutableProjectDescription;
import java.util.Collections;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SpringCloudGatewayProjectDescriptionCustomizer}.
 *
 * @author Stephane Nicoll
 */
class SpringCloudGatewayProjectDescriptionCustomizerTests {

  @Test
  void customizeWithSpringCloudGatewayAndSpringMvcMigratesToSpringWebFlux() {
    MutableProjectDescription description = new MutableProjectDescription();
    description.addDependency("cloud-gateway", mock(Dependency.class));
    description.addDependency("web", mock(Dependency.class));
    new SpringCloudGatewayProjectDescriptionCustomizer().customize(description);
    assertThat(description.getRequestedDependencies()).containsOnlyKeys("cloud-gateway", "webflux");
    Dependency webflux = description.getRequestedDependencies().get("webflux");
    assertThat(webflux.getGroupId()).isEqualTo("org.springframework.boot");
    assertThat(webflux.getArtifactId()).isEqualTo("spring-boot-starter-webflux");
  }

  @Test
  void customizeWithSpringCloudGatewayDoesNotAddSpringWebFlux() {
    MutableProjectDescription description = mock(MutableProjectDescription.class);
    given(description.getRequestedDependencies())
        .willReturn(Collections.singletonMap("cloud-gateway", mock(Dependency.class)));
    new SpringCloudGatewayProjectDescriptionCustomizer().customize(description);
    verify(description).getRequestedDependencies();
    verifyNoMoreInteractions(description);
  }

  @Test
  void customizeWithoutSpringCloudGatewayDoesNotRemoveSpringMvc() {
    MutableProjectDescription description = mock(MutableProjectDescription.class);
    given(description.getRequestedDependencies())
        .willReturn(Collections.singletonMap("web", mock(Dependency.class)));
    new SpringCloudGatewayProjectDescriptionCustomizer().customize(description);
    verify(description).getRequestedDependencies();
    verifyNoMoreInteractions(description);
  }
}
