package eCare.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ClientOfficeController {

    @GetMapping("/clientOffice")
    public String getClientOffice(Model model, CsrfToken token) {
        return "clientOffice";
    }

}