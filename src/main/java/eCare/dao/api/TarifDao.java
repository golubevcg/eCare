package eCare.dao.api;

import eCare.model.enitity.Tariff;

import java.util.List;

public interface TarifDao {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTarifByTarifName(String tarifName);
}
