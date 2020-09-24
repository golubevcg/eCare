package eCare.services.api;

import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Tariff;
import java.util.List;

public interface TarifService {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTariffByTariffName(String tarifName);
    Object getAllTariffs();
    TariffDTO getTariffDTOByTarifname(String name);
    Tariff convertDtoToEntity(TariffDTO tariffDto);
}
