package eCare.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eCare.model.dto.OptionDTO;
import eCare.services.impl.OptionServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewOptionController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @GetMapping(value = "/newOption")
    public String getUserRegistration(Model model){
        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        return "newOption";
    }

    @PostMapping(value = "/newOption/submitvalues", produces = "application/json")
    public @ResponseBody
    String updateValuesOnInformationFromView(@RequestBody String exportObject) {
        JsonObject obj = JsonParser.parseString(exportObject).getAsJsonObject();

        String optionName = obj.get("optionName").getAsString();
        String connectionCost = obj.get("connectionCost").getAsString();
        String price = obj.get("price").getAsString();
        String shortDiscription = obj.get("shortDiscription").getAsString();

        JsonArray jsonArrayObligatoryOptions = obj.get("oblgatoryOptions").getAsJsonArray();
        Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
        if (jsonArrayObligatoryOptions.size() != 0) {
            for (int i = 0; i < jsonArrayObligatoryOptions.size(); i++) {
                obligatoryOptionsSet.add(optionServiceImpl.getOptionDTOByName(jsonArrayObligatoryOptions.get(i).getAsString()));
            }
        }

        JsonArray jsonArrayIncompatibleOptions = obj.get("incompatibleOptions").getAsJsonArray();
        Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
        if (jsonArrayIncompatibleOptions.size() != 0) {
            for (int i = 0; i < jsonArrayIncompatibleOptions.size(); i++) {
                incompatibleOptionsSet.add(optionServiceImpl.getOptionDTOByName(jsonArrayIncompatibleOptions.get(i).getAsString()));
            }
        }

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(optionName);
        optionDTO.setConnectionCost(Integer.valueOf(connectionCost));
        optionDTO.setPrice(Integer.valueOf(price));
        optionDTO.setShortDiscription(shortDiscription);
        optionDTO.setObligatoryOptionsSet(obligatoryOptionsSet);
        optionDTO.setIncompatibleOptionsSet(incompatibleOptionsSet);

        optionServiceImpl.convertToEntityAndSave(optionDTO);

        return "/workerOffice";
    }


}
