package eCare.services.api;

import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OptionService {
    void save(Option option);
    void delete(Option option);
    void update(Option option);
    List<Option> getOptionByName(String optionName);
    List<OptionDTO> searchForOptionByName(String optionName);
    List<OptionDTO> getActiveOptions();
    OptionDTO getOptionDTOByNameOrNull(String name);

    @Transactional
    OptionDTO getOptionDTOById(Long optionId);

    @Transactional
    List<Option> getOptionById(Long optionId);

    void convertToEntityAndUpdate(OptionDTO optionDTO);

    @Transactional
    Option convertDTOtoEntity(OptionDTO optionDTO);

    @Transactional
    void convertToEntityAndSave(OptionDTO optionDTO);
}
