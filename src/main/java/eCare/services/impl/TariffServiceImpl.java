package eCare.services.impl;

import eCare.dao.api.TariffDao;
import eCare.model.converters.TariffMapper;
import eCare.model.dto.TariffDTO;
import eCare.model.entity.Tariff;
import eCare.services.api.TariffService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TariffService {

    private final TariffDao tariffDaoImpl;
    private final TariffMapper tariffMapper;

    public TariffServiceImpl(TariffDao tariffDaoImpl, TariffMapper tariffMapper) {
        this.tariffDaoImpl = tariffDaoImpl;
        this.tariffMapper = tariffMapper;
    }

    @Override
    @Transactional
    public void save(Tariff tariff) { tariffDaoImpl.save(tariff); }

    @Override
    @Transactional
    public void update(Tariff tariff) {
        tariffDaoImpl.update(tariff);
    }

    @Override
    @Transactional
    public void delete(Tariff tariff) {
        tariffDaoImpl.delete(tariff);
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
        tariffDaoImpl.update( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    @Transactional
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffMapper.toEntity(tariffDto);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(TariffDTO tariffDTO){
        tariffDaoImpl.save( tariffMapper.toEntity(tariffDTO) );
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

}
