package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Controller for page, in which cart finalisation occur.
 */

@Controller
public class CartPageController {

    final
    ContractService contractService;

    public CartPageController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(value = "/cartPage")
    public String getCartPage(Model model, HttpSession session){

        HashSet<ContractDTO> cartChangedContractsSet = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");

        List<ContractDTO> onlyContractsChanges = new ArrayList<>();

        Map<String, Map<String, String> > mapOfOptionsEnabledDisabled = new HashMap<>();

        for (ContractDTO contractDTOFromCart: cartChangedContractsSet) {
            ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractDTOFromCart.getContractNumber());
            ContractDTO editedContractDTO = new ContractDTO();
            editedContractDTO.setContractNumber(contractDTOFromDB.getContractNumber());
            boolean isChanged = false;

            if(!contractDTOFromCart.getTariff().equals( contractDTOFromDB.getTariff() )){
                editedContractDTO.setTariff(contractDTOFromCart.getTariff());
                isChanged = true;
            }

            Map<String, String> optionMapEnabledDisabled = new HashMap<>();
            //here we check if in new contract exists freshly connected options
            for (OptionDTO optionDTOFromSession: contractDTOFromCart.getSetOfOptions()) {
                if(!contractDTOFromDB.getSetOfOptions().contains(optionDTOFromSession)){
                    optionMapEnabledDisabled.put(optionDTOFromSession.getName(), "enabled");
                    isChanged = true;
                }
            }

            //here we check if in new contract were removed old options
            for (OptionDTO optionDTOFromDb: contractDTOFromDB.getSetOfOptions()) {
                if(!contractDTOFromCart.getSetOfOptions().contains(optionDTOFromDb)){
                    optionMapEnabledDisabled.put(optionDTOFromDb.getName(), "disabled");
                    isChanged = true;
                }
            }

            mapOfOptionsEnabledDisabled.put(contractDTOFromCart.getContractNumber(), optionMapEnabledDisabled);

            if(contractDTOFromCart.isBlocked() != contractDTOFromDB.isBlocked()){
                editedContractDTO.setBlocked(contractDTOFromCart.isBlocked());
                isChanged = true;
            }

            if(isChanged){
                onlyContractsChanges.add(editedContractDTO);
            }
        }

        model.addAttribute("onlyContractsChanges", onlyContractsChanges);
        model.addAttribute("mapOfOptionsEnabledDisabled", mapOfOptionsEnabledDisabled);

        return "cartPage";
    }

    @PostMapping(value = "/cartPage/removeContractFromSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    String removeContractFromSession(@PathVariable(value = "contractNumber") String contractNumber,
                                     @RequestBody String checked, HttpSession session) {
        HashSet<ContractDTO> cartContractsSetChangedForCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContractNumber().equals(contractNumber)){
                cartContractsSetChangedForCart.remove(contractDTO);
            }
        }
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);
        return "";
    }
}
