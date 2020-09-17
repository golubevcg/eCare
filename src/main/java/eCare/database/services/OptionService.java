package eCare.database.services;

import eCare.database.dao.OptionDao;
import eCare.database.entities.Option;

import java.util.List;

public class OptionService {

    private OptionDao optionDao = new OptionDao();

    public void save(Option option){
        optionDao.save(option);
    }

    public void delete(Option option){
        optionDao.save(option);
    }

    public void update(Option option) {optionDao.update(option);}

    public List<Option> getOptionByName(String optionName){
        return optionDao.getOptionByName(optionName);

    }

}
