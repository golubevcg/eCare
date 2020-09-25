package eCare.services.impl;

import eCare.dao.impl.OptionDaoImpl;
import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Option;
import eCare.model.converters.OptionMapper;
import eCare.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionDaoImpl optionDaoImpl;

    @Autowired
    private OptionMapper optionMapper;

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

    public OptionDTO getOptionDTOByName(String optionName) {
        Option option = this.getOptionByName(optionName).get(0);
        return optionMapper.toDTO(option);
    }

    public Option convertDTOtoEntity(OptionDTO optionDTO){
        return optionMapper.toEntity(optionDTO);
    }


}