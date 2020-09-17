package eCare.database.services;

import eCare.database.dao.TarifDao;
import eCare.database.entities.Tariff;

import java.util.List;

public class TarifService {
    private TarifDao tarifDao = new TarifDao();

    public void save(Tariff tarif) { tarifDao.save(tarif); }

    public void update(Tariff tarif) {
        tarifDao.update(tarif);
    }

    public void delete(Tariff tarif) {
        tarifDao.delete(tarif);
    }

    public List<Tariff> getTarifByTarifName(String tarifName){
        return tarifDao.getTarifByTarifName(tarifName);
    }

}
