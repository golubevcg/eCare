package eCare.controllers;

import eCare.model.dto.*;
import eCare.services.impl.TariffServiceImpl;
import eCare.services.impl.UserServiceImpl;
import eCare.validator.UserContractDTOValidator;
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

@Controller
public class RegistrationPageController {

    static final Logger log = Logger.getLogger(RegistrationPageController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TariffServiceImpl tariffService;

    @Autowired
    private UserContractDTOValidator userValidator;

    @GetMapping(value = "/userRegistration", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserContractDTO());
        model.addAttribute("listOfTariffs", tariffService.getActiveTariffs());

        System.out.println(tariffService.getActiveTariffs().get(0).getName());

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

}
