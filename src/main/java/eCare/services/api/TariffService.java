package eCare.services.api;

import eCare.model.dto.TariffDTO;
import eCare.model.enitity.Tariff;
import java.util.List;

public interface TariffService {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTariffByTariffName(String tarifName);
    Object getAllTariffs();
    TariffDTO getTariffDTOByTariffname(String name);
    Tariff convertDtoToEntity(TariffDTO tariffDto);
    List<Tariff> getActiveTariffs();
}
