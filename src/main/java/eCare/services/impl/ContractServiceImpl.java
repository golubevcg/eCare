package eCare.services.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eCare.controllers.EntrancePageController;
import eCare.dao.api.ContractDao;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.entity.Contract;
import eCare.model.converters.ContractMapper;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractServiceImpl implements ContractService {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    private final ContractDao contractDaoImpl;

    private final ContractMapper contractMapper;

    final
    UserService userServiceImpl;

    final
    TariffService tariffServiceImpl;

    final
    OptionService optionServiceImpl;


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
            log.info("Contract with number=" + contract.getContractNumber() + " was successfully saved.");
        }catch(Exception e){
            log.info("There was an error, during the saving of contract with number="
                    + contract.getContractNumber() + ".");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Contract contract) {
        try{
            contractDaoImpl.update(contract);
            log.info("Contract with number=" + contract.getContractNumber() + " was successfully updated.");
        }catch(Exception e){
            log.info("There was an error, during the updating of contract with number="
                    + contract.getContractNumber() + ".");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Contract contract) {
        try{
            contractDaoImpl.delete(contract);
            log.info("Contract with number=" + contract.getContractNumber() + " was successfully deleted.");
        }catch(Exception e){
            log.info("There was an error, during the deleting of contract with number="
                    + contract.getContractNumber() + ".");
            e.printStackTrace();
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
                .stream().map(c->contractMapper.toDTO(c)).collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractDTOById(Long contractID) {
        return this.getContractById(contractID)
                .stream()
                .map(contract-> contractMapper.toDTO(contract))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractDTOByNumber(String number) {
        return this.getContractByNumber(number)
                .stream()
                .map(c->contractMapper.toDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    public ContractDTO getContractDTOByNumberOrNull(String number) {
        List<Contract> contractDTOList = contractDaoImpl.getContractByNumber(number);
        if(contractDTOList==null){
            return null;
        }else{
            return contractMapper.toDTO(contractDTOList.get(0));
        }
    }

    @Override
    public void convertToEntityAndUpdate(ContractDTO contractDTO){
        try{
            contractDaoImpl.update( contractMapper.toEntity(contractDTO) );
            log.info("Contract with number=" + contractDTO.getContractNumber()
                    + " was successfully converted and updated.");
        }catch(Exception e){
            log.info("There was an error, during the converting and updating of contract with number="
                    + contractDTO.getContractNumber() + ".");
            e.printStackTrace();
        }
    }


    @Override
    public Contract convertDTOtoEntity(ContractDTO contractDTO){
        return contractMapper.toEntity(contractDTO);
    }

    @Override
    public void convertToEntityAndSave(ContractDTO contractDTO){
        try{
            this.save(contractMapper.toEntity(contractDTO));
            log.info("Contract with number=" + contractDTO.getContractNumber()
                    + " was successfully converted and saved.");
        }catch(Exception e){
            log.info("There was an error, during the converting and saving of contract with number="
                    + contractDTO.getContractNumber() + ".");
            e.printStackTrace();
        }
    }

    @Override
    public boolean submitValuesFromController(String exportArray, String contractNumberBeforeEditing){
        JsonObject jsonObject = JsonParser.parseString(exportArray).getAsJsonObject();

        ContractDTO contractDTO = this.getContractDTOByNumber(contractNumberBeforeEditing).get(0);

        String number = jsonObject.get("newNum").getAsString();
        String selectedUserLogin = jsonObject.get("selectedUserLogin").getAsString();;
        String tariff = jsonObject.get("selectedTariff").getAsString();
        Boolean isBlocked = jsonObject.get("isContractBlocked").getAsBoolean();

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
            this.convertToEntityAndUpdate(contractDTO);
            log.info(contractDTO.getContractNumber() + "was successfully edited and updated.");
            return true;
        }catch (Exception e){
            log.info(contractDTO.getContractNumber() + "there was problem with submitting this contract.");
            return false;
        }

    }

}
