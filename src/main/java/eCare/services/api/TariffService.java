package eCare.services.api;

import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.entity.Tariff;
import java.util.List;
import java.util.Set;

public interface TariffService {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTariffByTariffName(String tarifName);
    Object getAllTariffs();
    TariffDTO getTariffDTOByTariffNameOrNull(String name);
    Tariff convertDtoToEntity(TariffDTO tariffDto);
    List<TariffDTO> getActiveTariffs();
    List<TariffDTO> searchForTariffDTOByName(String name);
    void convertToEntityAndSave(TariffDTO tariffDTO);
    void convertToEntityAndUpdate(TariffDTO tariffDTO);

    void submitValuesFromController(String blockConnectedContracts, TariffDTO tariffDTO,
                                    String tariffNameBeforeEditing, Set<OptionDTO> availableOptions);
}
