package eCare.model.converters;

import eCare.model.dto.AdDTO;
import eCare.model.dto.ContractDTO;
import eCare.model.entity.Ad;
import eCare.model.entity.Contract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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