package eCare.controllers;

import eCare.model.dto.OptionDTO;
import eCare.services.impl.OptionServiceImpl;
import eCare.validator.OptionDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewOptionController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @Autowired
    private OptionDTOValidator optionDTOValidator;

    @GetMapping(value = "/newOption")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", new OptionDTO());
        return "newOption";
    }

    @PostMapping(value = "/newOption")
    public String getValue(Model model,
                                        @ModelAttribute("optionDTO") OptionDTO optionDTO,
                                        BindingResult optionDTOBindingResult,
        @RequestParam(required=false, name= "selectedObligatoryOptions") String[] selectedObligatoryOptions,
        @RequestParam(required=false, name= "selectedIncompatibleOptions") String[] selectedIncompatibleOptions){

        optionDTOValidator.validate(optionDTO, optionDTOBindingResult);

        if(optionDTOBindingResult.hasErrors()){
            return "newOption";
        }

        if(selectedIncompatibleOptions!=null){
            Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedIncompatibleOptions.length; i++) {
                incompatibleOptionsSet.add( optionServiceImpl.getOptionDTOByNameOrNull(selectedIncompatibleOptions[i]) );
            }
            optionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet);
        }

        if(selectedObligatoryOptions!=null) {
            Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedObligatoryOptions.length; i++) {
                obligatoryOptionsSet.add(optionServiceImpl.getOptionDTOByNameOrNull(selectedObligatoryOptions[i]));
            }
            optionDTO.setObligatoryOptionsSet(obligatoryOptionsSet);
        }

        optionServiceImpl.convertToEntityAndSave(optionDTO);
        log.info("New option was registered successfully.");

        return "/workerOffice";
    }

}
