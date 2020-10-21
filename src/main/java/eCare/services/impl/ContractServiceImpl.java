package eCare.services.impl;

import eCare.dao.api.ContractDao;
import eCare.model.dto.ContractDTO;
import eCare.model.entity.Contract;
import eCare.model.converters.ContractMapper;
import eCare.services.api.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContractServiceImpl implements ContractService {

    private final ContractDao contractDaoImpl;

    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractDao contractDaoImpl, ContractMapper contractMapper) {
        this.contractDaoImpl = contractDaoImpl;
        this.contractMapper = contractMapper;
    }


    @Override
    @Transactional
    public void save(Contract contract) {
        contractDaoImpl.save(contract);
    }

    @Override
    @Transactional
    public void update(Contract contract) {
        contractDaoImpl.update(contract);
    }

    @Override
    @Transactional
    public void updateConvertDTO(ContractDTO contractDTO) {
        contractDaoImpl.update(contractMapper.toEntity(contractDTO));
    }

    @Override
    @Transactional
    public void delete(Contract contract) {
        contractDaoImpl.delete(contract);
    }

    @Override
    @Transactional
    public List<Contract> getContractByNumber(String number) {
        return contractDaoImpl.getContractByNumber(number);
    }

    @Override
    @Transactional
    public List<Contract> getContractById(Long contractID) {
        return contractDaoImpl.getContractById(contractID);
    }

    @Override
    @Transactional
    public List<ContractDTO> searchForContractByNumber(String searchInput) {
        return contractDaoImpl.searchForContractByNumber(searchInput)
                .stream().map(c->contractMapper.toDTO(c)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ContractDTO> getContractDTOById(Long contractID) {
        return this.getContractById(contractID)
                .stream()
                .map(contract-> contractMapper.toDTO(contract))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ContractDTO> getContractDTOByNumber(String number) {
        return this.getContractByNumber(number)
                .stream()
                .map(c->contractMapper.toDTO(c))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContractDTO getContractDTOByNumberOrNull(String number) {
        List<Contract> contractDTOList = contractDaoImpl.getContractByNumber(number);
        if(contractDTOList==null){
            return null;
        }else{
            return contractMapper.toDTO(contractDTOList.get(0));
        } }

    @Override
    @Transactional
    public void convertToEntityAndUpdate(ContractDTO contractDTO){
        contractDaoImpl.update( contractMapper.toEntity(contractDTO) );
    }


    @Override
    @Transactional
    public Contract convertDTOtoEntity(ContractDTO contractDTO){
        return contractMapper.toEntity(contractDTO);
    }

    @Override
    @Transactional
    public void convertToEntityAndSave(ContractDTO contractDTO){
        this.save(contractMapper.toEntity(contractDTO));
    }

}
