package eCare.controllers;

import eCare.config.SpringSecurityConfig;
import eCare.model.enitity.User;
import eCare.services.impl.UserServiceImpl;
import eCare.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegistrationPageController {

    static final Logger log = Logger.getLogger(RegistrationPageController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/userRegistration")
    public String getUserRegistration(Model model, CsrfToken token){
        model.addAttribute("userForm", new User());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(@ModelAttribute("userForm") User userForm,
                                   BindingResult bindingResult, Model modelб){

        userValidator.validate(userForm, bindingResult);

        if(bindingResult.hasErrors()){

            return "userRegistration";
        }

        userServiceImpl.save(userForm);

        return "workerOffice";
    }

}
