package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Option;
import eCare.model.enitity.User;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewContractController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    TariffService tariffServiceImpl;

    @GetMapping(value = "/newContract")
    public String getUserRegistration(Model model){

        List<TariffDTO> listOfTariffs = tariffServiceImpl.getActiveTariffs();

        model.addAttribute("listOfTariffs", listOfTariffs);
        model.addAttribute("contractDTO", new ContractDTO());
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
        List<UserDTO> listOfUsers = userServiceImpl.searchForUserBySecondName(term);
        List<String> secondAndFirstNameOfUserlist = new ArrayList<>();

        for (UserDTO user: listOfUsers) {
            secondAndFirstNameOfUserlist.add(user.getSecondname() + " " + user.getFirstname());
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(secondAndFirstNameOfUserlist);
    }

}
