package eCare.model.dto;

import com.google.gson.annotations.Expose;
import eCare.model.enitity.Contract;
import eCare.model.enitity.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffDTO {

    private Long tariff_id;

    @Expose
    private String name;

    @Expose
    private Integer price;

    @Expose
    private String shortDiscription;

    private Set<ContractDTO> setOfContracts = new HashSet<>();

    @Expose
    private boolean isActive = true;

    private Set<OptionDTO> setOfOptions = new HashSet<>();
}