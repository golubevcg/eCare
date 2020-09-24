package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.RoleDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Contract;
import eCare.model.enitity.User;
import eCare.services.api.TarifService;
import eCare.services.api.UserService;
import eCare.services.impl.UserServiceImpl;
import eCare.validator.ContractValidator;
import eCare.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;

@Controller
public class RegistrationPageController {

    static final Logger log = Logger.getLogger(RegistrationPageController.class);

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private TarifService tarifService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ContractValidator contractValidator;

    @GetMapping("/userRegistration")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserDTO());
        model.addAttribute("tariffsList", tarifService.getAllTariffs());
        model.addAttribute("contractDTO", new ContractDTO());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(Model model, BindingResult bindingResult,
                                   @ModelAttribute("userForm") UserDTO userForm,
                                   @RequestParam("roleCheckbox") String roleCheckbox,
                                   @ModelAttribute("contractDTO") ContractDTO contractDTO,
                                   @ModelAttribute("tariffsList") List<TariffDTO> tariffsDtoList){

        userValidator.validate(userForm, bindingResult);
        contractValidator.validate(contractDTO, bindingResult);
        contractDTO.setUser(userForm);
        userForm.addContractDTO(contractDTO);

        if(roleCheckbox.equals(true)){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setRolename("ADMIN");
            HashSet<RoleDTO> rolesDTOHashSet = new HashSet<>();
            rolesDTOHashSet.add(roleDTO);
            userForm.setRoles(rolesDTOHashSet);
        }

        if(bindingResult.hasErrors()){
            return "userRegistration";
        }
        userServiceImpl.convertDtoAndSave(userForm);

        return "workerOffice";
    }

}
