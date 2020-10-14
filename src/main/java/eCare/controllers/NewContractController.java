package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.ContractDTO;
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
public class NewContractController {

    static final Logger log = Logger.getLogger(MainPageController.class);

    @Autowired
    UserService userServiceImpl;

    @Autowired
    TariffService tariffServiceImpl;

    @Autowired
    OptionService optionServiceImpl;

    @Autowired
    ContractService contractServiceImpl;

    @GetMapping(value = "/newContract")
    public String getNewContract(Model model){

        List<TariffDTO> listOfTariffs = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("selectedUserError", "");
        model.addAttribute("listOfTariffs", listOfTariffs);
        model.addAttribute("contractDTO", new ContractDTO());
        model.addAttribute("phoneNumberPatternError", "");
        model.addAttribute("phoneNumberEmptyError", "");
        return "newContract";
    }

    @ResponseBody
    @RequestMapping(value = "/newContract/loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {

        Set<Option> optionList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (Option option: optionList) {
            optionNamesSet.add(option.getName());
        }

        return new Gson().toJson(optionNamesSet);
    }

    @GetMapping(value = "/newContract/getUsersList", produces = "application/json")
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

    @PostMapping(value = "/newContract")
    public String saveUser(Model model, CsrfToken csrfToken,
                           @ModelAttribute ContractDTO contractDTO,
                           BindingResult bindingResult,
                           @RequestParam(required = false, name="contractNumber") String contractNumber,
                           @RequestParam(required = false, name="usersList") String selectedLogin,
                           @RequestParam(required = false, name="selectedTariff") String selectedTariff,
                           @RequestParam(required = false, name="selectedOptions") String[] selectedOptions){


        if(contractNumber.isEmpty()){
            bindingResult.addError(new ObjectError("phoneNumberEmptyError", "This field is required."));
            model.addAttribute("phoneNumberEmptyError", "This field is required.");
        }else{
            if(!contractNumber.matches("[+]*([0-9]{11})")){
                bindingResult.addError(new ObjectError("phoneNumberPatterError", "Phone number should look like this: +7XXXXXXXXXX."));
                model.addAttribute("phoneNumberPatternError", "Phone number should look like this: +7XXXXXXXXXX.");
            }
        }

        if(userServiceImpl.getUserByLogin(selectedLogin).size()==0){
            bindingResult.addError(new ObjectError("userList", "Please select existing user from drop-down list"));
            model.addAttribute("selectedUserError", "Please select existing user.");
        }

        if(bindingResult.hasErrors()){
            List<TariffDTO> listOfTariffs = tariffServiceImpl.getActiveTariffs();
            model.addAttribute("listOfTariffs", listOfTariffs);
            model.addAttribute("contractDTO", new ContractDTO());
            return "newContract";
        }

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber(contractNumber);
        contractDTO1.setUser(userServiceImpl.getUserDTOByLoginOrNull(selectedLogin));
        contractDTO1.setTariff(tariffServiceImpl.getTariffDTOByTariffnameOrNull(selectedTariff));
        if(selectedOptions!=null){
            for (int i = 0; i < selectedOptions.length; i++) {
                contractDTO1.addOption(optionServiceImpl.getOptionDTOByNameOrNull(selectedOptions[i]));
            }
        }

        contractServiceImpl.convertToEntityAndSave(contractDTO1);
        log.info("New contract was successfully registered.");

        return "workerOffice";
    }
}
