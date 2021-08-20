/*
 * Copyright (c) 2012-2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.extension.dependency.springnative;

import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.VersionParser;
import io.spring.initializr.generator.version.VersionRange;
import java.util.Arrays;
import java.util.List;

/**
 * Resolve the Spring Native buildtools version to use based on the Spring Native version.
 *
 * @author Stephane Nicoll
 */
abstract class SpringNativeBuildtoolsVersionResolver {

  private static final List<NativeBuildtoolsRange> ranges =
      Arrays.asList(
          new NativeBuildtoolsRange("[0.9.0,0.10.0-M1)", null),
          new NativeBuildtoolsRange("0.10.0-M1", "0.9.1"));

  static String resolve(String springNativeVersion) {
    Version nativeVersion = VersionParser.DEFAULT.parse(springNativeVersion);
    return ranges.stream()
        .filter((range) -> range.match(nativeVersion))
        .findFirst()
        .map(NativeBuildtoolsRange::getVersion)
        .orElse(null);
  }

  private static class NativeBuildtoolsRange {

    private final VersionRange range;

    private final String version;

    NativeBuildtoolsRange(String range, String version) {
      this.range = parseRange(range);
      this.version = version;
    }

    String getVersion() {
      return this.version;
    }

    private static VersionRange parseRange(String s) {
      return VersionParser.DEFAULT.parseRange(s);
    }

    boolean match(Version version) {
      return this.range.match(version);
    }
  }
}
