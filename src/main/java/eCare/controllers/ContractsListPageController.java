package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.UserDTO;
import eCare.services.api.UserService;
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

        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(principal.getName());

        Set<ContractDTO> contractsSet = userDTO.getListOfActiveContracts();
        if(session.getAttribute("cartContractsSetChangedForCart")==null){
            session.setAttribute("cartContractsSetDefaultFromDB", contractsSet);
            session.setAttribute("cartContractsSetChangedForCart", contractsSet);
        }else{
            contractsSet = (Set<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        }

        ArrayList<ContractDTO> sortedListOfContracts = new ArrayList<>();
        sortedListOfContracts.addAll(contractsSet);
        Collections.sort(sortedListOfContracts);

        model.addAttribute("contractsSet", sortedListOfContracts);
        return "contractsListPage";
    }

}