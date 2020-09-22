package eCare.controllers;

import eCare.security.UserDetailsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainPageController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @GetMapping("/")
    public String getMain(Model model, String error, String logout) {
//            if (error != null) {
//                model.addAttribute("error", "Username or password is incorrect.");
//            }
//
//            if (logout != null) {
//                model.addAttribute("message", "Logged out successfully.");
//            }
            return "main";
    }

    @PostMapping("/")
    public String postMain(Model model, String error, String logout) {

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
