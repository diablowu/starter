package io.spring.start.site.generator;

import io.spring.initializr.generator.condition.ProjectGenerationCondition;
import io.spring.initializr.generator.project.ProjectDescription;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalOnStarterFeatureCondition extends ProjectGenerationCondition {

  @Override
  protected boolean matches(
      ProjectDescription description, ConditionContext context, AnnotatedTypeMetadata metadata) {

    var starterFeature = ((StarterProjectDescription) description).getStarter();

    var annotations = metadata.getAnnotationAttributes(ConditionalOnStarterFeature.class.getName());

    if (annotations == null || annotations.isEmpty()) {
      return false;
    }

    if (!annotations.containsKey("value")) {
      return false;
    }

    String featureName = (String) annotations.get("value");

    if (StringUtils.isBlank(featureName)) {
      return false;
    }

    switch (featureName) {
      case "useDocker":
        {
          return starterFeature.isUseDocker();
        }
      case "useJIB":
        {
          return starterFeature.isUseJIB();
        }
      case "useParentModule":
        {
          return starterFeature.isUseParentModule();
        }
      case "useHelm":
        {
          return starterFeature.isUseHelm();
        }
      case "useGitLabCI":
        {
          return starterFeature.isUseGitLabCI();
        }
      default:
        {
          return false;
        }
    }
  }
}
