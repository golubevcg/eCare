package eCare.services.interf;

import eCare.model.Option;
import java.util.List;

public interface OptionService {
    void save(Option option);
    void delete(Option option);
    void update(Option option);
    List<Option> getOptionByName(String optionName);
}
