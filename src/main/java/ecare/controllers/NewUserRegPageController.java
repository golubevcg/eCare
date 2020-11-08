package ecare.controllers;

import com.google.gson.Gson;
import ecare.model.dto.*;
import ecare.model.entity.Option;
import ecare.services.api.*;
import ecare.validator.UserContractDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class NewUserRegPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    private final UserService userServiceImpl;

    private final UserContractDTOValidator userValidator;

    private final TariffService tariffServiceImpl;

    public NewUserRegPageController(UserContractDTOValidator userValidator,
                                    TariffService tariffServiceImpl,
                                    UserService userServiceImpl) {
        this.userValidator = userValidator;
        this.tariffServiceImpl = tariffServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/userRegistration", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserContractDTO());
        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        return "newUserRegPage";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(Model model,
                                   @ModelAttribute("userForm") UserContractDTO userForm,
                                   BindingResult userFormBindingResult,
                                   @RequestParam(required=false , name = "roleCheckbox") String roleCheckbox,
                                   @RequestParam(required=false , name = "selectedTariff") String selectedTariff,
                                   @RequestParam(required=false, name= "selectedOptions") String[] selectedOptionsArray){

        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        model.addAttribute("selectedTariff", selectedTariff);

        userValidator.validate(userForm, userFormBindingResult, Boolean.valueOf(roleCheckbox));
        if(userFormBindingResult.hasErrors()){
            return "newUserRegPage";
        }

        userServiceImpl.submitUserOnControllerData(userForm, roleCheckbox, selectedTariff, selectedOptionsArray);

        return "workerOfficePage";
    }

    @ResponseBody
    @GetMapping(value = "/userRegistration/loadOptionByTariff/{selectedTariff}")
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {

        Set<Option> optionList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (Option option: optionList) {
            optionNamesSet.add(option.getName());
        }

        return new Gson().toJson(optionNamesSet);
    }

}
