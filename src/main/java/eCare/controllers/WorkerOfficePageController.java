package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.OptionServiceImpl;
import eCare.services.impl.TariffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class WorkerOfficePageController {

    @Autowired
    ContractServiceImpl contractServiceImpl;

    @Autowired
    TariffServiceImpl tariffServiceImpl;

    @Autowired
    OptionServiceImpl optionServiceImpl;

    @GetMapping("/workerOffice")
    public String getWorkerOffice(Model model, CsrfToken token){
        return "workerOffice";
    }

    @GetMapping(value = "/workerOffice/searchInContracts/{searchInput}", produces = "application/json")
    public @ResponseBody
    String getContractSearchResult(Model model, CsrfToken token, Principal principal,@PathVariable String searchInput) {
        List<ContractDTO> searchResult = contractServiceImpl.searchForContractByNumber(searchInput);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(searchResult);
    }

    @GetMapping(value = "/workerOffice/searchInTariffs/{searchInput}", produces = "application/json")
    public @ResponseBody
    String getTariffsSearchResult(Model model, CsrfToken token, Principal principal,@PathVariable String searchInput) {
        List<TariffDTO> searchResult = tariffServiceImpl.searchForTariffDTOByName(searchInput);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(searchResult);
    }

    @GetMapping(value = "/workerOffice/searchInOptions/{searchInput}", produces = "application/json")
    public @ResponseBody
    String getOptionsSearchResult(Model model, CsrfToken token, Principal principal,@PathVariable String searchInput) {
        List<OptionDTO> searchResult = optionServiceImpl.searchForOptionByName(searchInput);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(searchResult);
    }



}
