package eCare.controllers;

import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.validator.TariffDTOValidator;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
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
public class NewTariffRegPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    @Autowired
    private OptionService optionService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private TariffDTOValidator tariffDTOValidator;

    @GetMapping(value = "/newTariff")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionService.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", new TariffDTO());
        return "newTariffRegPage";
    }

    @PostMapping(value = "/newTariff")
    public String getValue(Model model, @ModelAttribute("tariffDTO") TariffDTO tariffDTO,
                                        BindingResult tariffDTOBindingResult,
        @RequestParam(required=false, name= "selectedOptions") String[] selectedOptions){

        tariffDTOValidator.validate(tariffDTO, tariffDTOBindingResult);

        if(tariffDTOBindingResult.hasErrors()){
            return "newTariffRegPage";
        }

        if(selectedOptions!=null){
            Set<OptionDTO> optionDTOSet = new HashSet<>();
            ModelMapper mapper = new ModelMapper();

            for (int i = 0; i < selectedOptions.length; i++) {
                OptionDTO optionDTO = mapper.map(optionService.getOptionByName(selectedOptions[i]), OptionDTO.class);
                optionDTOSet.add( optionDTO);
            }
            tariffDTO.setSetOfOptions(optionDTOSet);
        }

        tariffService.convertToEntityAndSave( tariffDTO );
        log.info("New tariff with name: " + tariffDTO.getName() + " was registered successfully.");

        return "workerOfficePage";
    }

}
