package eCare.model.dto;

import eCare.model.enitity.Tariff;
import eCare.model.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private String contractNumber;
    private boolean isBlocked;
    private UserDTO user;
    private Tariff tariff;
    private boolean isActive = true;

    public ContractDTO(String contractNumber, UserDTO userDTO) {
        this.contractNumber = contractNumber;
        this.user = userDTO;
    }

    public String getContractNumber() {
        return contractNumber;
    }
}
