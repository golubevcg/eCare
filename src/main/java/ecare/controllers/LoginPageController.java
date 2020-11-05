package ecare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout, HttpSession httpSession) {

        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "loginPage";
    }

    @PostMapping("/login")
    public String postMain(Model model, String error, String logout) {
        return "loginPage";
    }
}
