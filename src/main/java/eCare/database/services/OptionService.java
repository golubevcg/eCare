package eCare.database.services;

import eCare.database.entities.Option;

public class OptionService {

    OptionService optionService = new OptionService();

    public void save(Option option){
        optionService.save(option);
    }

    public void update(Option option){
        optionService.update(option);
    }

    public void delete(Option option){
        optionService.delete(option);
    }
}
