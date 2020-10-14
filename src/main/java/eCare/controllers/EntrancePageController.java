package eCare.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntrancePageController {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    @GetMapping("/")
    public String getMain(Model model, CsrfToken token) {
        return "main";
    }
}