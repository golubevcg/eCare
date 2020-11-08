package ecare.controllers;

import ecare.model.dto.OptionDTO;
import ecare.services.api.OptionService;
import ecare.validator.OptionDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
public class NewOptionRegPageController {

    static final Logger log = Logger.getLogger(NewOptionRegPageController.class);

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

    AtomicBoolean foundedErrorDependency = new AtomicBoolean(false);

    @PostMapping(value = "/newOption/checkIncOptionDependenciesToPreventImpossibleDependency/")
    public @ResponseBody
    String checkIncOptionDependenciesToPreventImpossibleDependency(@RequestBody String expJson) {
        return optionServiceImpl.checkIncOptionDependenciesToPreventImpossibleDependency(expJson,foundedErrorDependency);
    }

    @PostMapping(value = "/newOption/checkOblOptionDependenciesToPreventImpossibleDependency/")
    public @ResponseBody
    String checkOblOptionDependenciesToImpossibleDependency(@RequestBody String expJson) {
        return optionServiceImpl.checkOblOptionDependenciesToPreventImpossibleDependency(expJson);
    }



}
