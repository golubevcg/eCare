package ecare.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntrancePageController {

    @GetMapping("/")
    public String getEntrancePage(Model model, CsrfToken token) {
        return "entrancePage";
    }
}