package ecare.controllers;

import com.google.gson.*;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Controller for page, in which checking or editing tariffs occur.
 */
@Controller
public class CheckTariffPageController {

    private final OptionService optionServiceImpl;

    private final TariffService tariffService;

    private String tariffNameBeforeEditing;

    Set<OptionDTO> availableOptions = new HashSet<>();

    public CheckTariffPageController(OptionService optionServiceImpl, TariffService tariffService) {
        this.optionServiceImpl = optionServiceImpl;
        this.tariffService = tariffService;
    }

    @GetMapping(value = "/checkTariff/{tariffName}")
    public String getCheckTariffPage(Model model, @PathVariable(name="tariffName")String tariffName){

        tariffNameBeforeEditing = tariffName;
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffNameOrNull(tariffName);

        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptionDTOs();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", tariffDTO);
        return "checkTariffPage";
    }

    @ResponseBody
    @GetMapping(value = "/checkTariff/checkNewName/{newName}")
    public boolean checkNewName(@PathVariable("newName") String newName) {
        if(tariffNameBeforeEditing.equals(newName)){
            return false;
        }

        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffNameOrNull(newName);

        return tariffDTO!=null;
    }

    @ResponseBody
    @GetMapping(value = "/checkTariff/getAvailableOptions/{oldName}")
    public String getAvailableOptions(@PathVariable("oldName") String oldName) {
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffNameOrNull(oldName);
        Set<OptionDTO> optionsSet = tariffDTO.getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (OptionDTO option: optionsSet) {
            optionNamesSet.add(option.getName());
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(optionNamesSet);
    }


    @PostMapping(value = "/checkTariff/submitArrayAvailableOption/", produces = "application/json")
    public @ResponseBody
    void submitAvailableOptions(Model model, CsrfToken token, Principal principal,
                             @RequestBody String selectedAvailableOptions) {
        JsonArray jsonArray = JsonParser.parseString(selectedAvailableOptions).getAsJsonArray();

        if(jsonArray.size()!=0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                availableOptions.add( optionServiceImpl.getOptionDTOByNameOrNull(jsonObject.get("id").getAsString()));
            }
        }

    }

    @PostMapping(value = "/checkTariff/{tariffName}")
    public String submitEditedTariff(Model model, @PathVariable(name="tariffName") String tariffName,
                                     @ModelAttribute TariffDTO tariffDTO,
                                     @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        tariffService.submitValuesFromController(blockConnectedContracts, tariffDTO,
                                                tariffNameBeforeEditing, availableOptions);
        return "workerOfficePage";
    }

    @ResponseBody
    @GetMapping(value = "/checkTariff/deleteTariff/{tariffName}")
    public boolean deleteOption(@PathVariable("tariffName") String tariffName) {
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffNameOrNull(tariffName);
        tariffDTO.setActive(false);
        tariffService.convertToEntityAndUpdate(tariffDTO);
        return true;
    }


}
