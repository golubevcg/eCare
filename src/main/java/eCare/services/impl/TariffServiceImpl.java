package eCare.services.impl;

import eCare.dao.impl.TarifDaoImpl;
import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Tariff;
import eCare.model.converters.TariffEntityToTariffDtoConverter;
import eCare.services.api.TarifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TariffServiceImpl implements TarifService {

    @Autowired
    private TarifDaoImpl tarifDaoImpl;

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
    public List<Tariff> getTarifByTarifName(String tarifName){
        return tarifDaoImpl.getTarifByTarifName(tarifName);
    }

    public TariffDTO getTariffDTOByTarifname(String name){
        Tariff tariff = this.getTarifByTarifName(name).get(0);
        return tariffEntityToTariffDtoConverter.convertToDto(tariff);
    }

    public Tariff convertDtoToEntity(TariffDTO tariffDto){
        return tariffEntityToTariffDtoConverter.convertDTOtoEntity(tariffDto);
    }

}
