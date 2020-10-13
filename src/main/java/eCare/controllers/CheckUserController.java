package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserDTO;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class CheckUserController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @Autowired
    UserService userServiceImpl;

    @GetMapping(value = "/checkUser/{userLogin}", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model, @PathVariable(name="userLogin") String userLogin){
        UserDTO userDTO = userServiceImpl.getUserDTOByLogin(userLogin);
        Set<ContractDTO> listOfContracts = userDTO.getListOfContracts();
        model.addAttribute("userForm", userDTO);
        model.addAttribute("listOfTariffs", listOfContracts);
        return "checkUser";
    }
}
