package eCare.services.impl;

import eCare.dao.impl.TarifDaoImpl;
import eCare.model.Tariff;
import eCare.services.interf.TarifService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TarifServiceImpl implements TarifService {

    @Autowired
    private TarifDaoImpl tarifDaoImpl;

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

}
