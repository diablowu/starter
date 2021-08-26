/*
 * Copyright (c) 2021 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.initializr.generator.buildsystem.maven;

public class CDATA {

  private static final String START_TAG = "<![CDATA[";
  private static final int START_TAG_LEN = START_TAG.length();
  private static final String END_TAG = "]]>";
  private static final int END_TAG_LEN = END_TAG.length();

  private String realString;

  public static CDATA parse(String text) {
    if (text == null || text.length() <= START_TAG_LEN + END_TAG_LEN) {
      return null;
    }

    if (text.startsWith(START_TAG) && text.endsWith(END_TAG)) {
      CDATA cdata = new CDATA();
      cdata.realString =
          String.format("%s", text.subSequence(START_TAG_LEN, text.length() - END_TAG_LEN));
      return cdata;
    } else {
      return null;
    }
  }

  public static String wrap(String realString) {
    return START_TAG + realString + END_TAG;
  }

  @Override
  public String toString() {
    return realString;
  }
}
