/*
 * Copyright (c) 2012-2020 Taikang Pension. All rights reserved.
 * Taikang Pension PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package io.spring.start.site.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Main Controller.
 *
 * @author Brian Clozel
 */
@Controller
public class HomeController {

  @GetMapping(path = "/", produces = MediaType.TEXT_HTML_VALUE)
  public String home() {
    return "forward:index.html";
  }
}
