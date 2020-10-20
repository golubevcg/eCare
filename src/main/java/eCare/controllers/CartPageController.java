package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.impl.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Controller for page, in which cart finalisation occur.
 */

@Controller
public class CartPageController {

    final
    ContractServiceImpl contractService;

    public CartPageController(ContractServiceImpl contractService) {
        this.contractService = contractService;
    }

    @GetMapping(value = "/cartPage")
    public String getCartPage(Model model, HttpSession session){

        Map<String, Map<String, String>> map = new HashMap<>();
        HashSet<ContractDTO> cartChangedContractsSet = (HashSet<ContractDTO>) session.getAttribute("cartChangedContractsSet");

        for (ContractDTO contract: cartChangedContractsSet) {
            ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contract.getContractNumber());
            String number = contractDTOFromDB.getContractNumber();

            Map<String, String> mapOpt = new HashMap<>();

            if(!contract.getTariff().equals( contractDTOFromDB.getTariff() )){

            }

            //here we check if in new contract exists freshly connected options
            for (OptionDTO optionDTOFromSession: contract.getSetOfOptions()) {
                if(!contractDTOFromDB.getSetOfOptions().contains(optionDTOFromSession)){
                    mapOpt.put(optionDTOFromSession.getName(), "disabled");
                }
            }

            //here we check if in new contract were removed old options
            for (OptionDTO optionDTOFromDb: contractDTOFromDB.getSetOfOptions()) {
                if(!contract.getSetOfOptions().contains(optionDTOFromDb)){
                    mapOpt.put(optionDTOFromDb.getName(), "enabled");
                }
            }

            if(contract.isBlocked() != contractDTOFromDB.isBlocked()){

            }

            map.put(number,mapOpt);
        }


        model.addAttribute("contractsOptions", map);

        return "cartPage";
    }

}
