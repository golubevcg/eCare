package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Option;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckContractController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @Autowired
    UserService userServiceImpl;

    @Autowired
    TariffService tariffServiceImpl;

    @Autowired
    OptionService optionServiceImpl;

    @Autowired
    ContractService contractServiceImpl;

    private String oldNumber;

    @GetMapping(value = "/checkContract/{contractId}")
    public String checkContractNumber(Model model,
                                      @PathVariable(name="contractId") String contractId){
        ContractDTO contractDTO = contractServiceImpl.getContractDTOById(Long.valueOf(contractId)).get(0);
        oldNumber = contractDTO.getContractNumber();
                List<TariffDTO> listOfTariffs = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("listOfTariffs", listOfTariffs);
        model.addAttribute("contractDTO", contractDTO);
        return "checkContract";
    }

    @ResponseBody
    @RequestMapping(value = "/checkContract/loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {
        Set<Option> optionList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (Option option: optionList) {
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

        if(oldNumber.equals(newNum)){
            return "false";
        }
        TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffnameOrNull(newNum);
        if(tariffDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }
}
