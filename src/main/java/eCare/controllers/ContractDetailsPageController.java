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
public class ContractDetailsPageController {

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

    @GetMapping("/contractDetails/{contractID}")
    public String getClientOffice(Model model, CsrfToken token, Principal principal,
                                  @PathVariable(value = "contractID") String contractID) {

        currentUser = userServiceImpl.getUserDTOByLoginOrNull(principal.getName());
        currentContract = contractServiceImpl.getContractDTOById(Long.parseLong(contractID)).get(0);

        model.addAttribute("contractNumber", currentContract.getContractNumber());
        model.addAttribute("firstAndSecondNames", currentUser.getFirstname() + " " + currentUser.getSecondname());

        TariffDTO tariffDTO = currentContract.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " $ / month");

        Set<OptionDTO> availableOptions = tariffDTO.getSetOfOptions();
        ArrayList<OptionDTO> sortedListOfAvailableOptions = new ArrayList<>();
        sortedListOfAvailableOptions.addAll(availableOptions);
        Collections.sort(sortedListOfAvailableOptions);

        Set<OptionDTO> connectedOptions = currentContract.getSetOfOptions();
        ArrayList<OptionDTO> sortedListOfConnectedOptions = new ArrayList<>();
        sortedListOfConnectedOptions.addAll(connectedOptions);
        Collections.sort(sortedListOfConnectedOptions);
        LinkedHashSet<OptionDTO> optionDTOLinkedHashSet = new LinkedHashSet<>();
        optionDTOLinkedHashSet.addAll(sortedListOfConnectedOptions);


        Map<OptionDTO, Boolean> enabledOptionsDTOMap = new LinkedHashMap<>();

        for (OptionDTO availableOption : sortedListOfAvailableOptions) {
            boolean isOptionInContract = false;

            for (OptionDTO connectedOption : sortedListOfConnectedOptions) {
                if (availableOption.getOption_id() == (connectedOption.getOption_id()))
                    isOptionInContract = true;
            }

            enabledOptionsDTOMap.put(availableOption, isOptionInContract);
        }

        model.addAttribute("enabledOptionsDTOMap", enabledOptionsDTOMap);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        model.addAttribute("connectedOptions", optionDTOLinkedHashSet);
        model.addAttribute("isBlocked", currentContract.isBlocked());

        return "contractDetailsPage";
    }

    @PostMapping(value = "/contractDetails/getTariffOptions", produces = "application/json")
    public @ResponseBody
    String postClientOffice(@RequestBody String selectedTariffName) {

        TariffDTO tariffDTO = tariffServiceImpl
                .getTariffDTOByTariffnameOrNull(selectedTariffName
                        .replace("\"", ""));

        Set<OptionDTO> setOfOptions = new HashSet<>();
        if(tariffDTO!=null){
            setOfOptions = tariffDTO.getSetOfOptions();
        }


        ArrayList<OptionDTO> sortedListOfOptions = new ArrayList<>();
        sortedListOfOptions.addAll(setOfOptions);
        Collections.sort(sortedListOfOptions);


        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(sortedListOfOptions);
    }

    @PostMapping(value = "/contractDetails/submitvalues/{contractNumber}", produces = "application/json")
    public @ResponseBody
    String updateValuesOnInformationFromView(@RequestBody String exportObject, @PathVariable String contractNumber) {
        JsonObject obj = JsonParser.parseString(exportObject).getAsJsonObject();
        Boolean blockNumberCheckBox = obj.get("blockNumberCheckBox").getAsBoolean();

        String tariffSelectedCheckboxes = obj.get("tariffSelectedCheckboxes").getAsString();
        JsonArray jsonArray = obj.get("optionsSelectedCheckboxes").getAsJsonArray();

        Set<OptionDTO> setOfOptions = new HashSet<>();

        if (jsonArray.size() != 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                setOfOptions.add(optionServiceImpl.getOptionDTOById(jsonArray.get(i).getAsLong()));
            }
        }
        currentContract.setSetOfOptions(setOfOptions);

        if (currentContract.isBlocked() != blockNumberCheckBox) {
            currentContract.setBlocked(blockNumberCheckBox);
        }

        if (!currentContract.getTariff().getName().equals(tariffSelectedCheckboxes)) {
            TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffnameOrNull(tariffSelectedCheckboxes);
            if(tariffDTO!=null){
                currentContract.setTariff(tariffDTO);
            }
        }

        JsonArray jsonArrayLockedOptions = obj.get("lockedOptionsArray").getAsJsonArray();

        if(jsonArrayLockedOptions.size()!=0){
            for (int i = 0; i < jsonArrayLockedOptions.size(); i++) {
                currentContract.addLockedOption(optionServiceImpl.getOptionDTOById(
                        jsonArrayLockedOptions.get(i).getAsLong() ) );
            }
        }

        contractServiceImpl.updateConvertDTO(currentContract);

        return "true";
    }

    @PostMapping(value = "/contractDetails/loadDependedOptions/{selectedOption}", produces = "application/json")
    public @ResponseBody
    String getDependingOptions(Model model, CsrfToken token, Principal principal,
                               @PathVariable(value = "selectedOption") String selectedOptionId,
                               @RequestBody String[] selectedTariffName) {
        OptionDTO changedOptionDTO = optionServiceImpl.getOptionDTOById( Long.valueOf(selectedOptionId) );
        TariffDTO selectedTariffDTO = tariffServiceImpl.getTariffDTOByTariffnameOrNull(selectedTariffName[0]);
        Boolean isChecked = Boolean.valueOf(selectedTariffName[1]);

        Set<OptionDTO> notSelectedOptionsSet = selectedTariffDTO.getSetOfOptions();
        notSelectedOptionsSet.remove(changedOptionDTO);
        Set<String> incompatibleOptionIds = new HashSet<>();
        Set<String> obligatoryOptionIds = new HashSet<>();

        this.cascadeCheckOptionDependencies(changedOptionDTO, incompatibleOptionIds,
                                            obligatoryOptionIds, notSelectedOptionsSet, isChecked);

        Set<String>[] array = new HashSet[2];
        array[0]=incompatibleOptionIds;
        array[1]=obligatoryOptionIds;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(array);
    }

    public void cascadeCheckOptionDependencies(OptionDTO currentOption,
                                               Set<String> incompatibleOptionIds,
                                               Set<String> obligatoryOptionIds,
                                               Set<OptionDTO> notSelectedOptionIds,
                                               Boolean isChecked){
        Set<OptionDTO> incompatibleOptionsSet = currentOption.getIncompatibleOptionsSet();
        Set<OptionDTO> obligatoryOptionsSet = currentOption.getObligatoryOptionsSet();

        for (OptionDTO option: incompatibleOptionsSet) {
            if( notSelectedOptionIds.contains( option )){
                incompatibleOptionIds.add( String.valueOf(option.getOption_id()) );
            }
        }

        for (OptionDTO option: obligatoryOptionsSet) {
            if( notSelectedOptionIds.contains( option )){
                obligatoryOptionIds.add( String.valueOf(option.getOption_id()) );

                if(isChecked){
                    this.cascadeCheckOptionDependencies(option,
                            incompatibleOptionIds,
                            obligatoryOptionIds,
                            notSelectedOptionIds,
                            isChecked);
                }

            }
        }

    }

    @GetMapping(value = "/contractDetails/getLockedOptions", produces = "application/json")
    public @ResponseBody
    String getLockedOptions(Model model, CsrfToken token, Principal principal) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(currentContract.getSetOfBlockedOptions());
    }



}