package eCare.controllers;

import com.google.gson.Gson;
import eCare.model.dto.*;
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

import java.util.HashSet;

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
                                   @RequestParam(required=false , name = "selectedTariff") String selectedTariff){

        System.out.println("SELECTED TARIFF: " + selectedTariff);

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

        userServiceImpl.convertDtoAndSave(userDTO);


        return "workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {
        Gson gson = new Gson();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("selectedTariff" + selectedTariff);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getListOfOptions().get(0).getName());
        return gson.toJson(
                tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getListOfOptions()
        );
    }

}
