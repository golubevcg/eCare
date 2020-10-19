package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.entity.Option;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for page, in which checking or editing contracts occur.
 */

@Controller
public class CheckContractPageController {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    @Autowired
    UserService userServiceImpl;

    @Autowired
    TariffService tariffServiceImpl;

    @Autowired
    OptionService optionServiceImpl;

    @Autowired
    ContractService contractServiceImpl;

    private String contractNumberBeforeEditing;
    private String userNameOfContractOwnerBeforeEditing;

    @GetMapping(value = "/checkContract/{contractId}")
    public String getCheckContractPage(Model model,
                                       @PathVariable(name="contractId") String contractId){
        ContractDTO contractDTOBeforeEditing = contractServiceImpl.getContractDTOById(Long.valueOf(contractId)).get(0);
        contractNumberBeforeEditing = contractDTOBeforeEditing.getContractNumber();
        userNameOfContractOwnerBeforeEditing = contractDTOBeforeEditing.getUser().getLogin();
        List<TariffDTO> listOfTariffs = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("listOfTariffs", listOfTariffs);
        model.addAttribute("contractDTO", contractDTOBeforeEditing);
        return "checkContractPage";
    }

    @ResponseBody
    @RequestMapping(value = "/checkContract/loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String returnOptionsListForSelectedTariff(@PathVariable("selectedTariff") String selectedTariff) {
        Set<Option> optionsList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getSetOfOptions();

        Set<String> optionNamesSet = new HashSet<>();
        for (Option option: optionsList) {
            optionNamesSet.add(option.getName());
        }

        return new Gson().toJson(optionNamesSet);
    }

    @GetMapping(value = "/checkContract/getUsersList", produces = "application/json")
    public @ResponseBody
    String getUsersList(@RequestParam(value="term", required = false, defaultValue="") String term){
        List<UserDTO> listOfUsers = userServiceImpl.searchForUserByLogin(term);
        List<String> secondAndFirstNameOfUserlist = new ArrayList<>();

        for (UserDTO user: listOfUsers) {
            secondAndFirstNameOfUserlist.add(user.getLogin());
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(secondAndFirstNameOfUserlist);
    }


    @ResponseBody
    @RequestMapping(value = "/checkContract/getAddionalOptions/{oldNumber}", method = RequestMethod.GET)
    public String getAvailableOptions(@PathVariable("oldNumber") String oldNumber) {
        ContractDTO contractDTO = contractServiceImpl.getContractDTOByNumber(oldNumber).get(0);
        Set<OptionDTO> optionsSet = contractDTO.getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (OptionDTO option: optionsSet) {
            optionNamesSet.add(option.getName());
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(optionNamesSet);
    }

    @ResponseBody
    @RequestMapping(value = "/checkContract/checkNewNumber/{newNum}", method = RequestMethod.GET)
    public String checkNewName(@PathVariable("newNum") String newNum) {
        if(contractNumberBeforeEditing.equals(newNum)){
            return "false";
        }
        ContractDTO contractDTO = contractServiceImpl.getContractDTOByNumber(newNum).get(0);
        if(contractDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkContract/checkUser/{selectedUser}", method = RequestMethod.GET)
    public String checkUser(@PathVariable("selectedUser") String selectedUser) {

        if(userNameOfContractOwnerBeforeEditing.equals(selectedUser)){
            return "true";
        }

        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(selectedUser);
        if(userDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @PostMapping(value = "/checkContract/submitChanges/", produces = "application/json")
    public @ResponseBody
    String submitValues(Model model, CsrfToken token, Principal principal,
                                @RequestBody String exportArray) {

        JsonObject jsonObject = JsonParser.parseString(exportArray).getAsJsonObject();

        ContractDTO contractDTO = contractServiceImpl.getContractDTOByNumber(contractNumberBeforeEditing).get(0);

        String number = jsonObject.get("newNum").getAsString();
        String selectedUserLogin = jsonObject.get("selectedUserLogin").getAsString();;
        String tariff = jsonObject.get("selectedTariff").getAsString();
        Boolean isBlocked = jsonObject.get("isContractBlocked").getAsBoolean();

        JsonArray jsonArrayTest = jsonObject.get("selectedOptions").getAsJsonArray();

        contractDTO.setContractNumber(number);
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(selectedUserLogin);
        contractDTO.setUser(userDTO);
        TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(tariff);
        contractDTO.setTariff(tariffDTO);
        if(jsonArrayTest.size()!=0) {
            for (int i = 0; i < jsonArrayTest.size(); i++) {
                contractDTO.addOption(optionServiceImpl.getOptionDTOByNameOrNull(jsonArrayTest.get(i).getAsString()));
            }
        }
        contractDTO.setBlocked(isBlocked);
        contractServiceImpl.convertToEntityAndUpdate(contractDTO);

        log.info(contractDTO.getContractNumber() + "was successfully edited and updated.");

        return "true";
    }

    @ResponseBody
    @RequestMapping(value = "/checkContract/deleteContract/{contractNumber}", method = RequestMethod.GET)
    public String deleteContract(@PathVariable("contractNumber") String contractNumber) {
        ContractDTO contractDTO = contractServiceImpl.getContractDTOByNumberOrNull(contractNumber);
        if(contractDTO==null){
            return "false";
        }else{
            contractDTO.setActive(false);
            contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            return "true";
        }

    }
}
