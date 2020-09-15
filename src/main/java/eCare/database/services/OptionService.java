package eCare.database.services;

import eCare.database.dao.OptionDao;
import eCare.database.entities.Option;

public class OptionService {

    OptionDao optionDao = new OptionDao();

    public void save(Option option){
        optionDao.save(option);
    }

    public void update(Option option){
        optionDao.update(option);
    }

    public void delete(Option option){
        optionDao.delete(option);
    }
}
