package eCare.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @GetMapping("/")
    public String getMain(Model model, CsrfToken token){
        return "main";
    }


    @GetMapping("/newTariff")
    public String getNewTarif(Model model, CsrfToken token){
        return "newTariff";
    }

    @GetMapping("/newOption")
    public String getNewOption(Model model, CsrfToken token){
        return "newOption";
    }

   }
