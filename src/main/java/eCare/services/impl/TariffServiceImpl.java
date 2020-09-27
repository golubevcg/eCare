package eCare.services.impl;

import eCare.dao.api.TarifDao;
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
    private TarifDao tarifDaoImpl;

    @Autowired
    TariffMapper tariffMapper;

    @Override
    public void save(Tariff tarif) { tarifDaoImpl.save(tarif); }

    @Override
    public void update(Tariff tarif) {
        tarifDaoImpl.update(tarif);
    }

    @Override
    public void delete(Tariff tarif) {
        tarifDaoImpl.delete(tarif);
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
    public TariffDTO getTariffDTOByTariffname(String name){
        Tariff tariff = this.getTariffByTariffName(name).get(0);
        return tariffMapper.toDTO(tariff);
    }

    @Override
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffMapper.toEntity(tariffDto);
    }

    @Override
    public List<TariffDTO> getActiveTariffs() {
        return tarifDaoImpl.getActiveTariffs().stream()
                .map(tariff-> tariffMapper.toDTO(tariff))
                .collect(Collectors.toList());
    }

}
