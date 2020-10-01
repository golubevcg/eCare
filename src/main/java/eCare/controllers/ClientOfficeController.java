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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        ContractDTO contractDTO = contractServiceImpl.getContractDTOById(Long.parseLong(contractID)).get(0);

        model.addAttribute("contractNumber", contractDTO.getContractNumber());
        model.addAttribute("firstAndSecondNames", userDTO.getFirstname() + " " + userDTO.getSecondname());

        TariffDTO tariffDTO = contractDTO.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " руб./мес.");

        List<OptionDTO> availableOptions = tariffDTO.getListOfOptions();
        List<OptionDTO> connectedOptions = contractDTO.getListOfOptions();
        Map<OptionDTO, Boolean> enabledOptionsDTOMap = new LinkedHashMap<>();
        for (int i = 0; i < availableOptions.size(); i++) {

            boolean isOptionInContract = false;
            for (int j = 0; j < connectedOptions.size(); j++) {
                if(availableOptions.get(i).equals(connectedOptions.get(j))){
                    isOptionInContract = true;
                }
            }

            enabledOptionsDTOMap.put(availableOptions.get(i), isOptionInContract);

        }

        model.addAttribute("enabledOptionsDTOMap", enabledOptionsDTOMap);


        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        model.addAttribute("connectedOptions", connectedOptions);

        return "/clientOffice";
    }

    @PostMapping("/clientOffice")
    public String postClientOffice(Model model, Principal principal,
                            @RequestParam(value = "tariffCheckbox", required = false) String[] tariffCheckbox) {
        return "clientOffice";
    }

    @PostMapping("/clientOffice/submitvalues")
    public @ResponseBody String updateValuesOnInformationFromView(
            @RequestBody( required = false) String[][] wrappedDataToExp) {
        System.out.println("++++++++++++");
        System.out.println(wrappedDataToExp==null);
        System.out.println("++++++++++++");

//        System.out.println(wrappedDataToExp.length);


        return "";
    }

}