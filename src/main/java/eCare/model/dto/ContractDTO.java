package eCare.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO implements Comparable{
    private Long contract_id;
    private String contractNumber;
    private boolean isBlocked;
    private UserDTO user;
    private TariffDTO tariff;
    private boolean isActive = true;
    private Set<OptionDTO> setOfOptions = new HashSet<>();

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void addOption(OptionDTO optionDTO){
        setOfOptions.add(optionDTO);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 1;
        ContractDTO that = (ContractDTO) o;
        if(contract_id>that.contract_id){
            return 1;
        }else{
            return 0;
        }
    }
}
