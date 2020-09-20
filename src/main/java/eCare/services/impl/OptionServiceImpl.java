package eCare.services.impl;

import eCare.dao.impl.OptionDaoImpl;
import eCare.model.Option;
import eCare.services.interf.OptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDaoImpl optionDaoImpl;

    @Override
    public void save(Option option){
        optionDaoImpl.save(option);
    }

    @Override
    public void delete(Option option){
        optionDaoImpl.save(option);
    }

    @Override
    public void update(Option option) {
        optionDaoImpl.update(option);}

    @Override
    public List<Option> getOptionByName(String optionName){
        return optionDaoImpl.getOptionByName(optionName);

    }

}
