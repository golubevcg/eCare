package eCare.dao.api;

import eCare.model.entity.Tariff;

import java.util.List;

public interface TariffDao {
    void save(Tariff tarif);
    void update(Tariff tarif);
    void delete(Tariff tarif);
    List<Tariff> getTariffByTariffName(String tarifName);
    List<Tariff> getAllTariffs();
    List<Tariff> getActiveTariffs();
    List<Tariff> searchForTariffByName(String name);
}
