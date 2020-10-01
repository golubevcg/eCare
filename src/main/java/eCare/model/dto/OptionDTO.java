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
public class OptionDTO implements Comparable{
    private Long option_id;
    private String name;
    private Integer price;
    private Integer connectionCost;
    private String shortDiscription;
    private boolean isActive = true;
    private List<TariffDTO> tariffsOptions = new ArrayList<>();
    private List<OptionDTO> incompatibleOptionsList = new ArrayList<>();
    private List<OptionDTO> obligatoryOptionsList = new ArrayList<>();

    @Override
    public int compareTo(Object o) {
        if (this == o) return 1;
        OptionDTO that = (OptionDTO) o;
        if(option_id>that.option_id){
            return 1;
        }else{
            return 0;
        }
    }


}