package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.validator.TariffDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckTariffPageController {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    @Autowired
    private OptionService optionServiceImpl;

    @Autowired
    private TariffService tariffService;

    @Autowired
    private ContractService contractServiceImpl;

    @Autowired
    private TariffDTOValidator tariffDTOValidator;

    private String oldName;

    @GetMapping(value = "/checkTariff/{tariffName}")
    public String getTariff(Model model, @PathVariable(name="tariffName")String tariffName){

        oldName = tariffName;
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(tariffName);

        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("tariffDTO", tariffDTO);
        return "checkTariffPage";
    }

    @ResponseBody
    @RequestMapping(value = "/checkTariff/checkNewName/{newName}", method = RequestMethod.GET)
    public String checkNewName(@PathVariable("newName") String newName) {
        if(oldName.equals(newName)){
            return "false";
        }

        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(newName);
        if(tariffDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkTariff/getAvailableOptions/{oldName}", method = RequestMethod.GET)
    public String getAvailableOptions(@PathVariable("oldName") String oldName) {
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(oldName);
        Set<OptionDTO> optionsSet = tariffDTO.getSetOfOptions();
        Set<String> optionNamesSet = new HashSet<>();
        for (OptionDTO option: optionsSet) {
            optionNamesSet.add(option.getName());
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(optionNamesSet);
    }

    Set<OptionDTO> availableOptions = new HashSet<>();

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
    public String submitEditedOptions(Model model, @PathVariable(name="tariffName") String tariffName,
                                      @ModelAttribute TariffDTO tariffDTO,
                                      @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        TariffDTO tariffDTOtoUpdate = tariffService.getTariffDTOByTariffnameOrNull(oldName);
        tariffDTOtoUpdate.setName(tariffDTO.getName());
        tariffDTOtoUpdate.setPrice(tariffDTO.getPrice());
        tariffDTOtoUpdate.setShortDiscription(tariffDTO.getShortDiscription());

        if(availableOptions!=null) {
            tariffDTOtoUpdate.setSetOfOptions(availableOptions);
        }

        if( blockConnectedContracts!=null){
            Set<ContractDTO> contractDTOS = tariffDTOtoUpdate.getSetOfContracts();
            for (ContractDTO contractDTO: contractDTOS) {
                contractDTO.setBlocked(true);
                contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            }
            tariffService.convertToEntityAndUpdate(tariffDTOtoUpdate);
        }else {
            tariffService.convertToEntityAndUpdate(tariffDTOtoUpdate);
        }
        return "workerOfficePage";
    }

    @ResponseBody
    @RequestMapping(value = "/checkTariff/deleteTariff/{tariffName}", method = RequestMethod.GET)
    public String deleteOption(@PathVariable("tariffName") String tariffName) {
        TariffDTO tariffDTO = tariffService.getTariffDTOByTariffnameOrNull(tariffName);
        tariffDTO.setActive(false);
        tariffService.convertToEntityAndUpdate(tariffDTO);
        return "true";
    }


}
