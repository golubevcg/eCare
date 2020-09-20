package eCare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String getMain(Model model){
        return "main";
    }

    @GetMapping("/clientOffice")
    public String getClientOffice(Model model){
        return "clientOffice";
    }

    @GetMapping("/workerOffice")
    public String getWorkerOffice(Model model){
        return "workerOffice";
    }

    @GetMapping("/userRegistration")
    public String getUserRegistration(Model model){
        return "userRegistration";
    }

    @GetMapping("/newTarif")
    public String getNewTarif(Model model){
        return "newTarif";
    }

    @GetMapping("/newOption")
    public String getNewOption(Model model){
        return "newOption";
    }
}
