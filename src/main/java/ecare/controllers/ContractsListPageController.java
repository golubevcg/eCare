package ecare.controllers;

import ecare.model.dto.ContractDTO;
import ecare.model.dto.UserDTO;
import ecare.services.api.UserService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Controller to the page, which returns list of user contracts.
 */
@Controller
public class ContractsListPageController {

    final
    UserService userServiceImpl;

    public ContractsListPageController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/contracts")
    public String getContractsListPage(Model model, CsrfToken token, Principal principal, HttpSession session) {

        String cartContractsSetChangedForCartName = "cartContractsSetChangedForCart";

        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(principal.getName());

        Set<ContractDTO> contractsSet = userDTO.getListOfActiveContracts();
        if(session.getAttribute(cartContractsSetChangedForCartName)==null){
            session.setAttribute("cartContractsSetDefaultFromDB", contractsSet);
            session.setAttribute(cartContractsSetChangedForCartName, contractsSet);
        }else{
            contractsSet = (Set<ContractDTO>) session.getAttribute(cartContractsSetChangedForCartName);
        }

        ArrayList<ContractDTO> sortedListOfContracts = new ArrayList<>();
        sortedListOfContracts.addAll(contractsSet);
        Collections.sort(sortedListOfContracts);

        model.addAttribute("contractsSet", sortedListOfContracts);
        return "contractsListPage";
    }

}