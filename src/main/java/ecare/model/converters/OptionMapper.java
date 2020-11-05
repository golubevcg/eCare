package ecare.model.converters;

import ecare.model.dto.OptionDTO;
import ecare.model.entity.Option;
import org.modelmapper.ModelMapper;
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
