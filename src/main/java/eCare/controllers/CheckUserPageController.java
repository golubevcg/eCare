package eCare.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserDTO;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@Controller
public class CheckUserPageController {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    @Autowired
    UserService userServiceImpl;

    private String userLoginBeforeEditing;
    private Long userPassportBeforeEditing;
    private String userEmailBeforeEditing;

    @GetMapping(value = "/checkUser/{userLogin}", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model, @PathVariable(name = "userLogin") String userLogin) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(userLogin);
        userLoginBeforeEditing = userDTO.getLogin();
        userPassportBeforeEditing = userDTO.getPassportInfo();
        userEmailBeforeEditing = userDTO.getEmail();
        Set<ContractDTO> listOfContracts = userDTO.getListOfContracts();
        model.addAttribute("userForm", userDTO);
        model.addAttribute("listOfTariffs", listOfContracts);
        return "checkUserPage";
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/checkPassportInfo/{newPassport}", method = RequestMethod.GET)
    public String checkPassport(@PathVariable("newPassport") String newPassport) {

        if (userPassportBeforeEditing.equals(Long.valueOf(newPassport))) {
            return "true";
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByPassportInfo(Long.valueOf(newPassport));
        if (listOfUsers != null) {
            return "false";
        } else {
            return "true";
        }
    }

    @ResponseBody
    @GetMapping(value = "/checkUser/checkEmail/{newEmail}")
    public String checkEmail(@PathVariable("newEmail") String newEmail) {

        if (userEmailBeforeEditing.equals(newEmail)) {
            return "true";
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByEmail(newEmail);
        if (listOfUsers != null) {
            return "false";
        } else {
            return "true";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/checkLogin/{newLogin}", method = RequestMethod.GET)
    public String checkLogin(@PathVariable("newLogin") String newLogin) {

        if (userLoginBeforeEditing.equals(newLogin)) {
            return "true";
        }

        UserDTO user = userServiceImpl.getUserDTOByLoginOrNull(newLogin);
        if (user != null) {
            return "false";
        } else {
            return "true";
        }
    }

    @PostMapping(value = "/checkUser/submitChanges/", produces = "application/json")
    public @ResponseBody
    String submitValues(Model model, CsrfToken token, Principal principal,
                        @RequestBody String exportArray) {
        JsonObject jsonObject = JsonParser.parseString(exportArray).getAsJsonObject();

        String firstName = jsonObject.get("firstName").getAsString();
        String secondName = jsonObject.get("secondName").getAsString();
        String dateOfBirth = jsonObject.get("dateOfBirth").getAsString();
        String passportInfo = jsonObject.get("passportInfo").getAsString();
        String address = jsonObject.get("address").getAsString();
        String email = jsonObject.get("email").getAsString();
        String login = jsonObject.get("login").getAsString();

        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(userLoginBeforeEditing);

        userDTO.setFirstname(firstName);
        userDTO.setSecondname(secondName);
        try {
            userDTO.setDateOfBirth(new SimpleDateFormat("yyyy/dd/mm").parse(dateOfBirth));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userDTO.setPassportInfo(Long.valueOf(passportInfo));
        userDTO.setAddress(address);
        userDTO.setEmail(email);
        userDTO.setLogin(login);

        userServiceImpl.convertToEntityAndUpdate(userDTO);

        log.info("User " + login + "successfully was updated");

        return "true";
    }

    @ResponseBody
    @RequestMapping(value = "/checkUser/deleteUser/{login}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("login") String login) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(login);
        if(userDTO==null){
            return "false";
        }else{
            userDTO.setActive(false);
            userServiceImpl.convertToEntityAndUpdate(userDTO);
            return "true";
        }

    }
}