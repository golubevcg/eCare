package ecare.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import ecare.model.dto.AdDTO;
import ecare.model.dto.TariffDTO;
import ecare.mq.MessageSender;
import ecare.services.api.AdService;
import ecare.services.api.TariffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdPageController {

    final
    MessageSender messageSender;

    final
    TariffService tariffServiceImpl;

    final
    AdService adServiceIml;


    public AdPageController(MessageSender messageSender,
                            TariffService tariffServiceImpl, AdService adServiceIml) {
        this.messageSender = messageSender;
        this.tariffServiceImpl = tariffServiceImpl;
        this.adServiceIml = adServiceIml;
    }

    @GetMapping("/adPage")
    public String getAdTextPage(Model model){
        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        ArrayList<TariffDTO> tariffDTOSFromMainAd =
                new ArrayList<>(adServiceIml.getAdDTOByNameOrNull("main").getSetOfTariffs());
        Collections.sort(tariffDTOSFromMainAd);
        model.addAttribute("listOfAdTariffs", tariffDTOSFromMainAd);
        return "adPage";
    }

    @PostMapping(value = "/adPage/submit", produces = "application/json")
    public @ResponseBody
    boolean submitAdChanges(@RequestBody String adText){
        JsonArray jsonArray = new Gson().fromJson(adText, JsonArray.class);
        AdDTO adDTO = adServiceIml.getAdDTOByNameOrNull("main");
        if (adDTO != null) {
            Set<TariffDTO> tariffDTOSet = new HashSet<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(jsonArray.get(i).getAsString());
                tariffDTOSet.add(tariffDTO);
            }
            adDTO.setSetOfTariffs(tariffDTOSet);
            adServiceIml.convertToEntityAndUpdate(adDTO);

            messageSender.sendMessage("update");
            return true;
        }

        return false;

    }

    @ResponseBody
    @GetMapping(value = "/adPage/getTariffInfo/{selectedTariff}")
    public String getTariffInfo(@PathVariable("selectedTariff") String selectedTariff) {
        TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(selectedTariff);
        String[] exportArray = new String[2];
        exportArray[0] = tariffDTO.getShortDiscription();
        exportArray[1] = tariffDTO.getPrice().toString();
        return new Gson().toJson(exportArray);
    }

}
