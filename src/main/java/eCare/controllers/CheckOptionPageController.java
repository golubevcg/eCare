package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.mq.MessageSender;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.apache.log4j.Logger;
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
    void setDependingOptions(CsrfToken token, @RequestBody String arrayOfArrays) {
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
    public String submitEditedOption(@PathVariable(name="optionName") String optionName,
                                     @ModelAttribute OptionDTO optionDTO,
                                     @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        optionServiceImpl.submitValuesFromController(optionName,optionDTO,
                obligatoryOptionsSet,incompatibleOptionsSet,blockConnectedContracts);

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
        return optionServiceImpl.getDependedOptionsJson(oldName);
    }
}