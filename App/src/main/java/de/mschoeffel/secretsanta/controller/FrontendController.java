package de.mschoeffel.secretsanta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Frontend controller to redirect direct url requests
 */
@Controller
public class FrontendController {

    @RequestMapping("{?:(?:(?!api|static|\\.).)*}/**")
    public String redirectApi() {
        return "/index.html";
    }
}
