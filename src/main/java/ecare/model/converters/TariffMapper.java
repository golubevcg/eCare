package ecare.model.converters;

import ecare.model.dto.TariffDTO;
import ecare.model.entity.Tariff;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(value = "prototype")
public class TariffMapper {

    private final ModelMapper modelMapper;

    public TariffMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tariff toEntity(TariffDTO tariffDTO){
        return Objects.isNull(tariffDTO) ? null : modelMapper.map(tariffDTO, Tariff.class);
    }

    public TariffDTO toDTO(Tariff tariff){
        return Objects.isNull(tariff) ? null : modelMapper.map(tariff, TariffDTO.class);
    }


}
