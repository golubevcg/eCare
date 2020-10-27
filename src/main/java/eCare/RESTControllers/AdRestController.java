package eCare.RESTControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.TariffDTO;
import eCare.services.api.AdService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
public class AdRestController {

    final AdService adServiceIml;


    public AdRestController(AdService adServiceIml) {
        this.adServiceIml = adServiceIml;
    }

    @GetMapping(value="adPage/getAdTariffs/{adName}", produces = "application/json")
    public String getMainAdTariffs(@PathVariable String adName){
        Set<TariffDTO> listOfTariffs = adServiceIml.getAdDTOByNameOrNull(adName).getSetOfTariffs();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(listOfTariffs);
    }


}
