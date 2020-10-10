package eCare.controllers;

import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Option;
import eCare.services.impl.OptionServiceImpl;
import eCare.services.impl.TariffServiceImpl;
import eCare.validator.TariffDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NewTariffController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionServiceImpl optionService;

    @Autowired
    private TariffServiceImpl tariffService;

    @Autowired
    private TariffDTOValidator tariffDTOValidator;

    @GetMapping(value = "/newTariff")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionService.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", new TariffDTO());
        return "newTariff";
    }

    @PostMapping(value = "/newTariff")
    public String getValue(Model model,
                                        @ModelAttribute("tariffDTO") TariffDTO tariffDTO,
                                        BindingResult tariffDTOBindingResult,
        @RequestParam(required=false, name= "selectedOptions") String[] selectedOptions){

        tariffDTOValidator.validate(tariffDTO, tariffDTOBindingResult);

        if(tariffDTOBindingResult.hasErrors()){
            return "newTariff";
        }

        if(selectedOptions!=null){
            for (int i = 0; i < selectedOptions.length; i++) {
                OptionDTO optionDTO = optionService.getOptionDTOByName(selectedOptions[i]);
                System.out.printf(optionDTO.toString());
                tariffDTO.addOptionDTO( optionDTO);
            }
        }

        System.out.println( tariffDTO.toString() );

        tariffService.convertToEntityAndSave( tariffDTO );
        log.info("New tariff was registered successfully.");

        return "/workerOffice";
    }

}
