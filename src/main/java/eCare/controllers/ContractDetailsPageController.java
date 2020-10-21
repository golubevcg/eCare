package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

/**
 * Controller for page, in which user can check and edit contract details.
 */

@Controller
public class ContractDetailsPageController {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

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
    public String getContractDetailsPage(Model model, CsrfToken token, Principal principal,
                                         @PathVariable(value = "contractID") String contractID, HttpSession session) {

        ContractDTO currentContractForCartFromSession = null;
        HashSet<ContractDTO> cartContractsSetChangedForCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContract_id().toString().equals(contractID)){
                currentContractForCartFromSession = contractDTO;
            }
        }

        System.out.println(currentContractForCartFromSession.getContractNumber() + " , " + currentContractForCartFromSession.getTariff().getName());
        for (OptionDTO option: currentContractForCartFromSession.getSetOfOptions()) {
            System.out.println("option: " + option.getName());
        }

        UserDTO userBeforeEditing = currentContractForCartFromSession.getUser();

        model.addAttribute("contractNumber", currentContractForCartFromSession.getContractNumber());
        model.addAttribute("firstAndSecondNames", userBeforeEditing.getFirstname() + " " + userBeforeEditing.getSecondname());

        TariffDTO tariffDTO = currentContractForCartFromSession.getTariff();
        model.addAttribute("selectedTariff", tariffDTO.getName());
        model.addAttribute("tariffDecription", tariffDTO.getShortDiscription());
        model.addAttribute("tariffPrice", tariffDTO.getPrice() + " $ / month");

        Set<OptionDTO> availableOptions = tariffDTO.getSetOfOptions();
        ArrayList<OptionDTO> sortedListOfAvailableOptions = new ArrayList<>();
        sortedListOfAvailableOptions.addAll(availableOptions);
        Collections.sort(sortedListOfAvailableOptions);

        Set<OptionDTO> connectedOptions = currentContractForCartFromSession.getSetOfOptions();
        ArrayList<OptionDTO> sortedListOfConnectedOptions = new ArrayList<>();
        sortedListOfConnectedOptions.addAll(connectedOptions);
        Collections.sort(sortedListOfConnectedOptions);
        LinkedHashSet<OptionDTO> optionDTOLinkedHashSet = new LinkedHashSet<>();
        optionDTOLinkedHashSet.addAll(sortedListOfConnectedOptions);


        Map<OptionDTO, Boolean> enabledOptionsDTOMap = new LinkedHashMap<>();

        for (OptionDTO availableOption : sortedListOfAvailableOptions) {
            boolean isOptionInContract = false;

            for (OptionDTO connectedOption : sortedListOfConnectedOptions) {
                if (availableOption.getOption_id() == (connectedOption.getOption_id()))
                    isOptionInContract = true;
            }

            enabledOptionsDTOMap.put(availableOption, isOptionInContract);
        }

        model.addAttribute("enabledOptionsDTOMap", enabledOptionsDTOMap);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        model.addAttribute("connectedOptions", optionDTOLinkedHashSet);
        model.addAttribute("isBlocked", currentContractForCartFromSession.isBlocked());

        return "contractDetailsPage";
    }

    @PostMapping(value = "/contractDetails/getTariffOptions/{contractNumber}", produces = "application/json")
    public @ResponseBody
    String postClientOffice(@RequestBody String selectedTariffName, HttpSession session,
                            @PathVariable(value = "contractNumber") String contractNumber) {

        ContractDTO currentContractForCartFromSession = null;
        HashSet<ContractDTO> cartContractsSetChangedForCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContractNumber().equals(contractNumber)){
                currentContractForCartFromSession = contractDTO;
            }
        }

        TariffDTO tariffDTO = tariffServiceImpl
                .getTariffDTOByTariffNameOrNull(selectedTariffName
                        .replace("\"", ""));

        Set<OptionDTO> setOfOptions = new HashSet<>();
        if(tariffDTO!=null){
            setOfOptions = tariffDTO.getSetOfOptions();
        }

        ArrayList<OptionDTO> sortedListOfOptions = new ArrayList<>();
        sortedListOfOptions.addAll(setOfOptions);
        Collections.sort(sortedListOfOptions);

        currentContractForCartFromSession.setTariff(tariffDTO);
        currentContractForCartFromSession.setSetOfOptions(new HashSet<>());
        cartContractsSetChangedForCart.add(currentContractForCartFromSession);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);

        System.out.println("afterTariffChange: " + currentContractForCartFromSession.getContractNumber() + " , " + currentContractForCartFromSession.getTariff().getName());
        for (OptionDTO option: currentContractForCartFromSession.getSetOfOptions()) {
            System.out.println("option: " + option.getName());
        }


        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(sortedListOfOptions);
    }

    /**
     *
      This method used for returning two arrays as json - first with arrays of id's for incompatible options,
      second for obligatory, they will be parced in front end for enabling/disabling dependent options.
      And with error message if child options blocked for enabling/disabling.
     */
    @PostMapping(value = "/contractDetails/loadDependedOptions/{selectedOption}", produces = "application/json")
    public @ResponseBody
    String getDependingOptions(@PathVariable(value = "selectedOption") String selectedOptionId,
                               @RequestBody String[] stringsArrayInfoFromFront, HttpSession session) {
        OptionDTO changedOptionDTO = optionServiceImpl.getOptionDTOById( Long.valueOf(selectedOptionId) );
        TariffDTO selectedTariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(stringsArrayInfoFromFront[0]);
        Boolean isChecked = Boolean.valueOf(stringsArrayInfoFromFront[1]);
        String contractNumber = stringsArrayInfoFromFront[4];

        String[] blockedOptionIdsArray = stringsArrayInfoFromFront[2].split(",");
        Set<String> blockedOptionIdsSet = new HashSet<>();
        for (int i = 0; i < blockedOptionIdsArray.length; i++) {
            blockedOptionIdsSet.add( blockedOptionIdsArray[i] );
        }

        String[] checkedOptionIdsArray = stringsArrayInfoFromFront[3].split(",");
        Set<String> checkedOptionIdsSet = new HashSet<>();
        for (int i = 0; i < checkedOptionIdsArray.length; i++) {
            checkedOptionIdsSet.add( checkedOptionIdsArray[i] );

        }

        Set<OptionDTO> notSelectedOptionsSet = selectedTariffDTO.getSetOfOptions();
        notSelectedOptionsSet.remove(changedOptionDTO);
        Set<String> incompatibleOptionIds = new HashSet<>();
        Set<String> obligatoryOptionIds = new HashSet<>();

        this.cascadeCheckOptionDependencies(changedOptionDTO, incompatibleOptionIds,
                                            obligatoryOptionIds, notSelectedOptionsSet, isChecked);

        Set<String>[] array = new HashSet[3];
        array[0]=incompatibleOptionIds;
        array[1]=obligatoryOptionIds;

        String obligatoryErrorMessage = "";
        for (String obligOptId: obligatoryOptionIds) {
            if(!checkedOptionIdsSet.contains(obligOptId) && blockedOptionIdsSet.contains(obligOptId)){
                obligatoryErrorMessage = obligatoryErrorMessage + optionServiceImpl.getOptionDTOById(Long.valueOf(obligOptId)).getName() + " ";
            }
        }

        String incompatibleErrorMessage = "";
        for (String incompOptId: incompatibleOptionIds) {
            if(checkedOptionIdsSet.contains(incompOptId) && blockedOptionIdsSet.contains(incompOptId)){
                incompatibleErrorMessage = incompatibleErrorMessage + optionServiceImpl.getOptionDTOById(Long.valueOf(incompOptId)).getName() + " ";
            }
        }
        Set<String> errorMessageSet = new HashSet<>();
        String finalErrorMessage = obligatoryErrorMessage + incompatibleErrorMessage;
        errorMessageSet.add(finalErrorMessage);
        array[2]= errorMessageSet;

        if(finalErrorMessage.length()==0){

                ContractDTO currentContractForCartFromSession = null;
                HashSet<ContractDTO> cartContractsSetChangedForCart = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
                for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
                    if(contractDTO.getContractNumber().equals(contractNumber)){
                        currentContractForCartFromSession = contractDTO;
                    }
                }

                OptionDTO optionDTO = optionServiceImpl.getOptionDTOById(Long.valueOf(selectedOptionId));
                if(isChecked){
                    currentContractForCartFromSession.addOption(optionDTO);
                }else{
                    currentContractForCartFromSession.removeOption(optionDTO);
                }

                for (String optionIdi: incompatibleOptionIds) {
                    OptionDTO optionDTOInc = optionServiceImpl.getOptionDTOById(Long.valueOf(optionIdi));
                    currentContractForCartFromSession.removeOption(optionDTOInc);
                }

                for (String optionIdo: obligatoryOptionIds) {
                    OptionDTO optionDTOObl = optionServiceImpl.getOptionDTOById(Long.valueOf(optionIdo));
                    currentContractForCartFromSession.addOption(optionDTOObl);
                }

                cartContractsSetChangedForCart.add(currentContractForCartFromSession);
                session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);

                System.out.println(currentContractForCartFromSession.getContractNumber() + " , " + currentContractForCartFromSession.getTariff().getName());
                for (OptionDTO option: currentContractForCartFromSession.getSetOfOptions()) {
                    System.out.println("option: " + option.getName());
                }

        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(array);
    }

    /**
     * This function for checking depending options in child options (recursively)
     */
    public void cascadeCheckOptionDependencies(OptionDTO currentOption,
                                               Set<String> incompatibleOptionIds,
                                               Set<String> obligatoryOptionIds,
                                               Set<OptionDTO> notSelectedOptionIds,
                                               Boolean isChecked){
        Set<OptionDTO> incompatibleOptionsSet = currentOption.getIncompatibleOptionsSet();
        Set<OptionDTO> obligatoryOptionsSet = currentOption.getObligatoryOptionsSet();

        for (OptionDTO option: incompatibleOptionsSet) {
            if( notSelectedOptionIds.contains( option )){
                incompatibleOptionIds.add( String.valueOf(option.getOption_id()) );
            }
        }

        for (OptionDTO option: obligatoryOptionsSet) {
            if( notSelectedOptionIds.contains( option )){
                obligatoryOptionIds.add( String.valueOf(option.getOption_id()) );

                if(isChecked){
                    this.cascadeCheckOptionDependencies(option,
                            incompatibleOptionIds,
                            obligatoryOptionIds,
                            notSelectedOptionIds,
                            isChecked);
                }

            }
        }

    }

}