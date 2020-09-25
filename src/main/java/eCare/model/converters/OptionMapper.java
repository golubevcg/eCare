package eCare.model.converters;

import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OptionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Option toEntity(OptionDTO optionDTO){
        return Objects.isNull(optionDTO) ? null : modelMapper.map(optionDTO, Option.class);
    }

    public OptionDTO toDTO(Option option){
        return Objects.isNull(option) ? null : modelMapper.map(option, OptionDTO.class);
    }

}
