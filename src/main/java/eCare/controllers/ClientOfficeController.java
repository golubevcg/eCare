package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.OptionServiceImpl;
import eCare.services.impl.TariffServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class ClientOfficeController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    TariffServiceImpl tariffServiceImpl;

    @Autowired
    ContractServiceImpl contractServiceImpl;

    @Autowired
    OptionServiceImpl optionServiceImpl;

    UserDTO currentUser;

    ContractDTO currentContract;

    @GetMapping("/clientOffice/{contractID}")
    public String getClientOffice(Model model, CsrfToken token, Principal principal,
                             @PathVariable(value="contractID") String contractID) {
        currentUser = userServiceImpl.getUserDTOByLogin(principal.getName());
        currentContract = contractServiceImpl.getContractDTOById(Long.parseLong(contractID)).get(0);

        model.addAttribute("contractNumber", currentContract.getContractNumber());
        model.addAttribute("firstAndSecondNames", currentUser.getFirstname() + " " + currentUser.getSecondname());

        TariffDTO tariffDTO = currentContract.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " руб./мес.");

        Set<OptionDTO> availableOptions = tariffDTO.getSetOfOptions();
        Set<OptionDTO> connectedOptions = currentContract.getListOfOptions();
        Map<OptionDTO, Boolean> enabledOptionsDTOMap = new LinkedHashMap<>();

        for (OptionDTO availableOption: availableOptions) {
            boolean isOptionInContract = false;
            for (OptionDTO connectedOption: connectedOptions) {
                if(availableOption.equals(connectedOption))
                isOptionInContract = true;
            }
            enabledOptionsDTOMap.put(availableOption, isOptionInContract);
        }

        model.addAttribute("enabledOptionsDTOMap", enabledOptionsDTOMap);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        model.addAttribute("connectedOptions", connectedOptions);

        return "clientOffice";
    }

        @PostMapping(value="/clientOffice/getTariffOptions", produces = "application/json")
    public @ResponseBody String postClientOffice(@RequestBody String selectedTariffName){

        Set<OptionDTO> setOfOptions =
                tariffServiceImpl.getTariffDTOByTariffname(selectedTariffName.replace("\"", "" ))
                        .getSetOfOptions();

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


        System.out.println(gson.toJson(setOfOptions));
        return gson.toJson(setOfOptions);
    }

    @PostMapping(value="/clientOffice/submitvalues", produces = "application/json")
    public @ResponseBody void updateValuesOnInformationFromView(@RequestBody String exportObject) {

        JsonObject obj = JsonParser.parseString(exportObject).getAsJsonObject();
        Boolean blockNumberCheckBox = obj.get("blockNumberCheckBox").getAsBoolean();

        String tariffSelectedCheckboxes = obj.get("tariffSelectedCheckboxes").getAsString();

        JsonArray jsonArray = obj.get("optionsSelectedCheckboxes").getAsJsonArray();
        jsonArray.get(0).getAsString();

        List<String> optionNamesArray = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            optionNamesArray.add(jsonArray.get(i).getAsString());
        }

        if(currentContract.isBlocked()!=blockNumberCheckBox){
            currentContract.setBlocked(blockNumberCheckBox);
        }

        if(!currentContract.getTariff().getName().equals(tariffSelectedCheckboxes)){
            currentContract.setTariff( tariffServiceImpl.getTariffDTOByTariffname(tariffSelectedCheckboxes) );
        }

        Set<OptionDTO> currentOptions = currentContract.getListOfOptions();

        for (OptionDTO currentOption: currentOptions) {

            for (int j = 0; j < optionNamesArray.size(); j++) {
                if(currentOption.getName() == optionNamesArray.get(j)){
                    optionNamesArray.remove(optionNamesArray.get(j));
                }
            }

        }

        if(!optionNamesArray.isEmpty()){
            for (int i = 0; i < optionNamesArray.size(); i++) {
                currentContract.addOption(optionServiceImpl.getOptionDTOByName(optionNamesArray.get(i)));
            }
        }

        contractServiceImpl.updateConvertDTO(currentContract);

    }

}