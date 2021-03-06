package ecare.services.impl;

import ecare.dao.api.TariffDao;
import ecare.model.converters.TariffMapper;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.entity.Tariff;
import ecare.mq.MessageSender;
import ecare.services.api.ContractService;
import ecare.services.api.TariffService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TariffService {

    static final Logger log = Logger.getLogger(TariffServiceImpl.class);

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
    public void save(Tariff tariff) {
        tariffDaoImpl.save(tariff);
    }

    @Override
    public void update(Tariff tariff) {
        tariffDaoImpl.update(tariff);

    }

    @Override
    public void delete(Tariff tariff) {
        tariffDaoImpl.delete(tariff);
    }

    @Override
    public List<Tariff> getTariffByTariffName(String tariffName){
        return tariffDaoImpl.getTariffByTariffName(tariffName);
    }

    @Override
    public List<TariffDTO> getAllTariffs() {
        return tariffDaoImpl.getAllTariffs()
                .stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public TariffDTO getTariffDTOByTariffNameOrNull(String name){
        List<Tariff> listOfTariffs = tariffDaoImpl.getTariffByTariffName(name);
        if(listOfTariffs.isEmpty()){
            return null;
        }else {
            Tariff tariff = listOfTariffs.get(0);
            return tariffMapper.toDTO(tariff);
        }
    }

    @Override
    public void convertToEntityAndUpdate(TariffDTO tariffDTO){
        this.update( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffMapper.toEntity(tariffDto);
    }

    @Override
    public void convertToEntityAndSave(TariffDTO tariffDTO){
        tariffDaoImpl.save( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    public List<TariffDTO> getActiveTariffs() {
        return tariffDaoImpl.getActiveTariffs().stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public List<TariffDTO> searchForTariffDTOByName(String name) {
        return tariffDaoImpl.searchForTariffByName(name).stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public void submitValuesFromController(String blockConnectedContracts, TariffDTO tariffDTO,
                                           String tariffNameBeforeEditing, Set<OptionDTO> availableOptions){
        TariffDTO tariffDTOtoUpdate = tariffMapper.toDTO(tariffDaoImpl
                .getTariffByTariffName(tariffNameBeforeEditing).get(0));
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
        }
        tariffDaoImpl.update(tariffMapper.toEntity(tariffDTOtoUpdate));

        messageSender.sendMessage("update");

        log.info(tariffDTOtoUpdate + " was edited and updated in database.");
    }

}
