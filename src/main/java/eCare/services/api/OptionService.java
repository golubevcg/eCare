package eCare.services.api;

import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface OptionService {
    void save(Option option);
    void delete(Option option);
    void update(Option option);
    List<Option> getOptionByName(String optionName);
    List<OptionDTO> searchForOptionByName(String optionName);
    List<OptionDTO> getActiveOptionDTOs();

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

    boolean submitValuesFromController(String optionName, OptionDTO optionDTO,
                                       Set<OptionDTO> obligatoryOptionsSet, Set<OptionDTO> incompatibleOptionsSet,
                                       String blockConnectedContracts);

    String getDependedOptionsJson(String oldName);

    String checkIncOptionDependenciesToPreventImpossibleDependency(String expJson, boolean foundedErrorDependency);

    String checkOblOptionDependenciesToPreventImpossibleDependency(String expJson);

    void returnAllObligatoryOptions(Set<OptionDTO> allObligatoryOptionsSet, OptionDTO optionDTO);

    String checkIncOptionDependenciesToPreventRecursion(String expJson);

    Set<OptionDTO> getParentObligatoryOptionDTOs(Long optionDTOid);

    String checkOblOptionDependenciesToPreventRecursion(String expJson);

    Set<OptionDTO> getParentIncompatibleOptionDTOs(Long optionDTOid);

    Set<OptionDTO> getAllParentDependencies(Long optionDTOid);
}
