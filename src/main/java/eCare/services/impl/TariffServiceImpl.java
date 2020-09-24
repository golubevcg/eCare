package eCare.services.impl;

import eCare.dao.api.TarifDao;
import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Tariff;
import eCare.model.converters.TariffEntityToTariffDtoConverter;
import eCare.services.api.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TariffServiceImpl implements TarifService {

    @Autowired
    private TarifDao tarifDaoImpl;

    @Autowired
    TariffEntityToTariffDtoConverter tariffEntityToTariffDtoConverter;

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
                .map(tariff->tariffEntityToTariffDtoConverter.convertToDto(tariff))
                .collect(Collectors.toList());
    }

    @Override
    public TariffDTO getTariffDTOByTarifname(String name){
        Tariff tariff = this.getTariffByTariffName(name).get(0);
        return tariffEntityToTariffDtoConverter.convertToDto(tariff);
    }

    @Override
    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffEntityToTariffDtoConverter.convertDTOtoEntity(tariffDto);
    }

}
