package eCare.services.impl;

import eCare.dao.api.OptionDao;
import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import eCare.model.converters.OptionMapper;
import eCare.services.api.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionServiceImpl implements OptionService {

    private final OptionDao optionDaoImpl;

    private final OptionMapper optionMapper;

    public OptionServiceImpl(OptionDao optionDaoImpl, OptionMapper optionMapper) {
        this.optionDaoImpl = optionDaoImpl;
        this.optionMapper = optionMapper;
    }

    @Override
    @Transactional
    public void save(Option option){
        optionDaoImpl.save(option);
    }

    @Override
    @Transactional
    public void delete(Option option){
        optionDaoImpl.save(option);
    }

    @Override
    @Transactional
    public void update(Option option) {
        optionDaoImpl.update(option);}

    @Override
    @Transactional
    public List<Option> getOptionByName(String optionName){
        return optionDaoImpl.getOptionByName(optionName);
    }

    @Override
    @Transactional
    public List<OptionDTO> searchForOptionByName(String optionName) {
        return optionDaoImpl.searchForOptionByName(optionName).stream()
                .map(o->optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OptionDTO> getActiveOptions() {
        return optionDaoImpl.getActiveOptions().stream().map(o->optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OptionDTO getOptionDTOByNameOrNull(String optionName) {
        List<Option> listOfOptions = this.getOptionByName(optionName);
        if(listOfOptions.isEmpty()){
            return null;
        }else{
            return optionMapper.toDTO(listOfOptions.get(0));
        }
    }

    @Override
    @Transactional
    public OptionDTO getOptionDTOById(Long optionId) {
        Option option = this.getOptionById(optionId).get(0);
        return optionMapper.toDTO(option);
    }

    @Override
    @Transactional
    public List<Option> getOptionById(Long optionId) {
        return optionDaoImpl.getOptionById(optionId);
    }

    public void convertToEntityAndUpdate(OptionDTO optionDTO){
        optionDaoImpl.update(optionMapper.toEntity(optionDTO));
    }

    @Override
    @Transactional
    public Option convertDTOtoEntity(OptionDTO optionDTO){
        return optionMapper.toEntity(optionDTO);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(OptionDTO optionDTO){
        optionDaoImpl.save( optionMapper.toEntity(optionDTO) );
    }


}
