package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Controller for the page where the shopping cart is being finalised.
 */

@Controller
public class CartPageController {

    final
    ContractService contractService;

    final
    OptionService optionService;

    public CartPageController(ContractService contractService, OptionService optionService) {
        this.contractService = contractService;
        this.optionService = optionService;
    }

    @GetMapping(value = "/cartPage")
    public String getCartPage(Model model, HttpSession session){

        HashSet<ContractDTO> cartChangedContractsSet = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");

        if(cartChangedContractsSet!=null) {
            List<ContractDTO> onlyContractsChanges = new ArrayList<>();
            Boolean showBlockedOnPage = false;
            Map<String, Map<String, String>> mapOfOptionsEnabledDisabled = new HashMap<>();

            for (ContractDTO contractDTOFromCart : cartChangedContractsSet) {
                ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractDTOFromCart.getContractNumber());
                ContractDTO editedContractDTO = new ContractDTO();
                editedContractDTO.setContractNumber(contractDTOFromDB.getContractNumber());
                boolean isChanged = false;

                if (!contractDTOFromCart.getTariff().equals(contractDTOFromDB.getTariff())) {
                    editedContractDTO.setTariff(contractDTOFromCart.getTariff());
                    isChanged = true;
                }

                Map<String, String> optionMapEnabledDisabled = new HashMap<>();
                //here we check if in new contract exists freshly connected options
                for (OptionDTO optionDTOFromSession : contractDTOFromCart.getSetOfOptions()) {
                    if (!contractDTOFromDB.getSetOfOptions().contains(optionDTOFromSession)
                            && contractDTOFromCart.getSetOfOptions().contains(optionDTOFromSession)) {
                        optionMapEnabledDisabled.put(optionDTOFromSession.getName(), "enabled");
                        isChanged = true;
                    }
                }

                //here we check if in new contract were removed old options
                for (OptionDTO optionDTOFromDb : contractDTOFromDB.getSetOfOptions()) {
                    if (!contractDTOFromCart.getSetOfOptions().contains(optionDTOFromDb)
                            && contractDTOFromCart.getSetOfOptions().contains(contractDTOFromDB.getSetOfOptions())) {

                        optionMapEnabledDisabled.put(optionDTOFromDb.getName(), "disabled");
                        isChanged = true;
                    }
                }

                mapOfOptionsEnabledDisabled.put(contractDTOFromCart.getContractNumber(), optionMapEnabledDisabled);

                if (contractDTOFromCart.isBlocked() != contractDTOFromDB.isBlocked()) {
                    editedContractDTO.setBlocked(contractDTOFromCart.isBlocked());
                    isChanged = true;
                    showBlockedOnPage = true;
                }

                if (isChanged) {
                    onlyContractsChanges.add(editedContractDTO);
                }
            }

            model.addAttribute("onlyContractsChanges", onlyContractsChanges);
            model.addAttribute("mapOfOptionsEnabledDisabled", mapOfOptionsEnabledDisabled);
            model.addAttribute("showBlockedOnPage", showBlockedOnPage);
        }
        return "cartPage";
    }

    @GetMapping(value = "/cartPage/removeContractChangingFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    void removeContractFromSession(@PathVariable(value = "contractNumber") String contractNumber, HttpSession session) {
        HashSet<ContractDTO> cartContractsSetChangedFromCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        cartContractsSetChangedFromCart.removeIf(contractDTO -> contractDTO.getContractNumber().equals(contractNumber.trim()));
        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        cartContractsSetChangedFromCart.add(contractDTOFromDB);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedFromCart);
    }

    @GetMapping(value = "/cartPage/removeTariffChangingFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    void removeTariffChangingInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber, HttpSession session) {
        HashSet<ContractDTO> cartContractsSetChangedFromCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        ContractDTO contractDTO = null;
        for (ContractDTO contractFromSession:cartContractsSetChangedFromCart) {
            if(contractFromSession.getContractNumber().equals(contractNumber.trim()) ){
                contractDTO = contractFromSession;
            }
        }

        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        contractDTO.setTariff(contractDTOFromDB.getTariff());
        contractDTO.setSetOfOptions(contractDTOFromDB.getSetOfOptions());

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedFromCart);
    }

    @GetMapping(value = "/cartPage/removeIsBlockedInContractFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    void removeIsBlockedInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber, HttpSession session) {
        HashSet<ContractDTO> cartContractsSetChangedFromCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        ContractDTO contractDTO = null;
        for (ContractDTO contractFromSession:cartContractsSetChangedFromCart) {
            if(contractFromSession.getContractNumber().equals(contractNumber.trim()) ){
                contractDTO = contractFromSession;
            }
        }
        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        contractDTO.setBlocked(contractDTOFromDB.isBlocked());

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedFromCart);

    }

    @PostMapping(value = "/cartPage/removeOptionInContractFromSession/{contractNumber}")
    public @ResponseBody
    void removeOptionInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                           HttpSession session, @RequestBody String optionName) throws UnsupportedEncodingException {

        JsonObject json = new Gson().fromJson(optionName, JsonObject.class);
        String changedOptionName = json.get("optionName").getAsString();
        OptionDTO changedOption = optionService.getOptionDTOByNameOrNull(changedOptionName);

        HashSet<ContractDTO> cartContractsSetChangedFromCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        ContractDTO contractDTO = null;
        for (ContractDTO contractFromSession:cartContractsSetChangedFromCart) {
            if(contractFromSession.getContractNumber().equals(contractNumber.trim()) ){
                contractDTO = contractFromSession;
            }
        }

        Set<OptionDTO> optionsSet = contractDTO.getSetOfOptions();
        if(!optionsSet.isEmpty() && optionsSet.contains(changedOption)){
            optionsSet.remove(changedOption);
        }else {
            optionsSet.add(changedOption);
        }

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedFromCart);

    }

    @GetMapping(value = "/cartPage/submitChanges")
    public @ResponseBody
    String removeIsBlockedInContractFromSession(HttpSession session) {
        HashSet<ContractDTO> cartContractsSetChangedFromCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractFromSession:cartContractsSetChangedFromCart) {
            contractService.updateConvertDTO(contractFromSession);
        }
        session.removeAttribute("cartContractsSetChangedForCart");

        return "true";
    }

}
