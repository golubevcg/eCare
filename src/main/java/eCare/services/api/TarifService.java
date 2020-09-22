package eCare.services.api;

import eCare.model.enitity.Tariff;
import java.util.List;

public interface TarifService {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTarifByTarifName(String tarifName);
}
