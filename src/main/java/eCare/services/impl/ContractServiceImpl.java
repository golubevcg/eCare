package eCare.services.impl;

import eCare.dao.impl.ContractDaoImpl;
import eCare.model.dto.ContractDTO;
import eCare.model.enitity.Contract;
import eCare.model.converters.ContractMapper;
import eCare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public void delete(Contract contract) {
        contractDaoImpl.delete(contract);
    }

    @Override
    public List<Contract> getContractByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number);
    }

    public ContractDTO getContractDTOByNumber(String number) {
        Contract contract = this.getContractByNumber(number).get(0);
        return contractMapper.toDTO(contract);
    }

    public Contract convertDTOtoEntity(ContractDTO contractDTO){
        return contractMapper.toEntity(contractDTO);
    }

}
