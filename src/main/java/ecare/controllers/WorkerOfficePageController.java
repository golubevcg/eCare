package ecare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
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

    final
    ContractService contractServiceImpl;

    final
    TariffService tariffServiceImpl;

    final
    OptionService optionServiceImpl;

    public WorkerOfficePageController(ContractService contractServiceImpl, TariffService tariffServiceImpl, OptionService optionServiceImpl) {
        this.contractServiceImpl = contractServiceImpl;
        this.tariffServiceImpl = tariffServiceImpl;
        this.optionServiceImpl = optionServiceImpl;
    }

    @GetMapping("/workerOffice")
    public String getWorkerOffice(Model model, CsrfToken token){
        return "workerOfficePage";
    }

    @GetMapping(value = "/workerOffice/searchInContracts/{searchInput}", produces = "application/json")
    public @ResponseBody
    String getContractSearchResult(@PathVariable String searchInput) {
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
