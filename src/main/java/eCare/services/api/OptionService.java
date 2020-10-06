package eCare.services.api;

import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Option;
import java.util.List;

public interface OptionService {
    void save(Option option);
    void delete(Option option);
    void update(Option option);
    List<Option> getOptionByName(String optionName);
    List<OptionDTO> searchForOptionByName(String optionName);
    List<OptionDTO> getActiveOptions();

}
