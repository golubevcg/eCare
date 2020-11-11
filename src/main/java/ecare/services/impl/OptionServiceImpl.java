package ecare.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ecare.dao.api.OptionDao;
import ecare.model.dto.ContractDTO;
import ecare.model.dto.OptionDTO;
import ecare.model.dto.TariffDTO;
import ecare.model.entity.Option;
import ecare.model.converters.OptionMapper;
import ecare.mq.MessageSender;
import ecare.services.api.ContractService;
import ecare.services.api.OptionService;
import ecare.services.api.TariffService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
public class OptionServiceImpl implements OptionService {

    static final Logger log = Logger.getLogger(OptionServiceImpl.class);

    private final OptionDao optionDaoImpl;

    private final OptionMapper optionMapper;

    private final MessageSender messageSender;

    private final ContractService contractServiceImpl;

    private final TariffService tariffServiceImpl;

    public OptionServiceImpl(OptionDao optionDaoImpl, OptionMapper optionMapper, MessageSender messageSender,
                             @Lazy ContractService contractServiceImpl, TariffService tariffServiceImpl) {
        this.optionDaoImpl = optionDaoImpl;
        this.optionMapper = optionMapper;
        this.messageSender = messageSender;
        this.contractServiceImpl = contractServiceImpl;
        this.tariffServiceImpl = tariffServiceImpl;
    }

    private final String optionWithName = "Option with name=";

    @Override
    public void save(Option option) {
        try {
            optionDaoImpl.save(option);
            log.info(optionWithName + option.getName() + " was successfully saved!");
        } catch (Exception e) {
            log.info("There was an error during saving option with name=" + option.getName());
        }

    }

    @Override
    public void delete(Option option) {
        try {
            optionDaoImpl.delete(option);
            log.info(optionWithName + option.getName() + " was successfully deleted!");
        } catch (Exception e) {
            log.info("There was an error during deleting option with name=" + option.getName());
        }
    }

    @Override
    public void update(Option option) {
        optionDaoImpl.update(option);

        try {
            log.info(optionWithName + option.getName() + " was successfully updated!");
        } catch (Exception e) {
            log.info("There was an error during updating option with name=" + option.getName());
        }
    }

    @Override
    public List<Option> getOptionByName(String optionName) {
        return optionDaoImpl.getOptionByName(optionName);
    }

