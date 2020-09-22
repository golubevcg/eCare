package eCare.model.dto;

import eCare.model.enitity.Option;
import eCare.model.enitity.Tariff;
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
public class OptionDTO {
    private String name;
    private Integer price;
    private Integer connectionCost;
    private String shortDiscription;
    private boolean isActive = true;
    private List<Tariff> tariffsOptions = new ArrayList<>();
    private List<Option> incompatibleOptionsList = new ArrayList<>();
    private List<Option> obligatoryOptionsList = new ArrayList<>();
}