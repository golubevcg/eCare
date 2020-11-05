package ecare.model.converters;

import ecare.model.dto.AdDTO;
import ecare.model.entity.Ad;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AdMapper {

    final
    ModelMapper modelMapper;

    public AdMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Ad toEntity(AdDTO adDTO){
        return Objects.isNull(adDTO) ? null : modelMapper.map(adDTO, Ad.class);
    }

    public AdDTO toDTO(Ad ad){
        return Objects.isNull(ad) ? null : modelMapper.map(ad, AdDTO.class);
    }

}