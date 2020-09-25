package eCare.controllers;

import eCare.model.dto.*;
import eCare.services.api.TarifService;
import eCare.services.api.UserService;
import eCare.services.impl.ContractServiceImpl;
import eCare.validator.UserContractValidator;
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
    private UserService userServiceImpl;

    @Autowired
    private TarifService tarifService;

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @Autowired
    private UserContractValidator userValidator;

    @GetMapping("/userRegistration")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserContractDTO());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(Model model,
                                   @ModelAttribute("userForm") UserContractDTO userForm,
                                   BindingResult userFormBindingResult,
                                   @RequestParam(required=false , name = "roleCheckbox") String roleCheckbox){

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
