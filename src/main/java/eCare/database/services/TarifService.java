package eCare.database.services;

import eCare.database.dao.TarifDao;
import eCare.database.entities.Tarif;

public class TarifService {

    TarifDao tarifDao = new TarifDao();

    public void save(Tarif tarif){
        tarifDao.save(tarif);
    }

    public void update(Tarif tarif){
        tarifDao.update(tarif);
    }

    public void delete(Tarif tarif){
        tarifDao.delete(tarif);
    }
}
