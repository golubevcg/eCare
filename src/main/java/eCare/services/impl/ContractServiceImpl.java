package eCare.services.impl;

import eCare.dao.impl.ContractDaoImpl;
import eCare.model.dto.ContractDTO;
import eCare.model.enitity.Contract;
import eCare.model.converters.ContractMapper;
import eCare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDaoImpl contractDaoImpl;

    @Autowired
    ContractMapper contractMapper;

    @Override
    public void save(Contract contract) {
        contractDaoImpl.save(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDaoImpl.update(contract);
    }

    public void updateConvertDTO(ContractDTO contractDTO) {
        contractDaoImpl.update(contractMapper.toEntity(contractDTO));
    }

    @Override
    public void delete(Contract contract) {
        contractDaoImpl.delete(contract);
    }

    @Override
    public List<Contract> getContractByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number);
    }

    @Override
    public List<Contract> getContractById(Long contractID) {
        return contractDaoImpl.getContractById(contractID);
    }

    public List<ContractDTO> getContractDTOById(Long contractID) {
        return this.getContractById(contractID)
                .stream()
                .map(contract-> contractMapper.toDTO(contract))
                .collect(Collectors.toList());
    }

    public List<ContractDTO> getContractDTOByNumber(String number) {
        return this.getContractByNumber(number)
                .stream()
                .map(c->contractMapper.toDTO(c))
                .collect(Collectors.toList());
    }



    public Contract convertDTOtoEntity(ContractDTO contractDTO){
        return contractMapper.toEntity(contractDTO);
    }

    public void convertToEntityAndSave(ContractDTO contractDTO){
        this.save(contractMapper.toEntity(contractDTO));
    }

}
