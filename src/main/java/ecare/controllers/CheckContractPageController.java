package ecare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecare.model.dto.*;
import ecare.model.entity.Option;
import ecare.services.api.*;
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
    @GetMapping(value = "/checkContract/loadOptionByTariff/{selectedTariff}")
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
    @GetMapping(value = "/checkContract/getAddionalOptions/{oldNumber}")
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
    @GetMapping(value = "/checkContract/checkNewNumber/{newNum}")
    public boolean checkNewName(@PathVariable("newNum") String newNum) {
        if(newNum.equals(contractNumberBeforeEditing)){
            return false;
        }
        List<ContractDTO> contractDTOList = contractServiceImpl.getContractDTOByNumber(newNum);

        return !contractDTOList.isEmpty();
    }

    @ResponseBody
    @GetMapping(value = "/checkContract/checkUser/{selectedUser}")
    public boolean checkUser(@PathVariable("selectedUser") String selectedUser) {

        if(selectedUser.equals(userNameOfContractOwnerBeforeEditing)){
            return true;
        }

        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(selectedUser);

        return userDTO!=null;
    }

    @PostMapping(value = "/checkContract/submitChanges/", produces = "application/json")
    public @ResponseBody
    boolean submitValues(@RequestBody String exportArray) {
        return contractServiceImpl
                .submitValuesFromController(exportArray, contractNumberBeforeEditing);
    }

    @ResponseBody
    @GetMapping(value = "/checkContract/deleteContract/{contractNumber}")
    public boolean deleteContract(@PathVariable("contractNumber") String contractNumber) {
        ContractDTO contractDTO = contractServiceImpl.getContractDTOByNumberOrNull(contractNumber);
        if(contractDTO==null){
            return false;
        }else{
            contractDTO.setActive(false);
            contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            return true;
        }
    }

}
