package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserDTO;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class ContractsController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/contracts")
    public String getClientOffice(Model model, CsrfToken token, Principal principal) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLogin(principal.getName());
        Set<ContractDTO> contractsSet = userDTO.getListOfContracts();
        model.addAttribute("contractsSet", contractsSet);
        return "contracts";
    }

    @PostMapping("/contracts")
    public String postClientOffice(Model model, Principal principal,
                                   @RequestParam String tariffCheckbox) {

        return "contracts";
    }

}