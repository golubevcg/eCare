package eCare.model.converters;

import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Option;
import eCare.services.impl.OptionServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class OptionEntityToOptionDTOConverter {

    OptionServiceImpl optionServiceImpl = new OptionServiceImpl();

    public OptionDTO convertToDto(Option option){

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(option.getName());
        optionDTO.setPrice(option.getPrice());
        optionDTO.setConnectionCost(option.getConnectionCost());
        optionDTO.setShortDiscription(option.getShortDiscription());
        optionDTO.setActive(option.isActive());
        optionDTO.setTariffsOptions(option.getTariffsOptions());
        optionDTO.setIncompatibleOptionsList(option.getIncompatibleOptionsList());
        optionDTO.setObligatoryOptionsList(option.getObligatoryOptionsList());

        return optionDTO;
    }


    public Option convertDTOtoEntity(OptionDTO optionDTO){

        Option option = optionServiceImpl.getOptionByName(optionDTO.getName()).get(0);

        option.setPrice(optionDTO.getPrice());
        option.setConnectionCost(optionDTO.getConnectionCost());
        option.setShortDiscription(optionDTO.getShortDiscription());
        option.setActive(optionDTO.isActive());
        option.setTariffsOptions(optionDTO.getTariffsOptions());
        option.setIncompatibleOptionsList(optionDTO.getIncompatibleOptionsList());
        option.setObligatoryOptionsList(optionDTO.getObligatoryOptionsList());

        return option;
    }

}
