package eCare.controllers;

import eCare.model.dto.ContractDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout, HttpSession httpSession) {

        httpSession.setAttribute("cartChangedContractsList", new HashSet<ContractDTO>());


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
