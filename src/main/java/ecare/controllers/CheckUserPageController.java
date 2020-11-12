package ecare.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.UserDTO;
import ecare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * Controller for page, in which checking or editing of user occur.
 */

@Controller
public class CheckUserPageController {

    static final Logger log = Logger.getLogger(CheckUserPageController.class);

    final
    UserService userServiceImpl;

    private String userLoginBeforeEditing;
    private String userPassportBeforeEditing;
    private String userEmailBeforeEditing;

    public CheckUserPageController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/checkUser/{userLogin}", produces = "text/plain;charset=UTF-8")
    public String getUserRegistrationPage(Model model, @PathVariable(name = "userLogin") String userLogin) {
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
    @GetMapping(value = "/checkUser/checkPassportInfo/{newPassport}")
    public boolean checkPassport(@PathVariable("newPassport") String newPassport) {

        if (userPassportBeforeEditing!=null && userPassportBeforeEditing.equals(newPassport)) {
            return true;
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByPassportInfo(newPassport);
        return listOfUsers != null;
    }

    @ResponseBody
    @GetMapping(value = "/checkUser/checkEmail/{newEmail}")
    public boolean checkEmail(@PathVariable("newEmail") String newEmail) {

        if (userPassportBeforeEditing!=null && userEmailBeforeEditing.equals(newEmail)) {
            return true;
        }

        List<UserDTO> listOfUsers = userServiceImpl.getUserDTOByEmail(newEmail);
        return listOfUsers != null;
    }

    @ResponseBody
    @GetMapping(value = "/checkUser/checkLogin/{newLogin}")
    public boolean checkLogin(@PathVariable("newLogin") String newLogin) {

        if (userPassportBeforeEditing!=null && userLoginBeforeEditing.equals(newLogin)) {
            return true;
        }

        UserDTO user = userServiceImpl.getUserDTOByLoginOrNull(newLogin);
        return user != null;
    }

    @PostMapping(value = "/checkUser/submitChanges/", produces = "application/json")
    public @ResponseBody
    boolean submitValues(@RequestBody String exportArray) {
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
            userDTO.setDateOfBirth(new SimpleDateFormat("yyyy-dd-mm").parse(dateOfBirth));
        } catch (ParseException e) {
            log.info("There was an Error during date parsing.");
        }
        userDTO.setPassportInfo(passportInfo);
        userDTO.setAddress(address);
        userDTO.setEmail(email);
        userDTO.setLogin(login);

        userServiceImpl.convertToEntityAndUpdate(userDTO);

        log.info("User " + login + "successfully was updated");

        return true;
    }

    @ResponseBody
    @GetMapping(value = "/checkUser/deleteUser/{login}")
    public boolean deleteUser(@PathVariable("login") String login) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(login);
        if(userDTO==null){
            return false;
        }else{
            userDTO.setActive(false);
            userServiceImpl.convertToEntityAndUpdate(userDTO);
            return true;
        }

    }
}