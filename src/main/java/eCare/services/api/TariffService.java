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
    TariffDTO getTariffDTOByTariffnameOrNull(String name);
    Tariff convertDtoToEntity(TariffDTO tariffDto);
    List<TariffDTO> getActiveTariffs();
    List<TariffDTO> searchForTariffDTOByName(String name);
    void convertToEntityAndSave(TariffDTO tariffDTO);
}
