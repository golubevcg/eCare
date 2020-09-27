package eCare.controllers;

import com.google.gson.Gson;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.RoleDTO;
import eCare.model.dto.UserContractDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Option;
import eCare.services.impl.OptionServiceImpl;
import eCare.services.impl.TariffServiceImpl;
import eCare.services.impl.UserServiceImpl;
import eCare.validator.UserContractDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class RegistrationPageController {

    static final Logger log = Logger.getLogger(RegistrationPageController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TariffServiceImpl tariffServiceImpl;

    @Autowired
    private UserContractDTOValidator userValidator;

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @GetMapping(value = "/userRegistration", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserContractDTO());
        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(Model model,
                                   @ModelAttribute("userForm") UserContractDTO userForm,
                                   BindingResult userFormBindingResult,
                                   @RequestParam(required=false , name = "roleCheckbox") String roleCheckbox,
                                   @RequestParam(required=false , name = "selectedTariff") String selectedTariff,
                                   @RequestParam(required = false, name= "selectedOptions") String[] selectedOptionsArray){

        System.out.println("SELECTED TARIFF: " + selectedTariff);

        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());

        model.addAttribute("selectedTariff", selectedTariff);

        userValidator.validate(userForm, userFormBindingResult);

        ContractDTO contractDTO = userForm.getContractDTO();
        UserDTO userDTO = userForm.getUserDTO();

        contractDTO.setUser(userDTO);
        userForm.addContractDTO(contractDTO);

        RoleDTO roleDTO = new RoleDTO();
        HashSet<RoleDTO> rolesDTOHashSet = new HashSet<>();

        if( roleCheckbox!=null){
            roleDTO.setRolename("ADMIN");
            rolesDTOHashSet.add(roleDTO);
        }else{
            roleDTO.setRolename("USER");
            rolesDTOHashSet.add(roleDTO);
        }

        if(userFormBindingResult.hasErrors()){
            return "userRegistration";
        }

        contractDTO.setTariff(tariffServiceImpl.getTariffDTOByTariffname(selectedTariff));

        System.out.println("++++++++ selected options array:");
        for (int i = 0; i < selectedOptionsArray.length; i++) {
            System.out.println(selectedOptionsArray[i]);
        }

        userServiceImpl.convertDtoAndSave(userDTO);
        log.info("New user successfully registered.");


        return "workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/userRegistration/loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {

        List<Option> optionList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getListOfOptions();
        List<String> optionNamesList = new ArrayList<>();
        for (Option option: optionList) {
            optionNamesList.add(option.getName());
        }

        return new Gson().toJson(optionNamesList);
    }

}
