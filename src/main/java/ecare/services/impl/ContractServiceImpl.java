package ecare.services.impl;

import com.google.gson.*;
import ecare.dao.api.ContractDao;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.Contract;
import ecare.model.converters.ContractMapper;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import ecare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContractServiceImpl implements ContractService {

    static final Logger log = Logger.getLogger(ContractServiceImpl.class);

    private final ContractDao contractDaoImpl;

    private final ContractMapper contractMapper;

    final
    UserService userServiceImpl;

    final
    TariffService tariffServiceImpl;

    final
    OptionService optionServiceImpl;

    String contractWithNumber = "Contract with number=";


    public ContractServiceImpl(ContractDao contractDaoImpl, ContractMapper contractMapper,
                               UserService userServiceImpl, TariffService tariffServiceImpl,
                               OptionService optionServiceImpl) {
        this.contractDaoImpl = contractDaoImpl;
        this.contractMapper = contractMapper;
        this.userServiceImpl = userServiceImpl;
        this.tariffServiceImpl = tariffServiceImpl;
        this.optionServiceImpl = optionServiceImpl;
    }


    @Override
    public void save(Contract contract) {
        try {
            contractDaoImpl.save(contract);
            log.info(contractWithNumber + contract.getContractNumber() + " was successfully saved.");
        }catch(Exception e){
            log.info("There was an error, during the saving of contract with number="
                    + contract.getContractNumber() + ".");
        }
    }

    @Override
    public void update(Contract contract) {
        try{
            contractDaoImpl.update(contract);
            log.info(contractWithNumber + contract.getContractNumber() + " was successfully updated.");
        }catch(Exception e){
            log.info("There was an error, during the updating of contract with number="
                    + contract.getContractNumber() + ".");
        }
    }

    @Override
    public void delete(Contract contract) {
        try{
            contractDaoImpl.delete(contract);
            log.info(contractWithNumber + contract.getContractNumber() + " was successfully deleted.");
        }catch(Exception e){
            log.info("There was an error, during the deleting of contract with number="
                    + contract.getContractNumber() + ".");
        }
    }

    @Override
    public List<Contract> getContractByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number);
    }

    @Override
    public List<Contract> getContractById(Long contractID) {
        return contractDaoImpl.getContractById(contractID);
    }

    @Override
    public List<ContractDTO> searchForContractByNumber(String searchInput) {
        return contractDaoImpl.searchForContractByNumber(searchInput)
                .stream().map(contractMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractDTOById(Long contractID) {
        return contractDaoImpl.getContractById(contractID)
                .stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractDTOByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number)
                .stream()
                .map(contractMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContractDTO getContractDTOByNumberOrNull(String number) {
        List<Contract> contractsList = contractDaoImpl.getContractByNumber(number);
        if(contractsList.isEmpty()){
            return null;
        }else{
            return contractMapper.toDTO(contractsList.get(0));
        }
    }

    @Override
    public void convertToEntityAndUpdate(ContractDTO contractDTO){
        try{
            contractDaoImpl.update( contractMapper.toEntity(contractDTO) );
            log.info(contractWithNumber + contractDTO.getContractNumber()
                    + " was successfully converted and updated.");
        }catch(Exception e){
            log.info("There was an error, during the converting and updating of contract with number="
                    + contractDTO.getContractNumber() + ".");
        }
    }


    @Override
    public Contract convertDTOtoEntity(ContractDTO contractDTO){
        return contractMapper.toEntity(contractDTO);
    }

    @Override
    public void convertToEntityAndSave(ContractDTO contractDTO){
        try{
            contractDaoImpl.save(contractMapper.toEntity(contractDTO));
            log.info(contractWithNumber + contractDTO.getContractNumber()
                    + " was successfully converted and saved.");
        }catch(Exception e){
            log.info("There was an error, during the converting and saving of contract with number="
                    + contractDTO.getContractNumber() + ".");
        }
    }

    @Override
    public boolean submitValuesFromController(String exportArray, String contractNumberBeforeEditing){
        JsonObject jsonObject = JsonParser.parseString(exportArray).getAsJsonObject();

        List<Contract> contractsList = contractDaoImpl.getContractByNumber(contractNumberBeforeEditing);

        ContractDTO contractDTO = contractMapper.toDTO( contractsList.get(0) );

        String number = jsonObject.get("newNum").getAsString();
        String selectedUserLogin = jsonObject.get("selectedUserLogin").getAsString();
        String tariff = jsonObject.get("selectedTariff").getAsString();
        boolean isBlocked = jsonObject.get("isContractBlocked").getAsBoolean();

        JsonArray jsonArrayTest = jsonObject.get("selectedOptions").getAsJsonArray();

        contractDTO.setContractNumber(number);
        UserDTO userDTO = userServiceImpl.getUserDTOByLoginOrNull(selectedUserLogin);
        contractDTO.setUser(userDTO);
        TariffDTO tariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(tariff);
        contractDTO.setTariff(tariffDTO);

        if(jsonArrayTest.size()!=0) {
            for (int i = 0; i < jsonArrayTest.size(); i++) {
                contractDTO.addOption(optionServiceImpl.getOptionDTOByNameOrNull(jsonArrayTest.get(i).getAsString()));
            }
        }

        contractDTO.setBlocked(isBlocked);
        try{
            contractDaoImpl.update( contractMapper.toEntity(contractDTO) );
            log.info(contractDTO.getContractNumber() + "was successfully edited and updated.");
            return true;
        }catch (Exception e){
            log.info(contractDTO.getContractNumber() + "there was problem with submitting this contract.");
            return false;
        }

    }

    /**
     *
     This method used for returning two arrays as json - first with arrays of id's for incompatible options,
     second for obligatory, they will be parsed in front end for enabling/disabling dependent options.
     And with error message if child options blocked for enabling/disabling.
     */
    @Override
    public String getDependingOptions(HttpSession session, String selectedOptionId, String[] stringsArrayInfoFromFront){

        OptionDTO changedOptionDTO = optionServiceImpl.getOptionDTOById( Long.valueOf(selectedOptionId) );
        TariffDTO selectedTariffDTO = tariffServiceImpl.getTariffDTOByTariffNameOrNull(stringsArrayInfoFromFront[0]);
        Boolean isChecked = Boolean.valueOf(stringsArrayInfoFromFront[1]);
        String contractNumber = stringsArrayInfoFromFront[4];

        String[] blockedOptionIdsArray = stringsArrayInfoFromFront[2].split(",");
        Set<String> blockedOptionIdsSet = new HashSet<>(Arrays.asList(blockedOptionIdsArray));

        String[] checkedOptionIdsArray = stringsArrayInfoFromFront[3].split(",");
        Set<String> checkedOptionIdsSet = new HashSet<>(Arrays.asList(checkedOptionIdsArray));

        Set<OptionDTO> notSelectedOptionsSet = selectedTariffDTO.getSetOfOptions();
        notSelectedOptionsSet.remove(changedOptionDTO);
        Set<String> incompatibleOptionIds = new HashSet<>();
        Set<String> obligatoryOptionIds = new HashSet<>();

        this.cascadeCheckOptionDependencies(changedOptionDTO, incompatibleOptionIds,
                obligatoryOptionIds, notSelectedOptionsSet, isChecked);

        Set<String>[] array = new HashSet[3];
        array[0]=incompatibleOptionIds;
        array[1]=obligatoryOptionIds;

        StringBuilder obligatoryErrorMessage = new StringBuilder();
        fillObligatoryOptionsMessageWithNamesBasedOnOptionId(blockedOptionIdsSet,
                                                checkedOptionIdsSet, obligatoryOptionIds, obligatoryErrorMessage);

        StringBuilder incompatibleErrorMessage = new StringBuilder();

        fillIncompatibleOptionsMessageWithNamesBasedOnOptionId(blockedOptionIdsSet,
                                                checkedOptionIdsSet, incompatibleOptionIds, incompatibleErrorMessage);

        Set<String> errorMessageSet = new HashSet<>();
        String finalErrorMessage = obligatoryErrorMessage.toString() + incompatibleErrorMessage.toString();
        errorMessageSet.add(finalErrorMessage);
        array[2]= errorMessageSet;

        if(finalErrorMessage.length()==0){
            ContractDTO currentContractForCartFromSession = null;
            HashSet<ContractDTO> cartContractsSetChangedForCart
                    = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");

            checkContractsAndIfNeededAddThemToSessionAttributes(session, selectedOptionId,
                    isChecked, contractNumber, incompatibleOptionIds,
                    obligatoryOptionIds, cartContractsSetChangedForCart);
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(array);
}

    private void checkContractsAndIfNeededAddThemToSessionAttributes(HttpSession session,
                                                                     String selectedOptionId,
                                                                     Boolean isChecked,
                                                                     String contractNumber,
                                                                     Set<String> incompatibleOptionIds,
                                                                     Set<String> obligatoryOptionIds,
                                                                     HashSet<ContractDTO>
                                                                             cartContractsSetChangedForCart) {
        ContractDTO currentContractForCartFromSession;
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContractNumber().equals(contractNumber)){
                currentContractForCartFromSession = contractDTO;

                OptionDTO optionDTO = optionServiceImpl.getOptionDTOById(Long.valueOf(selectedOptionId));

                ifCheckedAddOptionToContractOrRemove(isChecked, currentContractForCartFromSession, optionDTO);

                addIncompatibleOptionToCurrentContractFromSessionBasedOnId(incompatibleOptionIds,
                        currentContractForCartFromSession);

                addObligatoryOptionToCurrentContractFromSessionBasedOnId(obligatoryOptionIds,
                        currentContractForCartFromSession);

                cartContractsSetChangedForCart.add(currentContractForCartFromSession);
                session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);
            }
        }
    }

    private void fillObligatoryOptionsMessageWithNamesBasedOnOptionId(Set<String> blockedOptionIdsSet,
                                                                        Set<String> checkedOptionIdsSet,
                                                                        Set<String> obligatoryOptionIds,
                                                                        StringBuilder obligatoryErrorMessage) {
        for (String obligOptId: obligatoryOptionIds) {
            if(!checkedOptionIdsSet.contains(obligOptId) && blockedOptionIdsSet.contains(obligOptId)){
                obligatoryErrorMessage
                        .append(optionServiceImpl.getOptionDTOById(Long.valueOf(obligOptId)).getName() + " ");
            }
        }
    }

    private void fillIncompatibleOptionsMessageWithNamesBasedOnOptionId(Set<String> blockedOptionIdsSet,
                                                                          Set<String> checkedOptionIdsSet,
                                                                          Set<String> incompatibleOptionIds,
                                                                          StringBuilder incompatibleErrorMessage) {
        for (String incompOptId: incompatibleOptionIds) {
            if(checkedOptionIdsSet.contains(incompOptId) && blockedOptionIdsSet.contains(incompOptId)){
                incompatibleErrorMessage
                        .append(optionServiceImpl.getOptionDTOById(Long.valueOf(incompOptId)).getName() + " ");
            }
        }
    }

    private void ifCheckedAddOptionToContractOrRemove(Boolean isChecked, ContractDTO currentContractForCartFromSession, OptionDTO optionDTO) {
        if(isChecked){
            currentContractForCartFromSession.addOption(optionDTO);
        }else{
            currentContractForCartFromSession.removeOption(optionDTO);
        }
    }

    private void addIncompatibleOptionToCurrentContractFromSessionBasedOnId(Set<String> incompatibleOptionIds, ContractDTO currentContractForCartFromSession) {
        for (String optionIdi: incompatibleOptionIds) {
            OptionDTO optionDTOInc = optionServiceImpl.getOptionDTOById(Long.valueOf(optionIdi));
            currentContractForCartFromSession.removeOption(optionDTOInc);
        }
    }

    private void addObligatoryOptionToCurrentContractFromSessionBasedOnId(Set<String> obligatoryOptionIds, ContractDTO currentContractForCartFromSession) {
        for (String optionIdo: obligatoryOptionIds) {
            OptionDTO optionDTOObl = optionServiceImpl.getOptionDTOById(Long.valueOf(optionIdo));
            currentContractForCartFromSession.addOption(optionDTOObl);
        }
    }

    /**
     * This function for checking depending options in child options (recursively)
     */
    public void cascadeCheckOptionDependencies(OptionDTO currentOption,
                                               Set<String> incompatibleOptionIds,
                                               Set<String> obligatoryOptionIds,
                                               Set<OptionDTO> notSelectedOptionIds,
                                               Boolean isChecked) {

        Set<OptionDTO> incompatibleOptionsSet = currentOption.getIncompatibleOptionsSet();
        Set<OptionDTO> obligatoryOptionsSet = currentOption.getObligatoryOptionsSet();

        for (OptionDTO option : incompatibleOptionsSet) {
            if (notSelectedOptionIds.contains(option)) {
                incompatibleOptionIds.add(String.valueOf(option.getOption_id()));
            }
        }

        for (OptionDTO option : obligatoryOptionsSet) {
            if (notSelectedOptionIds.contains(option)) {
                obligatoryOptionIds.add(String.valueOf(option.getOption_id()));

                if (isChecked) {
                    this.cascadeCheckOptionDependencies(option,
                            incompatibleOptionIds,
                            obligatoryOptionIds,
                            notSelectedOptionIds,
                            isChecked);
                }

            }
        }
    }

    @Override
    public String getSortedListOfOptions(String contractNumber, String selectedTariffName, HttpSession session){
        ContractDTO currentContractForCartFromSession = null;
        HashSet<ContractDTO> cartContractsSetChangedForCart
                = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
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

        if(currentContractForCartFromSession!=null){
            currentContractForCartFromSession.setTariff(tariffDTO);
            currentContractForCartFromSession.setSetOfOptions(new HashSet<>());
        }
        cartContractsSetChangedForCart.add(currentContractForCartFromSession);
        session.setAttribute("cartContractsSetChangedForCart", cartContractsSetChangedForCart);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(sortedListOfOptions);
    }

    @Override
    public void addContractDetailsToModelForPage(Model model, String contractID,
                                                 HttpSession session){
        ContractDTO currentContractForCartFromSession = getCurrentContractFromSessionByContractId(session,contractID);

        UserDTO userBeforeEditing = currentContractForCartFromSession.getUser();

        model.addAttribute("contractNumber", currentContractForCartFromSession.getContractNumber());
        model.addAttribute("firstAndSecondNames",
                userBeforeEditing.getFirstname() + " " + userBeforeEditing.getSecondname());

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
                if (availableOption.getOption_id().equals(connectedOption.getOption_id()))
                    isOptionInContract = true;
            }

            enabledOptionsDTOMap.put(availableOption, isOptionInContract);
        }

        model.addAttribute("enabledOptionsDTOMap", enabledOptionsDTOMap);

        List<TariffDTO> activeTariffsList = tariffServiceImpl.getActiveTariffs();
        model.addAttribute("activeTariffsList", activeTariffsList);
        model.addAttribute("connectedOptions", optionDTOLinkedHashSet);
        model.addAttribute("isBlocked", currentContractForCartFromSession.isBlocked());
    }

    public ContractDTO getCurrentContractFromSessionByContractId(HttpSession session, String contractID){
        ContractDTO currentContractForCartFromSession = null;
        HashSet<ContractDTO> cartContractsSetChangedForCart
                = (HashSet<ContractDTO>) session.getAttribute("cartContractsSetChangedForCart");
        for (ContractDTO contractDTO: cartContractsSetChangedForCart) {
            if(contractDTO.getContract_id().toString().equals(contractID)){
                currentContractForCartFromSession = contractDTO;
            }
        }

        return currentContractForCartFromSession;
    }

    @Override
    public void validateContractNumberFromController(String contractNumber, BindingResult bindingResult, Model model){
        if(contractNumber.isEmpty()){
            bindingResult.addError(new ObjectError("phoneNumberEmptyError",
                    "This field is required."));
            model.addAttribute("phoneNumberEmptyError", "This field is required.");
        }else{
            if(!contractNumber.matches("[+]*([0-9]{11})")){
                bindingResult.addError(new ObjectError("phoneNumberPatterError",
                        "Phone number should look like this: +7XXXXXXXXXX."));
                model.addAttribute("phoneNumberPatternError", "Phone number should look like this: +7XXXXXXXXXX.");
            }
        }
    }

    @Override
    public void validateLoginFromController(String selectedLogin, BindingResult bindingResult, Model model){
        if(selectedLogin.isEmpty()){
            bindingResult.addError(new ObjectError("userList",
                    "Please select existing user from drop-down list"));
            model.addAttribute("selectedUserError", "Please select existing user.");
        }else{
            if(userServiceImpl.getUserByLogin(selectedLogin).size()==0){
                bindingResult.addError(new ObjectError("userList",
                        "Please select existing user from drop-down list"));
                model.addAttribute("selectedUserError", "Please select existing user.");
            }
        }
    }


}
