package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.mq.MessageSender;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller for page, in which checking or editing options occur.
 */

@Controller
public class CheckOptionPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    private final OptionService optionServiceImpl;

    private final ContractService contractServiceImpl;

    private final MessageSender messageSender;

    private String optionNameBeforeEditing;

    public CheckOptionPageController(OptionService optionServiceImpl, ContractService contractServiceImpl, MessageSender messageSender) {
        this.optionServiceImpl = optionServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
        this.messageSender = messageSender;
    }

    @GetMapping(value = "/checkOption/{optionName}")
    public String getCheckOptionPage(Model model, @PathVariable(name="optionName") String optionName) {
        optionNameBeforeEditing = optionName;
        OptionDTO optionDTOBeforeEditing = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", optionDTOBeforeEditing);
        return "checkOptionPage";
    }

    private Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
    private Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();

    /**
     * here we submit incompatible and obligatory options set's, it will be used in submitEditedOptions method
     */
    @PostMapping(value = "/checkOption/submitArraysValues", produces = "application/json")
    public @ResponseBody
    void getDependingOptions(Model model, CsrfToken token,
                               @RequestBody String arrayOfArrays) {
        JsonArray jsonArray = JsonParser.parseString(arrayOfArrays).getAsJsonArray();

        JsonArray obligatoryOptionsJsonArray = jsonArray.get(0).getAsJsonArray();
        if(obligatoryOptionsJsonArray.size()!=0) {
            for (int i = 0; i < obligatoryOptionsJsonArray.size(); i++) {
                JsonObject jsonObject = obligatoryOptionsJsonArray.get(i).getAsJsonObject();
                obligatoryOptionsSet.add(optionServiceImpl.getOptionDTOByNameOrNull(jsonObject.get("id").getAsString()));
            }
        }

        JsonArray incompatibleOptionsJsonArray = jsonArray.get(1).getAsJsonArray();
        if(incompatibleOptionsJsonArray.size()!=0){
            for (int i = 0; i <incompatibleOptionsJsonArray.size() ; i++) {
                JsonObject jsonObject = incompatibleOptionsJsonArray.get(i).getAsJsonObject();
                incompatibleOptionsSet.add( optionServiceImpl.getOptionDTOByNameOrNull( jsonObject.get("id").getAsString()) );
            }
        }


    }


    @PostMapping(value = "/checkOption/{optionName}")
    public String submitEditedOption(Model model, @PathVariable(name="optionName") String optionName,
                                     @ModelAttribute OptionDTO optionDTO,
                                     BindingResult optionDTOBindingResult,
                                     @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        OptionDTO optionDTO1 = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        optionDTO1.setName( optionDTO.getName() );
        optionDTO1.setPrice( optionDTO.getPrice() );
        optionDTO1.setConnectionCost( optionDTO.getConnectionCost() );
        optionDTO1.setShortDescription( optionDTO.getShortDescription() );

        if(incompatibleOptionsSet !=null) {
            optionDTO1.setIncompatibleOptionsSet(incompatibleOptionsSet);
        }

        if(obligatoryOptionsSet !=null) {
            optionDTO1.setObligatoryOptionsSet(obligatoryOptionsSet);
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

        messageSender.sendMessage("update");
        log.info(optionDTO1.getName() + " was successfully edited and updated.");
        return "workerOfficePage";
    }

    @ResponseBody
    @RequestMapping(value = "/checkOption/checkNewName/{newName}", method = RequestMethod.GET)
    public String newNameValidationCheckInDB(@PathVariable("newName") String newName) {
        if(optionNameBeforeEditing.equals(newName)){
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