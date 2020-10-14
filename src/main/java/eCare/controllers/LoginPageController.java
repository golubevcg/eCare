package eCare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String getLogin(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String postMain(Model model, String error, String logout) {
        return "login";
    }
}
