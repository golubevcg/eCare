package eCare.model.dto;

import eCare.model.enitity.Option;
import eCare.model.enitity.Tariff;
import eCare.model.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private Long contract_id;
    private String contractNumber;
    private boolean isBlocked;
    private UserDTO user;
    private TariffDTO tariff;
    private boolean isActive = true;
    private List<OptionDTO> listOfOptions = new ArrayList<>();
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void addOption(OptionDTO optionDTO){
        listOfOptions.add(optionDTO);
    }
}
