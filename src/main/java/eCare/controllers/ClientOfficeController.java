package eCare.controllers;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.services.impl.ContractServiceImpl;
import eCare.services.impl.TariffServiceImpl;
import eCare.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ClientOfficeController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    TariffServiceImpl tariffServiceImpl;

    @Autowired
    ContractServiceImpl contractServiceImpl;

    @GetMapping("/clientOffice/{contractID}")
    public String getClientOffice(Model model, CsrfToken token, Principal principal,
                             @PathVariable(value="contractID") String contractID) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLogin(principal.getName());
        System.out.println("1");
        ContractDTO contractDTO = contractServiceImpl.getContractDTOById(Long.parseLong(contractID)).get(0);
        System.out.println("2");

        model.addAttribute("contractNumber", contractDTO.getContractNumber());
        model.addAttribute("firstAndSecondNames", userDTO.getFirstname() + " " + userDTO.getSecondname());
        System.out.println("3");

        TariffDTO tariffDTO = contractDTO.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " руб./мес.");
        System.out.println("4");

        List<OptionDTO> optionDTOList = tariffDTO.getListOfOptions();
        model.addAttribute("listOfOptions", optionDTOList);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        System.out.println("5");

        return "/clientOffice";
    }

    @PostMapping("/clientOffice")
    public String postClientOffice(Model model, Principal principal,
                                   @RequestParam String tariffCheckbox) {
        UserDTO userDTO = userServiceImpl.getUserDTOByLogin(principal.getName());
        ContractDTO contractDTO = userDTO.getListOfContracts().get(0);

        System.out.println(tariffCheckbox);

//        забрать выбранный тариф, если он отличается от текущего - поменять,
//        забрать выбранные опции и пройтись списком по текущим, если есть изменения обновить контракт,
//        забрать значение заблокировать номер, если тру - заблокировать номер, контракт обновить
//        потом разобраться с блокировкой несовместимых опций
//         потом подумать что делать с несколькими контрактами
//                потом подумать как встроить pageination


        return "clientOffice";
    }

}