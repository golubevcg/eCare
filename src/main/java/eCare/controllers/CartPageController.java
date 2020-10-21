package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.ContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        for (ContractDTO contractDTOFromCart: cartChangedContractsSet) {
            ContractDTO contractDTOFromDB = contractService.getContractDTOByNumberOrNull(contractDTOFromCart.getContractNumber());
            ContractDTO editedContractDTO = new ContractDTO();
            editedContractDTO.setContractNumber(contractDTOFromDB.getContractNumber());
            boolean isChanged = false;

            if(!contractDTOFromCart.getTariff().equals( contractDTOFromDB.getTariff() )){
                editedContractDTO.setTariff(contractDTOFromCart.getTariff());
                isChanged = true;
            }

            Set<OptionDTO> optionDTOSet = new HashSet<>();
            //here we check if in new contract exists freshly connected options
            for (OptionDTO optionDTOFromSession: contractDTOFromCart.getSetOfOptions()) {
                if(!contractDTOFromDB.getSetOfOptions().contains(optionDTOFromSession)){
                    optionDTOSet.add(optionDTOFromSession);
                    isChanged = true;
                }
            }

            //here we check if in new contract were removed old options
            for (OptionDTO optionDTOFromDb: contractDTOFromDB.getSetOfOptions()) {
                if(!contractDTOFromCart.getSetOfOptions().contains(optionDTOFromDb)){
                    optionDTOSet.add(optionDTOFromDb);
                    isChanged = true;
                }
            }

            if(!optionDTOSet.isEmpty()){
                editedContractDTO.setSetOfOptions(optionDTOSet);
                isChanged = true;
            }

            if(contractDTOFromCart.isBlocked() != contractDTOFromDB.isBlocked()){
                editedContractDTO.setBlocked(contractDTOFromCart.isBlocked());
                isChanged = true;
            }

            if(isChanged){
                onlyContractsChanges.add(editedContractDTO);
            }
        }

        for (int i = 0; i < onlyContractsChanges.size(); i++) {
            System.out.println("contract " + i);
            System.out.println(onlyContractsChanges.get(i).getContractNumber());
            System.out.println("tariff " + onlyContractsChanges.get(i).getTariff());

            for (OptionDTO optiondDTO:onlyContractsChanges.get(i).getSetOfOptions()) {
                System.out.println("option: " + optiondDTO.getName());
            }


        }

        model.addAttribute("onlyContractsChanges", onlyContractsChanges);

        return "cartPage";
    }

}
