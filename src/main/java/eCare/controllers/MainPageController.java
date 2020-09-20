package eCare.controllers;

import eCare.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLOutput;

@Controller
public class MainPageController {

//    @GetMapping("/")
//    public String getMain(Model model, CsrfToken token){
//        return "main";
//    }

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

    //THIS PAGE FOR TESTING PURPOSES ONLY
    @GetMapping("/")
    public String getReg(Model model, CsrfToken token){
        model.addAttribute("userForm", new User());
        return "registration";
    }

}
