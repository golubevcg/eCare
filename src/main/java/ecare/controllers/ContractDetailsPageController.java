package ecare.controllers;

import ecare.model.dto.ContractDTO;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * Controller for page, in which user can check and edit contract details.
 */

@Controller
public class ContractDetailsPageController {

    final UserService userServiceImpl;
    final TariffService tariffServiceImpl;
    final ContractService contractServiceImpl;
    final OptionService optionServiceImpl;
    public ContractDetailsPageController(UserService userServiceImpl, TariffService tariffServiceImpl,
                                         ContractService contractServiceImpl, OptionService optionServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.tariffServiceImpl = tariffServiceImpl;
        this.contractServiceImpl = contractServiceImpl;
        this.optionServiceImpl = optionServiceImpl;
    }

    @GetMapping("/contractDetails/{contractID}")
    public String getContractDetailsPage(Model model, CsrfToken token,
                                         @PathVariable(value = "contractID") String contractID,
                                         HttpSession session) {
        contractServiceImpl.addContractDetailsToModelForPage(model, contractID, session);
        return "contractDetailsPage";
    }

    @PostMapping(value = "/contractDetails/getTariffOptions/{contractNumber}", produces = "application/json")
    public @ResponseBody
    String getSortedListOfOptions(@RequestBody String selectedTariffName, HttpSession session,
                            @PathVariable(value = "contractNumber") String contractNumber) {

        return contractServiceImpl.getSortedListOfOptions(contractNumber, selectedTariffName, session);
    }


    @PostMapping(value = "/contractDetails/loadDependedOptions/{selectedOption}", produces = "application/json")
    public @ResponseBody
    String getDependingOptions(@PathVariable(value = "selectedOption") String selectedOptionId,
                               @RequestBody String[] stringsArrayInfoFromFront, HttpSession session) {

        return contractServiceImpl.getDependingOptions(session, selectedOptionId, stringsArrayInfoFromFront);
    }

    @PostMapping(value = "/contractDetails/updateBlockedInSession/{contractNumber}", produces = "application/json")
    public @ResponseBody
    String updateBlockedInSession(@PathVariable(value = "contractNumber") String contractNumber,
                               @RequestBody String checked, HttpSession session) {
        ContractDTO contract = null;
        HashSet<ContractDTO> cartContractsSetChangedForCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContractNumber().equals(contractNumber)){
                contract = contractDTO;
            }
        }
        contract.setBlocked(Boolean.parseBoolean(checked));
        cartContractsSetChangedForCart.add(contract);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);
        return "";
    }
}