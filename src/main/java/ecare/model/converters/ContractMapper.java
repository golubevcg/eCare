package ecare.model.converters;

import ecare.model.dto.ContractDTO;
import ecare.model.entity.Contract;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContractMapper {

   final
   ModelMapper modelMapper;

    public ContractMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Contract toEntity(ContractDTO contractDTO){
       return Objects.isNull(contractDTO) ? null : modelMapper.map(contractDTO, Contract.class);
   }

   public ContractDTO toDTO(Contract contract){
       return Objects.isNull(contract) ? null : modelMapper.map(contract, ContractDTO.class);
   }

}
