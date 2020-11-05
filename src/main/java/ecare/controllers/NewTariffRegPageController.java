package ecare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.validator.TariffDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewTariffRegPageController {

    static final Logger log = Logger.getLogger(NewTariffRegPageController.class);

    private final OptionService optionService;

    private final TariffService tariffService;

    private final TariffDTOValidator tariffDTOValidator;

    public NewTariffRegPageController(OptionService optionService, TariffService tariffService, TariffDTOValidator tariffDTOValidator) {
        this.optionService = optionService;
        this.tariffService = tariffService;
        this.tariffDTOValidator = tariffDTOValidator;
    }

    @GetMapping(value = "/newTariff")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionService.getActiveOptionDTOs();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", new TariffDTO());
        return "newTariffRegPage";
    }

    @PostMapping(value = "/newTariff")
    public String submitNewTariff(Model model, @ModelAttribute("tariffDTO") TariffDTO tariffDTO,
                                  BindingResult tariffDTOBindingResult,
                                  @RequestParam(required=false, name= "selectedOptions") String[] selectedOptions){

        tariffDTOValidator.validate(tariffDTO, tariffDTOBindingResult);

        if(tariffDTOBindingResult.hasErrors()){
            return "newTariffRegPage";
        }

        if(selectedOptions!=null){
            Set<OptionDTO> optionDTOSet = new HashSet<>();
            for (int i = 0; i < selectedOptions.length; i++) {

                OptionDTO optionDTO = optionService.getOptionDTOByNameOrNull(selectedOptions[i]);
                optionDTOSet.add( optionDTO);
            }
            tariffDTO.setSetOfOptions(optionDTOSet);
        }

        tariffService.convertToEntityAndSave( tariffDTO );
        log.info("New tariff with name: " + tariffDTO.getName() + " was registered successfully.");

        return "workerOfficePage";
    }

    @PostMapping(value = "/newOption/returnAllObligatoryOptions/")
    public @ResponseBody
    String checkIncOptionDependenciesToPreventRecursion(@RequestBody String expJson) {

        Set<OptionDTO> allOptionsSet = new HashSet<>();
        JsonPrimitive jsonPrimitive = new Gson().fromJson(expJson, JsonPrimitive.class);
        OptionDTO optionDTO = optionService.getOptionDTOByNameOrNull(jsonPrimitive.getAsString());
        optionService.returnAllObligatoryOptions(allOptionsSet,optionDTO);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(allOptionsSet);
    }


}
