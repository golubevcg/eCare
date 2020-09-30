package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.services.impl.TariffServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ClientOfficeController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    TariffServiceImpl tariffServiceImpl;

    @GetMapping("/clientOffice")
    public String getClientOffice(Model model, CsrfToken token, Principal principal) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLogin(principal.getName());
        System.out.println(userDTO.toString());

        ContractDTO contractDTO = userDTO.getListOfContracts().get(0);

        model.addAttribute("contractNumber", contractDTO.getContractNumber());
        model.addAttribute("firstAndSecondNames", userDTO.getFirstname() + " " + userDTO.getSecondname());

        TariffDTO tariffDTO = contractDTO.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " руб./мес.");

        List<OptionDTO> optionDTOList = tariffDTO.getListOfOptions();
        model.addAttribute("listOfOptions", optionDTOList);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);

        return "clientOffice";
    }

}