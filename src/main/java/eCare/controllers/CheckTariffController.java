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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckTariffController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionService optionService;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private TariffDTOValidator tariffDTOValidator;

    @GetMapping(value = "/checkTariff/{tariffName}")
    public String getUserRegistration(Model model, @PathVariable(name="tariffName")String tariffName){

        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(tariffName);

        List<OptionDTO> listOfAllActiveOptions = optionService.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", tariffDTO);
        return "checkTariff";
    }

    @PostMapping(value = "/checkTariff")
    public String getValue(Model model, @ModelAttribute("tariffDTO") TariffDTO tariffDTO,
                                        BindingResult tariffDTOBindingResult,
        @RequestParam(required=false, name= "selectedOptions") String[] selectedOptions){


        return "/workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/checkTariff/checkNewName/{newName}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("newName") String newName) {
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(newName);
        if(tariffDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

}
