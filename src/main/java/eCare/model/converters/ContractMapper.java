package eCare.model.converters;

import eCare.model.dto.ContractDTO;
import eCare.model.entity.Contract;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContractMapper {

   @Autowired
   ModelMapper modelMapper;

   public Contract toEntity(ContractDTO contractDTO){
       return Objects.isNull(contractDTO) ? null : modelMapper.map(contractDTO, Contract.class);
   }

   public ContractDTO toDTO(Contract contract){
       return Objects.isNull(contract) ? null : modelMapper.map(contract, ContractDTO.class);
   }

}
