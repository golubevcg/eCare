package eCare.model.dto;

import eCare.model.enitity.Contract;
import eCare.model.enitity.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffDTO {
    private Long tariff_id;
    private String name;
    private Integer price;
    private String shortDiscription;
    private List<ContractDTO> listOfContracts = new ArrayList<>();
    private boolean isActive = true;
    private List<OptionDTO> listOfOptions = new ArrayList<>();
}