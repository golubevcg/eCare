package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.RoleDTO;
import eCare.model.dto.UserContractDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Option;
import eCare.services.impl.*;
import eCare.validator.UserContractDTOValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class RegistrationPageController {

    static final Logger log = Logger.getLogger(RegistrationPageController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private TariffServiceImpl tariffServiceImpl;

    @Autowired
    private UserContractDTOValidator userValidator;

    @Autowired
    private OptionServiceImpl optionServiceImpl;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @GetMapping(value = "/userRegistration", produces = "text/plain;charset=UTF-8")
    public String getUserRegistration(Model model){
        model.addAttribute("userForm", new UserContractDTO());
        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        return "userRegistration";
    }

    @PostMapping("/userRegistration")
    public String postRegistration(Model model,
                                   @ModelAttribute("userForm") UserContractDTO userForm,
                                   BindingResult userFormBindingResult,
                                   @RequestParam(required=false , name = "roleCheckbox") String roleCheckbox,
                                   @RequestParam(required=false , name = "selectedTariff") String selectedTariff,
                                   @RequestParam(required=false, name= "selectedOptions") String[] selectedOptionsArray){

        model.addAttribute("listOfTariffs", tariffServiceImpl.getActiveTariffs());
        model.addAttribute("selectedTariff", selectedTariff);
        userValidator.validate(userForm, userFormBindingResult);
        UserDTO userDTO = userForm.getUserDTO();
        RoleDTO roleDTO = new RoleDTO();

        ContractDTO contractDTO = null;

        if( roleCheckbox!=null){
            roleDTO = roleServiceImpl.getRoleDTOByRolename("ADMIN");
        }else{
            roleDTO = roleServiceImpl.getRoleDTOByRolename("USER");
            contractDTO = userForm.getContractDTO();

            if(selectedOptionsArray.length!=0) {
                for (int i = 0; i < (selectedOptionsArray.length); i++) {
                    contractDTO.addOption(optionServiceImpl.getOptionDTOByName(selectedOptionsArray[i]));
                }
            }

            contractDTO.setTariff(tariffServiceImpl.getTariffDTOByTariffname(selectedTariff));
        }

        if(userFormBindingResult.hasErrors()){
            return "userRegistration";
        }

        contractDTO.setUser(userDTO);

        HashSet<RoleDTO> roleDTOHashSet = new HashSet<>();
        roleDTOHashSet.add(roleDTO);
        roleDTO.addUser(userDTO);

        userDTO.addContractDTO(contractDTO);
        userDTO.setRoles(roleDTOHashSet);
        userServiceImpl.convertToEntityAndSave(userDTO);

        contractDTO.setUser(userServiceImpl.getUserDTOByLogin(userDTO.getLogin()));
        contractServiceImpl.convertToEntityAndSave(contractDTO);

        return "workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/userRegistration/loadOptionByTariff/{selectedTariff}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("selectedTariff") String selectedTariff) {

        List<Option> optionList = tariffServiceImpl.getTariffByTariffName(selectedTariff).get(0).getListOfOptions();
        List<String> optionNamesList = new ArrayList<>();
        for (Option option: optionList) {
            optionNamesList.add(option.getName());
        }

        return new Gson().toJson(optionNamesList);
    }

}
