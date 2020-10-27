package eCare.model.converters;

import eCare.model.dto.OptionDTO;
import eCare.model.entity.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OptionMapper {

    private final ModelMapper modelMapper;

    public OptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Option toEntity(OptionDTO optionDTO){
        return Objects.isNull(optionDTO) ? null : modelMapper.map(optionDTO, Option.class);
    }

    public OptionDTO toDTO(Option option){
        return Objects.isNull(option) ? null : modelMapper.map(option, OptionDTO.class);
    }

}
