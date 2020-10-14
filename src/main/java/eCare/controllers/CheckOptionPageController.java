package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckOptionPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    @Autowired
    private OptionService optionServiceImpl;

    @Autowired
    private ContractService contractServiceImpl;

    private String oldName;

    @GetMapping(value = "/checkOption/{optionName}")
    public String getOptionToCheck(Model model, @PathVariable(name="optionName") String optionName) {
        oldName = optionName;
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(optionName);

        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", optionDTO);
        return "checkOption";
    }

    private Set<OptionDTO> obligatoryOptionsSetFin = new HashSet<>();
    private Set<OptionDTO> incompatibleOptionsSetFin = new HashSet<>();


    @PostMapping(value = "/checkOption/submitArraysValues", produces = "application/json")
    public @ResponseBody
    void getDependingOptions(Model model, CsrfToken token, Principal principal,
                               @RequestBody String arrayOfArrays) {
        JsonArray jsonArray = JsonParser.parseString(arrayOfArrays).getAsJsonArray();

        JsonArray obligatoryOptionsJsonArray = jsonArray.get(0).getAsJsonArray();
        if(obligatoryOptionsJsonArray.size()!=0) {
            for (int i = 0; i < obligatoryOptionsJsonArray.size(); i++) {
                JsonObject jsonObject = obligatoryOptionsJsonArray.get(i).getAsJsonObject();
                obligatoryOptionsSetFin.add(optionServiceImpl.getOptionDTOByNameOrNull(jsonObject.get("id").getAsString()));
            }
        }

        JsonArray incompatibleOptionsJsonArray = jsonArray.get(1).getAsJsonArray();
        if(incompatibleOptionsJsonArray.size()!=0){
            for (int i = 0; i <incompatibleOptionsJsonArray.size() ; i++) {
                JsonObject jsonObject = incompatibleOptionsJsonArray.get(i).getAsJsonObject();
                incompatibleOptionsSetFin.add( optionServiceImpl.getOptionDTOByNameOrNull( jsonObject.get("id").getAsString()) );
            }
        }


    }

    @PostMapping(value = "/checkOption/{optionName}")
    public String submitEditedOptions(Model model, @PathVariable(name="optionName") String optionName,
                                      @ModelAttribute OptionDTO optionDTO,
                                      BindingResult optionDTOBindingResult,
                                      @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        OptionDTO optionDTO1 = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        optionDTO1.setName( optionDTO.getName() );
        optionDTO1.setPrice( optionDTO.getPrice() );
        optionDTO1.setConnectionCost( optionDTO.getConnectionCost() );
        optionDTO1.setShortDiscription( optionDTO.getShortDiscription() );

        if(incompatibleOptionsSetFin!=null) {
            optionDTO1.setIncompatibleOptionsSet(incompatibleOptionsSetFin);
        }

        if(obligatoryOptionsSetFin!=null) {
            optionDTO1.setObligatoryOptionsSet(obligatoryOptionsSetFin);
        }

        if( blockConnectedContracts!=null){
            Set<ContractDTO> contractDTOS = optionDTO1.getContractsOptions();
            for (ContractDTO contractDTO: contractDTOS) {
                contractDTO.setBlocked(true);
                contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            }
            optionServiceImpl.convertToEntityAndUpdate(optionDTO1);
        }else {
            optionServiceImpl.convertToEntityAndUpdate(optionDTO1);
        }

        return "/workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/checkOption/checkNewName/{newName}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("newName") String newName) {
        if(oldName.equals(newName)){
            return "false";
        }
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(newName);
        if(optionDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkOption/deleteOption/{optionName}", method = RequestMethod.GET)
    public String deleteOption(@PathVariable("optionName") String optionName) {
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        optionDTO.setActive(false);
        optionServiceImpl.convertToEntityAndUpdate(optionDTO);
        return "true";
    }

    @ResponseBody
    @RequestMapping(value = "/checkOption/getIncompatibleAndObligatoryOptions/{oldName}", method = RequestMethod.GET)
    public String getDependedOptions(@PathVariable("oldName") String oldName) {
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(oldName);
        Set<OptionDTO> incompatibleOptionsSet = optionDTO.getIncompatibleOptionsSet();
        Set<String> incompatibleOptionNamesSet = new HashSet<>();
        for (OptionDTO option: incompatibleOptionsSet) {
            incompatibleOptionNamesSet.add(option.getName());
        }

        Set<OptionDTO> obligatoryOptionsSet = optionDTO.getObligatoryOptionsSet();
        Set<String> obligatoryOptionNamesSet = new HashSet<>();
        for (OptionDTO option: obligatoryOptionsSet) {
            obligatoryOptionNamesSet.add(option.getName());
        }

        Set<String>[] array = new HashSet[2];
        array[0]=incompatibleOptionNamesSet;
        array[1]=obligatoryOptionNamesSet;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(array);
    }
}