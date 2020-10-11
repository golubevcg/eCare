package eCare.controllers;

import com.google.gson.Gson;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class CheckOptionController {

    static final Logger log = Logger.getLogger(UserRegistrationController.class);

    @Autowired
    private OptionService optionServiceImpl;

    @Autowired
    private ContractService contractServiceImpl;

    @GetMapping(value = "/checkOption/{optionName}")
    public String getOptionToCheck(Model model, @PathVariable(name="optionName") String optionName) {

        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(optionName);

        List<OptionDTO> listOfAllActiveOptions = optionServiceImpl.getActiveOptions();
        model.addAttribute("listOfActiveOptions", listOfAllActiveOptions);
        model.addAttribute("optionDTO", optionDTO);
        return "checkOption";
    }

    @PostMapping(value = "/checkOption/{optionName}")
    public String submitEditedOptions(Model model, @PathVariable(name="optionName") String optionName,
                                      @ModelAttribute OptionDTO optionDTO,
                                      BindingResult optionDTOBindingResult,
                                      @RequestParam(required = false, name="") String[] selectedIncompatibleOptions,
                                      @RequestParam(required = false, name="") String[] selectedObligatoryOptions,
                                      @RequestParam(required=false , name = "blockConnectedContracts") String blockConnectedContracts){

        OptionDTO optionDTO1 = optionServiceImpl.getOptionDTOByNameOrNull(optionName);
        optionDTO1.setName( optionDTO.getName() );
        optionDTO1.setPrice( optionDTO.getPrice() );
        optionDTO1.setConnectionCost( optionDTO.getConnectionCost() );
        optionDTO1.setShortDiscription( optionDTO.getShortDiscription() );

        if(selectedIncompatibleOptions!=null) {
            Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedIncompatibleOptions.length; i++) {
                incompatibleOptionsSet.add( optionServiceImpl.getOptionDTOByNameOrNull(selectedIncompatibleOptions[i]) );
            }
            optionDTO1.setIncompatibleOptionsSet(incompatibleOptionsSet);
        }

        if(selectedObligatoryOptions!=null) {
            Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();
            for (int i = 0; i < selectedObligatoryOptions.length; i++) {
                obligatoryOptionsSet.add( optionServiceImpl.getOptionDTOByNameOrNull(selectedObligatoryOptions[i]) );
            }
            optionDTO1.setIncompatibleOptionsSet(obligatoryOptionsSet);
        }


        if( blockConnectedContracts!=null){
            System.out.println("STARTED TO BLOCK CONTRACTS!");
            Set<ContractDTO> contractDTOS = optionDTO1.getContractsOptions();
            for (ContractDTO contractDTO: contractDTOS) {
                contractDTO.setBlocked(true);
                contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            }
            optionServiceImpl.convertToEntityAndUpdate(optionDTO1);
        }else {
            optionServiceImpl.convertToEntityAndUpdate(optionDTO1);
        }

        return "/workerOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/checkOption/checkNewName/{newName}", method = RequestMethod.GET)
    public String loadOptionByTariff(@PathVariable("newName") String newName) {
        OptionDTO optionDTO = optionServiceImpl.getOptionDTOByNameOrNull(newName);
        if(optionDTO!=null){
            return "true";
        }else{
            return "false";
        }
    }

}