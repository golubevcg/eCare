package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import eCare.services.api.OptionService;
import eCare.validator.OptionDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewOptionRegPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    private final OptionService optionServiceImpl;

    private final OptionDTOValidator optionDTOValidator;

    public NewOptionRegPageController(OptionService optionServiceImpl, OptionDTOValidator optionDTOValidator) {
        this.optionServiceImpl = optionServiceImpl;
        this.optionDTOValidator = optionDTOValidator;
    }

    @GetMapping(value = "/newOption")
    public String getUserRegistration(Model model) {
        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptionDTOs();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", new OptionDTO());
        return "newOptionRegPage";
    }

    @PostMapping(value = "/newOption")
    public String submitNewOption(@ModelAttribute("optionDTO") OptionDTO optionDTO,
                                  BindingResult optionDTOBindingResult,
                                  @RequestParam(required = false, name = "selectedObligatoryOptions") String[] selectedObligatoryOptions,
                                  @RequestParam(required = false, name = "selectedIncompatibleOptions") String[] selectedIncompatibleOptions) {

        optionDTOValidator.validate(optionDTO, optionDTOBindingResult);

        if (optionDTOBindingResult.hasErrors()) {
            return "newOptionRegPage";
        }

        if (selectedIncompatibleOptions != null) {
            Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedIncompatibleOptions.length; i++) {
                incompatibleOptionsSet.add(optionServiceImpl.getOptionDTOByNameOrNull(selectedIncompatibleOptions[i]));
            }
            optionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet);
        }

        if (selectedObligatoryOptions != null) {
            Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedObligatoryOptions.length; i++) {
                obligatoryOptionsSet.add(optionServiceImpl.getOptionDTOByNameOrNull(selectedObligatoryOptions[i]));
            }
            optionDTO.setObligatoryOptionsSet(obligatoryOptionsSet);
        }

        optionServiceImpl.convertToEntityAndSave(optionDTO);
        log.info("New option with name: " + optionDTO.getName() + " registered successfully.");

        return "workerOfficePage";
    }

    boolean foundedErrorDependency = false;

    @PostMapping(value = "/newOption/checkIncOptionDependenciesToPreventRecursion/")
    public @ResponseBody
    String checkIncOptionDependenciesToPreventRecursion(@RequestBody String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = optionServiceImpl.getOptionDTOByNameOrNull(lastSelectedValue);

        String errorOptionName = null;

        foundedErrorDependency = false;
        if (!jsonObject.get("selectedObligOptions").isJsonNull()) {

            JsonArray obligJsonArray = jsonObject.get("selectedObligOptions").getAsJsonArray();
            String[] selectedObligNames = new String[obligJsonArray.size()];

            for (int i = 0; i < selectedObligNames.length; i++) {
                String currentSelectedObligName = obligJsonArray.get(i).getAsString();
                OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(currentSelectedObligName);

                if (optionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
                    errorOptionName = optionDTO.getName();
                    return new Gson().toJson(errorOptionName);
                } else {
                    OptionDTO selectedObligOptionDTO = optionServiceImpl.getOptionDTOByNameOrNull(currentSelectedObligName);
                    for (OptionDTO entity : optionDTO.getObligatoryOptionsSet()) {
                        recursivlyCheckInObligDependecies(lastSelectedOptionDTO, selectedObligOptionDTO);
                        if (foundedErrorDependency) {
                            errorOptionName = entity.getName();
                            return new Gson().toJson(errorOptionName);
                        }
                    }
                }
            }

        }

        return "";
    }

    public boolean recursivlyCheckInObligDependecies(OptionDTO lastSelectedOptionDTO,
                                                     OptionDTO selectedObligOptionDTO) {
        if (selectedObligOptionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
            foundedErrorDependency = true;
            return false;
        } else {
            for (OptionDTO entity : selectedObligOptionDTO.getObligatoryOptionsSet()) {
                recursivlyCheckInObligDependecies(lastSelectedOptionDTO, entity);
            }
        }
        return true;
    }


    @PostMapping(value = "/newOption/checkOblOptionDependenciesToPreventRecursion/")
    public @ResponseBody
    String checkOblOptionDependenciesToPreventRecursion(@RequestBody String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = optionServiceImpl.getOptionDTOByNameOrNull(lastSelectedValue);

        if (!jsonObject.get("selectedIncOptions").isJsonNull()) {
            JsonArray incJsonArray = jsonObject.get("selectedIncOptions").getAsJsonArray();

            Set<OptionDTO> allObligatoryOptionsSet = lastSelectedOptionDTO.getObligatoryOptionsSet();

            for (OptionDTO entity: allObligatoryOptionsSet) {
                returnAllObligatoryOptions(allObligatoryOptionsSet, entity);
            }

            for (int i = 0; i < incJsonArray.size(); i++) {
                OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull( incJsonArray.get(i).getAsString() );
                if(allObligatoryOptionsSet.contains(optionDTO)){
                    return new Gson().toJson(optionDTO.getName());
                }
            }

        }
        return "";
    }

    private void returnAllObligatoryOptions(Set<OptionDTO> allObligatoryOptionsSet, OptionDTO optionDTO){
        Set<OptionDTO> obligatoryOptionsOfCurrentOptionDTO = optionDTO.getObligatoryOptionsSet();
        if(obligatoryOptionsOfCurrentOptionDTO.size()>0){
            for (OptionDTO entity: obligatoryOptionsOfCurrentOptionDTO) {
                if(!allObligatoryOptionsSet.contains(entity)){
                    allObligatoryOptionsSet.add(entity);
                    returnAllObligatoryOptions(allObligatoryOptionsSet, entity);
                }
            }
        }


    }
}
