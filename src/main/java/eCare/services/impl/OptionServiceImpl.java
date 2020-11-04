package eCare.services.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import eCare.controllers.NewUserRegPageController;
import eCare.dao.api.OptionDao;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import eCare.model.converters.OptionMapper;
import eCare.mq.MessageSender;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OptionServiceImpl implements OptionService {

    static final Logger log = Logger.getLogger(NewUserRegPageController.class);

    private final OptionDao optionDaoImpl;

    private final OptionMapper optionMapper;

    private final MessageSender messageSender;

    private final ContractService contractServiceImpl;

    public OptionServiceImpl(OptionDao optionDaoImpl, OptionMapper optionMapper, MessageSender messageSender,
                             @Lazy ContractService contractServiceImpl) {
        this.optionDaoImpl = optionDaoImpl;
        this.optionMapper = optionMapper;
        this.messageSender = messageSender;
        this.contractServiceImpl = contractServiceImpl;
    }

    @Override
    @Transactional
    public void save(Option option) {
        try {
            optionDaoImpl.save(option);
            log.info("Option with name=" + option.getName() + " was successfully saved!");
        } catch (Exception e) {
            log.info("There was an error during saving option with name=" + option.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void delete(Option option) {
        try {
            optionDaoImpl.delete(option);
            log.info("Option with name=" + option.getName() + " was successfully deleted!");
        } catch (Exception e) {
            log.info("There was an error during deleting option with name=" + option.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void update(Option option) {
        try {
            optionDaoImpl.update(option);

            log.info("Option with name=" + option.getName() + " was successfully updated!");
        } catch (Exception e) {
            log.info("There was an error during updating option with name=" + option.getName());
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<Option> getOptionByName(String optionName) {
        return optionDaoImpl.getOptionByName(optionName);
    }

    @Override
    @Transactional
    public List<OptionDTO> searchForOptionByName(String optionName) {
        return optionDaoImpl.searchForOptionByName(optionName).stream()
                .map(o -> optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<OptionDTO> getActiveOptionDTOs() {
        return optionDaoImpl.getActiveOptions().stream().map(o -> optionMapper.toDTO(o)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OptionDTO getOptionDTOByNameOrNull(String optionName) {
        List<Option> listOfOptions = this.getOptionByName(optionName);
        if (listOfOptions.isEmpty()) {
            return null;
        } else {
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

    public void convertToEntityAndUpdate(OptionDTO optionDTO) {
        this.update(optionMapper.toEntity(optionDTO));
    }

    @Override
    @Transactional
    public Option convertDTOtoEntity(OptionDTO optionDTO) {
        return optionMapper.toEntity(optionDTO);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(OptionDTO optionDTO) {
        this.save(optionMapper.toEntity(optionDTO));
    }

    @Override
    public boolean submitValuesFromController(String optionName, OptionDTO optionDTO,
                                              Set<OptionDTO> obligatoryOptionsSet, Set<OptionDTO> incompatibleOptionsSet,
                                              String blockConnectedContracts) {

        OptionDTO optionDTO1 = this.getOptionDTOByNameOrNull(optionName);
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

            if (blockConnectedContracts != null) {

                Set<ContractDTO> contractDTOS = optionDTO1.getContractsOptions();
                for (ContractDTO contractDTO : contractDTOS) {
                    contractDTO.setBlocked(true);
                    contractServiceImpl.convertToEntityAndUpdate(contractDTO);
                }

                this.convertToEntityAndUpdate(optionDTO1);
            } else {
                this.convertToEntityAndUpdate(optionDTO1);
            }

            messageSender.sendMessage("update");
            log.info("Option with name= " + optionDTO1.getName() + " was successfully edited and updated.");
            return true;

        } catch (Exception e) {
            log.info("There was an error with updating values for option with name=" + optionDTO1.getName());
            return false;
        }

    }

    @Override
    public String getDependedOptionsJson(String oldName) {
        OptionDTO optionDTO = this.getOptionDTOByNameOrNull(oldName);
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
    public String checkIncOptionDependenciesToPreventRecursion(String expJson, boolean foundedErrorDependency) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = this.getOptionDTOByNameOrNull(lastSelectedValue);

        String errorOptionName = null;

        foundedErrorDependency = false;
        if (!jsonObject.get("selectedObligOptions").isJsonNull()) {

            JsonArray obligJsonArray = jsonObject.get("selectedObligOptions").getAsJsonArray();
            String[] selectedObligNames = new String[obligJsonArray.size()];

            for (int i = 0; i < selectedObligNames.length; i++) {
                String currentSelectedObligName = obligJsonArray.get(i).getAsString();
                OptionDTO optionDTO = this.getOptionDTOByNameOrNull(currentSelectedObligName);

                if (optionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
                    errorOptionName = optionDTO.getName();
                    return new Gson().toJson(errorOptionName);
                } else {
                    OptionDTO selectedObligOptionDTO = this.getOptionDTOByNameOrNull(currentSelectedObligName);
                    for (OptionDTO entity : optionDTO.getObligatoryOptionsSet()) {
                        recursivlyCheckInObligDependecies(lastSelectedOptionDTO,
                                                        selectedObligOptionDTO, foundedErrorDependency);
                        if (foundedErrorDependency) {
                            errorOptionName = entity.getName();
                            return new Gson().toJson(errorOptionName);
                        }
                    }
                }
            }

        }
        return "";
    }

    public boolean recursivlyCheckInObligDependecies(OptionDTO lastSelectedOptionDTO,
                                                     OptionDTO selectedObligOptionDTO,
                                                     boolean foundedErrorDependency) {
        if (selectedObligOptionDTO.getObligatoryOptionsSet().contains(lastSelectedOptionDTO)) {
            foundedErrorDependency = true;
            return false;
        } else {
            for (OptionDTO entity : selectedObligOptionDTO.getObligatoryOptionsSet()) {
                recursivlyCheckInObligDependecies(lastSelectedOptionDTO, entity, foundedErrorDependency);
            }
        }
        return true;
    }

    @Override
    public String checkOblOptionDependenciesToPreventRecursion(String expJson) {
        JsonObject jsonObject = new Gson().fromJson(expJson, JsonObject.class);

        String lastSelectedValue = jsonObject.get("lastSelectedVal").getAsString();
        OptionDTO lastSelectedOptionDTO = this.getOptionDTOByNameOrNull(lastSelectedValue);

        if (!jsonObject.get("selectedIncOptions").isJsonNull()) {
            JsonArray incJsonArray = jsonObject.get("selectedIncOptions").getAsJsonArray();

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

    private void returnAllObligatoryOptions(Set<OptionDTO> allObligatoryOptionsSet, OptionDTO optionDTO){
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
}
