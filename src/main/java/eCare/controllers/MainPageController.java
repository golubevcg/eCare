package eCare.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String getMain(Model model, CsrfToken token){
        return "main";
    }

    @GetMapping("/clientOffice")
    public String getClientOffice(Model model, CsrfToken token){
        return "clientOffice";
    }

    @GetMapping("/workerOffice")
    public String getWorkerOffice(Model model, CsrfToken token){
        return "workerOffice";
    }

    @GetMapping("/userRegistration")
    public String getUserRegistration(Model model, CsrfToken token){
        return "userRegistration";
    }

    @GetMapping("/newTarif")
    public String getNewTarif(Model model, CsrfToken token){
        return "newTarif";
    }

    @GetMapping("/newOption")
    public String getNewOption(Model model, CsrfToken token){
        return "newOption";
    }

   }
