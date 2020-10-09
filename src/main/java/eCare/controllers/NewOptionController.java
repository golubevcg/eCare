package eCare.controllers;

import eCare.model.dto.OptionDTO;
import eCare.services.impl.OptionServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NewOptionController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @GetMapping(value = "/newOption")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", new OptionDTO());
        return "newOption";
    }

    @PostMapping(value = "/newOption")
    public String getValue(Model model,
                           @ModelAttribute("optionDTO") OptionDTO optionDTO){

        System.out.println(optionDTO.getName());
        return "newOption";
    }

}
