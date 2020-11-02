package eCare.services.impl;

import eCare.controllers.NewUserRegPageController;
import eCare.dao.api.TariffDao;
import eCare.model.converters.TariffMapper;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.entity.Tariff;
import eCare.mq.MessageSender;
import eCare.services.api.ContractService;
import eCare.services.api.TariffService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TariffService {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    private final TariffDao tariffDaoImpl;
    private final TariffMapper tariffMapper;
    private final ContractService contractServiceImpl;
    private final MessageSender messageSender;

    public TariffServiceImpl(TariffDao tariffDaoImpl, TariffMapper tariffMapper, @Lazy ContractService contractServiceImpl, MessageSender messageSender) {
        this.tariffDaoImpl = tariffDaoImpl;
        this.tariffMapper = tariffMapper;
        this.contractServiceImpl = contractServiceImpl;
        this.messageSender = messageSender;
    }

    @Override
    @Transactional
    public void save(Tariff tariff) {
        try{
            tariffDaoImpl.save(tariff);
            log.info("Tariff with name=" + tariff.getName() + " was successfully saved!");
        }catch(Exception e){
            log.info("There was an error during saving tariff with name=" + tariff.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(Tariff tariff) {
        try{
            tariffDaoImpl.update(tariff);
            log.info("Tariff with name=" + tariff.getName() + " was successfully updated!");
        }catch(Exception e){
            log.info("There was an error during updating tariff with name=" + tariff.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void delete(Tariff tariff) {
        try{
            tariffDaoImpl.delete(tariff);
            log.info("Tariff with name=" + tariff.getName() + " was successfully deleted!");
        }catch(Exception e){
            log.info("There was an error during deleting tariff with name=" + tariff.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Tariff> getTariffByTariffName(String tariffName){
        return tariffDaoImpl.getTariffByTariffName(tariffName);
    }

    @Override
    @Transactional
    public List<TariffDTO> getAllTariffs() {
        return tariffDaoImpl.getAllTariffs()
                .stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TariffDTO getTariffDTOByTariffNameOrNull(String name){
        List<Tariff> listOfTariffs = this.getTariffByTariffName(name);
        if(listOfTariffs.isEmpty()){
            return null;
        }else {
            Tariff tariff = listOfTariffs.get(0);
            return tariffMapper.toDTO(tariff);
        }
    }

    @Override
    @Transactional
    public void convertToEntityAndUpdate(TariffDTO tariffDTO){
        this.update( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    @Transactional
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffMapper.toEntity(tariffDto);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(TariffDTO tariffDTO){
        this.save( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    @Transactional
    public List<TariffDTO> getActiveTariffs() {
        return tariffDaoImpl.getActiveTariffs().stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TariffDTO> searchForTariffDTOByName(String name) {
        return tariffDaoImpl.searchForTariffByName(name).stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public void submitValuesFromController(String blockConnectedContracts, TariffDTO tariffDTO,
                                           String tariffNameBeforeEditing, Set<OptionDTO> availableOptions){
        TariffDTO tariffDTOtoUpdate = this.getTariffDTOByTariffNameOrNull(tariffNameBeforeEditing);
        tariffDTOtoUpdate.setName(tariffDTO.getName());
        tariffDTOtoUpdate.setPrice(tariffDTO.getPrice());
        tariffDTOtoUpdate.setShortDiscription(tariffDTO.getShortDiscription());

        if(availableOptions!=null) {
            tariffDTOtoUpdate.setSetOfOptions(availableOptions);
        }

        if( blockConnectedContracts!=null){
            Set<ContractDTO> contractDTOS = tariffDTOtoUpdate.getSetOfContracts();
            for (ContractDTO contractDTO: contractDTOS) {
                contractDTO.setBlocked(true);
                contractServiceImpl.convertToEntityAndUpdate(contractDTO);
            }
            this.convertToEntityAndUpdate(tariffDTOtoUpdate);
        }else {
            this.convertToEntityAndUpdate(tariffDTOtoUpdate);
        }

        messageSender.sendMessage("update");

        log.info(tariffDTOtoUpdate + " was edited and updated in database.");
    }

}
