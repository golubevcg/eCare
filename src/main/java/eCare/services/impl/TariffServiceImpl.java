package eCare.services.impl;

import eCare.dao.api.TariffDao;
import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Tariff;
import eCare.model.converters.TariffMapper;
import eCare.services.api.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TariffService {

    @Autowired
    private TariffDao tarifDaoImpl;

    @Autowired
    TariffMapper tariffMapper;

    @Override
    public void save(Tariff tariff) { tarifDaoImpl.save(tariff); }

    @Override
    public void update(Tariff tariff) {
        tarifDaoImpl.update(tariff);
    }

    @Override
    public void delete(Tariff tariff) {
        tarifDaoImpl.delete(tariff);
    }

    @Override
    public List<Tariff> getTariffByTariffName(String tariffName){
        return tarifDaoImpl.getTariffByTariffName(tariffName);
    }

    @Override
    public List<TariffDTO> getAllTariffs() {
        return tarifDaoImpl.getAllTariffs()
                .stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public TariffDTO getTariffDTOByTariffnameOrNull(String name){
        List<Tariff> listOfTariffs = this.getTariffByTariffName(name);
        if(listOfTariffs.isEmpty()){
            return null;
        }else {
            Tariff tariff = listOfTariffs.get(0);
            return tariffMapper.toDTO(tariff);
        }
    }

    @Override
    public void convertToEntityAndUpdate(TariffDTO tariffDTO){
        tarifDaoImpl.update( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffMapper.toEntity(tariffDto);
    }

    public void convertToEntityAndSave(TariffDTO tariffDTO){
        tarifDaoImpl.save( tariffMapper.toEntity(tariffDTO) );
    }

    @Override
    public List<TariffDTO> getActiveTariffs() {
        return tarifDaoImpl.getActiveTariffs().stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public List<TariffDTO> searchForTariffDTOByName(String name) {
        return tarifDaoImpl.searchForTariffByName(name).stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

}