    @Override
    public List<OptionDTO> searchForOptionByName(String optionName) {
        return optionDaoImpl.searchForOptionByName(optionName).stream()
                .map(optionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OptionDTO> getActiveOptionDTOs() {
        return optionDaoImpl.getActiveOptions().stream().map(optionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public OptionDTO getOptionDTOByNameOrNull(String optionName) {
        List<Option> listOfOptions = optionDaoImpl.getOptionByName(optionName);
        if (listOfOptions.isEmpty()) {
            return null;
        } else {
            return optionMapper.toDTO(listOfOptions.get(0));
        }
    }

    @Override
    public OptionDTO getOptionDTOById(Long optionId) {
        Option option = optionDaoImpl.getOptionById(optionId).get(0);
        return optionMapper.toDTO(option);
    }

    @Override
    public List<Option> getOptionById(Long optionId) {
        return optionDaoImpl.getOptionById(optionId);
    }

    public void convertToEntityAndUpdate(OptionDTO optionDTO) {
        optionDaoImpl.update(optionMapper.toEntity(optionDTO));
    }

    @Override
    public Option convertDTOtoEntity(OptionDTO optionDTO) {
        return optionMapper.toEntity(optionDTO);
    }

    @Override
    public void convertToEntityAndSave(OptionDTO optionDTO) {
        optionDaoImpl.save(optionMapper.toEntity(optionDTO));
    }

    @Override
    public boolean submitValuesFromController(String optionName, OptionDTO optionDTO,
                                              Set<OptionDTO> obligatoryOptionsSet, Set<OptionDTO> incompatibleOptionsSet,
                                              String blockConnectedContracts) {


        OptionDTO optionDTO1 = optionMapper.toDTO( optionDaoImpl.getOptionByName(optionName).get(0) );
        optionDTO1.setName(optionDTO.getName());
        optionDTO1.setPrice(optionDTO.getPrice());
        optionDTO1.setConnectionCost(optionDTO.getConnectionCost());
        optionDTO1.setShortDescription(optionDTO.getShortDescription());

        if (incompatibleOptionsSet != null) {
            optionDTO1.setIncompatibleOptionsSet(incompatibleOptionsSet);
        }

        if (obligatoryOptionsSet != null) {
            optionDTO1.setObligatoryOptionsSet(obligatoryOptionsSet);
        }

        try {

            if(blockConnectedContracts != null) {
                Set<ContractDTO> contractDTOS = optionDTO1.getContractsOptions();
                for (ContractDTO contractDTO : contractDTOS) {
                    contractDTO.setBlocked(true);
                    contractServiceImpl.convertToEntityAndUpdate(contractDTO);
                }
            }
            optionDaoImpl.update(optionMapper.toEntity(optionDTO1));

            messageSender.sendMessage("update");
            log.info(optionWithName + optionDTO1.getName() + " was successfully edited and updated.");
            updateTariffsConnectedToThisOptions(optionDTO1);
            return true;

        } catch (Exception e) {
            log.info("There was an error with updating values for option with name=" + optionDTO1.getName());
            return false;
        }

    }

    @Override
    public String getDependedOptionsJson(String oldName) {

        OptionDTO optionDTO = optionMapper.toDTO( optionDaoImpl.getOptionByName(oldName).get(0));
        Set<OptionDTO> incompatibleOptionsSet = optionDTO.getIncompatibleOptionsSet();
        Set<String> incompatibleOptionNamesSet = new HashSet<>();

        for (OptionDTO option : incompatibleOptionsSet) {
            incompatibleOptionNamesSet.add(option.getName());
        }

        Set<OptionDTO> obligatoryOptionsSet = optionDTO.getObligatoryOptionsSet();
        Set<String> obligatoryOptionNamesSet = new HashSet<>();
        for (OptionDTO option : obligatoryOptionsSet) {
            obligatoryOptionNamesSet.add(option.getName());
        }

        Set<String>[] array = new HashSet[2];
        array[0] = incompatibleOptionNamesSet;
        array[1] = obligatoryOptionNamesSet;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(array);
    }

    @Override
    public String checkIncOptionDependenciesToPreventImpossibleDependency(String expJson, AtomicBoolean foundedErrorDependency) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = optionMapper.toDTO( optionDaoImpl.getOptionByName(lastSelectedValue).get(0));

        String errorOptionName = null;

        foundedErrorDependency.set(false);
        if (!jsonObject.get("selectedObligOptions").isJsonNull()) {

            JsonArray obligJsonArray = jsonObject.get("selectedObligOptions").getAsJsonArray();
            String[] selectedObligNames = new String[obligJsonArray.size()];

            for (int i = 0; i < selectedObligNames.length; i++) {
                String currentSelectedObligName = obligJsonArray.get(i).getAsString();
                OptionDTO optionDTO = optionMapper.toDTO(
                        optionDaoImpl.getOptionByName(currentSelectedObligName).get(0));

                if (optionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
                    errorOptionName = optionDTO.getName();
                    return new Gson().toJson(errorOptionName);
                } else {
                    OptionDTO selectedObligOptionDTO = this.getOptionDTOByNameOrNull(currentSelectedObligName);
                    for (OptionDTO entity : optionDTO.getObligatoryOptionsSet()) {
                        recursivlyCheckInObligDependecies(lastSelectedOptionDTO,
                                                        selectedObligOptionDTO, foundedErrorDependency);
                        if (foundedErrorDependency.get()) {
                            errorOptionName = entity.getName();
                            return new Gson().toJson(errorOptionName);
                        }
                    }
                }
            }

        }
        return "";
    }

    private final String selectedIncOptionsString = "selectedIncOptions";

    @Override
    public String checkOblOptionDependenciesToPreventImpossibleDependency(String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = this.getOptionDTOByNameOrNull(lastSelectedValue);

        if (!jsonObject.get(selectedIncOptionsString).isJsonNull()) {
            JsonArray incJsonArray = jsonObject.get(selectedIncOptionsString).getAsJsonArray();

            Set<OptionDTO> allObligatoryOptionsSet = lastSelectedOptionDTO.getObligatoryOptionsSet();

            for (OptionDTO entity: allObligatoryOptionsSet) {
                returnAllObligatoryOptions(allObligatoryOptionsSet, entity);
            }

            for (int i = 0; i < incJsonArray.size(); i++) {
                OptionDTO optionDTO = this.getOptionDTOByNameOrNull( incJsonArray.get(i).getAsString() );
                if(allObligatoryOptionsSet.contains(optionDTO)){
                    return new Gson().toJson(optionDTO.getName());
                }
            }

        }
        return "";
    }

    @Override
    public void returnAllObligatoryOptions(Set<OptionDTO> allObligatoryOptionsSet, OptionDTO optionDTO){
        Set<OptionDTO> obligatoryOptionsOfCurrentOptionDTO = optionDTO.getObligatoryOptionsSet();
        if(obligatoryOptionsOfCurrentOptionDTO.size()>0){
            for (OptionDTO entity: obligatoryOptionsOfCurrentOptionDTO) {
                if(!allObligatoryOptionsSet.contains(entity)){
                    allObligatoryOptionsSet.add(entity);
                    returnAllObligatoryOptions(allObligatoryOptionsSet, entity);
                }
            }
        }
    }

    void updateTariffsConnectedToThisOptions(OptionDTO optionDTO){
        Set<TariffDTO> tariffDTOSet = optionDTO.getTariffsOptions();
        Set<OptionDTO> obligatoryOptionsSet = optionDTO.getObligatoryOptionsSet();

        for (TariffDTO tariffEntity: tariffDTOSet) {
            for (OptionDTO optionEntity: obligatoryOptionsSet) {
                tariffEntity.addOptionDTO(optionEntity);
            }
            tariffServiceImpl.convertToEntityAndUpdate(tariffEntity);
        }
        log.info("Tariffs with option name= " + optionDTO.getName() + " were successfully updated.");

    }

    public boolean recursivlyCheckInObligDependecies(OptionDTO lastSelectedOptionDTO,
                                                     OptionDTO selectedObligOptionDTO,
                                                     AtomicBoolean foundedErrorDependency) {
        if (selectedObligOptionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
            foundedErrorDependency.set(true);
            return false;
        } else {
            for (OptionDTO entity : selectedObligOptionDTO.getObligatoryOptionsSet()) {
                recursivlyCheckInObligDependecies(lastSelectedOptionDTO, entity, foundedErrorDependency);
            }
        }
        return true;
    }

    public boolean recursivlyCheckInIncDependecies(OptionDTO lastSelectedOptionDTO,
                                                   OptionDTO selectedIncompOptionDTO,
                                                   AtomicBoolean foundedErrorDependency) {
        if (selectedIncompOptionDTO.getIncompatibleOptionsSet().contains(lastSelectedOptionDTO)) {
            foundedErrorDependency.set(true);
            return false;
        } else {
            for (OptionDTO entity : selectedIncompOptionDTO.getIncompatibleOptionsSet()) {
                recursivlyCheckInObligDependecies(lastSelectedOptionDTO, entity, foundedErrorDependency);
            }
        }
        return true;
    }

    @Override
    public String checkIncOptionDependenciesToPreventRecursion(String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String currentlyCheckedOptionId = jsonObject.get("currentlyCheckedOptionId").getAsString();

        OptionDTO lastSelectedOptionDTO = optionMapper.toDTO(optionDaoImpl
                .getOptionById(Long.parseLong(currentlyCheckedOptionId)).get(0));
        if (!jsonObject.get(selectedIncOptionsString).isJsonNull()) {
            JsonArray incJsonArray = jsonObject.get(selectedIncOptionsString).getAsJsonArray();

            Set<OptionDTO> parentObligatoryOptionDTOs = getAllParentDependencies(lastSelectedOptionDTO.getOption_id());

            for (int i = 0; i < incJsonArray.size(); i++) {
                OptionDTO optionDTO = this.getOptionDTOByNameOrNull( incJsonArray.get(i).getAsString() );

                if(parentObligatoryOptionDTOs.contains(optionDTO)) {
                    return new Gson().toJson(optionDTO.getName());
                }
            }

        }

        return "";
        }

    @Override
    public Set<OptionDTO> getParentObligatoryOptionDTOs(Long optionDTOid){
        return new HashSet<>(optionDaoImpl
                .getParentObligatoryOptions(optionDTOid)
                .stream()
                .map(optionMapper::toDTO)
                .collect(Collectors.toList()));

    }

    @Override
    public String checkOblOptionDependenciesToPreventRecursion(String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String currentlyCheckedOptionId = jsonObject.get("currentlyCheckedOptionId").getAsString();
        OptionDTO lastSelectedOptionDTO = optionMapper.toDTO(optionDaoImpl
                .getOptionById(Long.parseLong(currentlyCheckedOptionId)).get(0));
        if (!jsonObject.get("selectedOblOptions").isJsonNull()) {
            JsonArray incJsonArray = jsonObject.get("selectedOblOptions").getAsJsonArray();
            Set<OptionDTO> parentIncOptionDTOs = getAllParentDependencies(lastSelectedOptionDTO.getOption_id());

            for (int i = 0; i < incJsonArray.size(); i++) {
                OptionDTO optionDTO = this.getOptionDTOByNameOrNull( incJsonArray.get(i).getAsString() );

                if(parentIncOptionDTOs.contains(optionDTO)) {
                    return new Gson().toJson(optionDTO.getName());
                }
            }

        }
        return "";
    }

    @Override
    public Set<OptionDTO> getParentIncompatibleOptionDTOs(Long optionDTOid){
        return new HashSet<>(optionDaoImpl
                .getParentIncompatibleOptions(optionDTOid)
                .stream()
                .map(optionMapper::toDTO)
                .collect(Collectors.toList()));

    }

    @Override
    public Set<OptionDTO> getAllParentDependencies(Long optionDTOid){
      return new HashSet<>(optionDaoImpl
          .getAllParentDependencies(optionDTOid)
            .stream()
                .map(optionMapper::toDTO)
                .collect(Collectors.toList()));
    }
}
