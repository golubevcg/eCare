package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserDTO;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class CheckUserController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @Autowired
    UserService userServiceImpl;

    private String oldUserLogin;
    private Long oldUserPassport;
    private String oldUserEmail;

    private UserDTO oldUser;

    @GetMapping(value = "/checkUser/{userLogin}", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model, @PathVariable(name="userLogin") String userLogin){
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(userLogin);
        oldUser = userDTO;
        oldUserLogin = userDTO.getLogin();
        oldUserPassport = userDTO.getPassportInfo();
        oldUserEmail = userDTO.getEmail();
        Set<ContractDTO> listOfContracts = userDTO.getListOfContracts();
        model.addAttribute("userForm", userDTO);
        model.addAttribute("listOfTariffs", listOfContracts);
        return "checkUser";
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/checkPassportInfo/{newPassport}", method = RequestMethod.GET)
    public String checkPassport(@PathVariable("newPassport") String newPassport) {

        if(oldUserPassport.equals(Long.valueOf(newPassport))){
            return "true";
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByPassportInfo(Long.valueOf(newPassport));
        if(listOfUsers!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/checkEmail/{newEmail}", method = RequestMethod.GET)
    public String checkEmail(@PathVariable("newEmail") String newEmail) {

        if(oldUserEmail.equals(newEmail)){
            return "true";
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByEmail(newEmail);
        if(listOfUsers!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/checkLogin/{newLogin}", method = RequestMethod.GET)
    public String checkLogin(@PathVariable("newLogin") String newLogin) {

        if(oldUserLogin.equals(newLogin)){
            return "true";
        }

        UserDTO user = userServiceImpl.getUserDTOByLoginOrNull(newLogin);
        if(user!=null){
            return "true";
        }else{
            return "false";
        }
    }
}
