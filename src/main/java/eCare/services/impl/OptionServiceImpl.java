package eCare.services.impl;

import eCare.dao.impl.OptionDaoImpl;
import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Option;
import eCare.model.converters.OptionMapper;
import eCare.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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

    @Override
    public List<OptionDTO> searchForOptionByName(String optionName) {
        return optionDaoImpl.searchForOptionByName(optionName).stream()
                .map(o->optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    @Override
    public List<OptionDTO> getActiveOptions() {
        return optionDaoImpl.getActiveOptions().stream().map(o->optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    public OptionDTO getOptionDTOByName(String optionName) {
        Option option = this.getOptionByName(optionName).get(0);
        return optionMapper.toDTO(option);
    }

    public OptionDTO getOptionDTOById(Long optionId) {
        Option option = this.getOptionById(optionId).get(0);
        return optionMapper.toDTO(option);
    }

    private List<Option> getOptionById(Long optionId) {
        return optionDaoImpl.getOptionById(optionId);
    }


    public Option convertDTOtoEntity(OptionDTO optionDTO){
        return optionMapper.toEntity(optionDTO);
    }

    public void convertToEntityAndSave(OptionDTO optionDTO){
        optionDaoImpl.save( optionMapper.toEntity(optionDTO) );
    }


}
