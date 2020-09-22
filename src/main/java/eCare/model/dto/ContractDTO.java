package eCare.model.dto;

import eCare.model.enitity.Tariff;
import eCare.model.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
    private String contractNumber;
    private boolean isBlocked;
    private User user;
    private Tariff tariff;
    private boolean isActive = true;
}
