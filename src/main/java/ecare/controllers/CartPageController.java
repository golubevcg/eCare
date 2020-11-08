package ecare.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.services.api.CartService;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Controller for the page where the shopping cart is being finalised.
 */

@Controller
public class CartPageController {

    static final Logger log = Logger.getLogger(CartPageController.class);

    final
    ContractService contractService;

    final
    OptionService optionService;

    final
    CartService cartServiceImpl;

    public CartPageController(ContractService contractService, OptionService optionService,
                                CartService cartServiceImpl) {
        this.contractService = contractService;
        this.optionService = optionService;
        this.cartServiceImpl = cartServiceImpl;
    }

    Set<ContractDTO> cartContractsSetChangedFromCart = new HashSet<>();
    String cartContractsSetChangedForCart = "cartContractsSetChangedForCart";

    @GetMapping(value = "/cartPage")
    public String getCartPage(Model model, HttpSession session){
        cartContractsSetChangedFromCart = getChangedContractsSetFromSession(session);

        if(cartContractsSetChangedFromCart!=null) {
            List<ContractDTO> onlyContractsChanges = new ArrayList<>();
            AtomicBoolean showBlockedOnPage = new AtomicBoolean(false);
            Map<String, Map<String, String>> mapOfOptionsEnabledDisabled = new HashMap<>();

            for (ContractDTO contractDTOFromCart : cartContractsSetChangedFromCart) {
                cartServiceImpl.compareContractInDBWithContractInSession(
                        contractDTOFromCart,onlyContractsChanges,showBlockedOnPage, mapOfOptionsEnabledDisabled);
            }

            model.addAttribute("onlyContractsChanges", onlyContractsChanges);
            model.addAttribute("mapOfOptionsEnabledDisabled", mapOfOptionsEnabledDisabled);
            model.addAttribute("showBlockedOnPage", showBlockedOnPage);
        }

        return "cartPage";
    }

    @GetMapping(value = "/cartPage/removeContractChangingFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    void removeContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                   HttpSession session) {
        cartContractsSetChangedFromCart = getChangedContractsSetFromSession(session);
        cartContractsSetChangedFromCart.removeIf(
                contractDTO -> contractDTO.getContractNumber().equals(contractNumber.trim()) );
        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        cartContractsSetChangedFromCart.add(contractDTOFromDB);
        session.setAttribute(cartContractsSetChangedForCart, cartContractsSetChangedFromCart);
    }

    @GetMapping(value = "/cartPage/removeTariffChangingFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    void removeChangedTariffInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                                  HttpSession session) {
        cartContractsSetChangedFromCart = getChangedContractsSetFromSession(session);
        ContractDTO contractDTO = getContractByNumberFromSession(contractNumber);

        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        contractDTO.setTariff(contractDTOFromDB.getTariff());
        contractDTO.setSetOfOptions(contractDTOFromDB.getSetOfOptions());

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute(cartContractsSetChangedForCart, cartContractsSetChangedFromCart);
    }

    @GetMapping(value = "/cartPage/removeIsBlockedInContractFromSession/{contractNumber}",
            produces = "application/json")
    public @ResponseBody
    void removeIsBlockedInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                              HttpSession session) {

        cartContractsSetChangedFromCart = getChangedContractsSetFromSession(session);

        ContractDTO contractDTO = getContractByNumberFromSession(contractNumber);
        ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractNumber.trim());
        contractDTO.setBlocked(contractDTOFromDB.isBlocked());

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute(cartContractsSetChangedForCart, cartContractsSetChangedFromCart);

    }

    @PostMapping(value = "/cartPage/removeOptionInContractFromSession/{contractNumber}")
    public @ResponseBody
    void removeOptionInContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                           HttpSession session, @RequestBody String optionName){

        JsonObject json = new Gson().fromJson(optionName, JsonObject.class);
        String changedOptionName = json.get("optionName").getAsString();
        OptionDTO changedOption = optionService.getOptionDTOByNameOrNull(changedOptionName);

        cartContractsSetChangedFromCart = getChangedContractsSetFromSession(session);
        ContractDTO contractDTO = getContractByNumberFromSession(contractNumber);

        Set<OptionDTO> optionsSet = contractDTO.getSetOfOptions();
        if(!optionsSet.isEmpty() && optionsSet.contains(changedOption)){
            optionsSet.remove(changedOption);
        }else{
            optionsSet.add(changedOption);
        }

        cartContractsSetChangedFromCart.add(contractDTO);
        session.setAttribute(cartContractsSetChangedForCart, cartContractsSetChangedFromCart);

    }

    @GetMapping(value = "/cartPage/submitChanges")
    public @ResponseBody
    boolean submitChanges(HttpSession session) {
        Set<ContractDTO> cartContractsSetChangedFromCartSession = getChangedContractsSetFromSession(session);

        for (ContractDTO contractFromSession:cartContractsSetChangedFromCartSession) {
            contractService.convertToEntityAndUpdate(contractFromSession);
            log.info("Changes for contract with number = "
                    + contractFromSession.getContractNumber() + " were submitted");
        }
        session.removeAttribute(cartContractsSetChangedForCart);

        return true;
    }

    public Set<ContractDTO> getChangedContractsSetFromSession(HttpSession session){
        return (Set<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
    }

    public ContractDTO getContractByNumberFromSession(String contractNumber){
        ContractDTO contractDTO = null;
        for (ContractDTO contractFromSession:cartContractsSetChangedFromCart) {
            if(contractFromSession.getContractNumber().equals(contractNumber.trim()) ){
                contractDTO = contractFromSession;
            }
        }
        return contractDTO;
    }

}
