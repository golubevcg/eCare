package ecare.dao.api;

import ecare.model.entity.Option;

import java.util.List;
import java.util.Set;

public interface OptionDao {
    void save(Option option);
    void update(Option option);
    void delete(Option option);
    List<Option> getOptionByName(String name);
    List<Option> getOptionById(Long optionId);
    List<Option> searchForOptionByName(String name);
    List<Option> getActiveOptions();

    Set<Option> getParentObligatoryOptions(Long optionDTOid);

    Set<Option> getParentIncompatibleOptions(Long optionDTOid);

    Set<Option> getAllParentDependencies(Long optionDTOid);
}
