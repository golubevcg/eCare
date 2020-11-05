package ecare.controllers;

import com.google.gson.*;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.Option;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for page, in which checking or editing contracts occur.
 */

@Controller
public class CheckContractPageController {

    static final Logger log = Logger.getLogger(CheckContractPageController.class);

    final
    UserService userServiceImpl;

    final
    TariffService tariffServiceImpl;

    final
    OptionService optionServiceImpl;

    final
    ContractService contractServiceImpl;

    private String contractNumberBeforeEditing;
    private String userNameOfContractOwnerBeforeEditing;

    public CheckContractPageController(UserService userServiceImpl, TariffService tariffServiceImpl, OptionService optionServiceImpl, ContractService contractServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.tariffServiceImpl = tariffServiceImpl;
        this.optionServiceImpl = optionServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
    }

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
    String submitValues(CsrfToken token, @RequestBody String exportArray) {

        if( contractServiceImpl.submitValuesFromController(exportArray, contractNumberBeforeEditing) ){
            return "true";
        }else{
            return"false";
        }

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