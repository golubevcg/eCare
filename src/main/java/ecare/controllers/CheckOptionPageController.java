package ecare.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ecare.model.dto.OptionDTO;
import ecare.services.api.OptionService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller for page, in which checking or editing options occur.
 */

@Controller
public class CheckOptionPageController {

    private final OptionService optionServiceImpl;


    private String optionNameBeforeEditing;

    public CheckOptionPageController(OptionService optionServiceImpl) {
        this.optionServiceImpl = optionServiceImpl;
    }

    @GetMapping(value = "/checkOption/{optionName}")
    public String getCheckOptionPage(Model model, @PathVariable(name="optionName") String optionName) {
        optionNameBeforeEditing = optionName;
        OptionDTO optionDTOBeforeEditing = optionServiceImpl.getOptionDTOByNameOrNull(optionName);

        Set<OptionDTO> setOfAllActiveOptions = new HashSet<>(optionServiceImpl.getActiveOptionDTOs());
        setOfAllActiveOptions.remove(optionDTOBeforeEditing);

        model.addAttribute("listOfActiveOptions", setOfAllActiveOptions);
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
    void setDependingOptions(@RequestBody String arrayOfArrays) {
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
                incompatibleOptionsSet.add( optionServiceImpl
                        .getOptionDTOByNameOrNull( jsonObject.get("id").getAsString()) );
            }
        }

    }


    @PostMapping(value = "/checkOption/{optionName}")
    public String submitEditedOption(@PathVariable(name="optionName") String optionName,
                                     @ModelAttribute OptionDTO optionDTO,
                                     @RequestParam(required=false , name = "blockConnectedContracts")
                                                 String blockConnectedContracts){

        optionServiceImpl.submitValuesFromController(optionName,optionDTO,
                obligatoryOptionsSet,incompatibleOptionsSet,blockConnectedContracts);

        return "workerOfficePage";
    }

    @ResponseBody
    @GetMapping(value = "/checkOption/checkNewName/{newName}")
    public boolean newNameValidationCheckInDB(@PathVariable("newName") String newName) {
        if(optionNameBeforeEditing!=null && optionNameBeforeEditing.equals(newName)){
            return false;
        }
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(newName);

        return optionDTO!=null;
    }

    @ResponseBody
    @GetMapping(value = "/checkOption/deleteOption/{optionName}")
    public boolean deleteOption(@PathVariable("optionName") String optionName) {
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        optionDTO.setActive(false);
        optionServiceImpl.convertToEntityAndUpdate(optionDTO);
        return true;
    }

    @ResponseBody
    @GetMapping(value = "/checkOption/getIncompatibleAndObligatoryOptions/{oldName}")
    public String getDependedOptions(@PathVariable("oldName") String oldName) {
        return optionServiceImpl.getDependedOptionsJson(oldName);
    }

    @PostMapping(value = "/checkOption/checkIncOptionDependenciesToPreventRecursion/")
    public @ResponseBody
    String checkIncOptionDependenciesToPreventRecursion(@RequestBody String expJson) {
        return optionServiceImpl.checkIncOptionDependenciesToPreventRecursion(expJson);
    }

    @PostMapping(value = "/checkOption/checkOblOptionDependenciesToPreventRecursion/")
    public @ResponseBody
    String checkOblOptionDependenciesToPreventRecursion(@RequestBody String expJson) {
        return optionServiceImpl.checkOblOptionDependenciesToPreventRecursion(expJson);
    }
}