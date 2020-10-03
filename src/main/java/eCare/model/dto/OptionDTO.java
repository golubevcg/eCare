package eCare.model.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO implements Comparable{
    @Expose
    private Long option_id;
    @Expose
    private String name;
    @Expose
    private Integer price;
    @Expose
    private Integer connectionCost;
    @Expose
    private String shortDiscription;
    private boolean isActive = true;
    private Set<TariffDTO> tariffsOptions = new HashSet<>();
    private Set<ContractDTO> contractsOptions = new HashSet<>();
    private Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
    private Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();

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

    @Override
    public String toString() {
        return "OptionDTO{" +
                "option_id=" + option_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                ", shortDiscription='" + shortDiscription + '\'' +
                ", isActive=" + isActive +
                ", tariffsOptions=" + tariffsOptions +
                ", incompatibleOptionsList=" + incompatibleOptionsSet +
                ", obligatoryOptionsList=" + obligatoryOptionsSet +
                '}';
    }
}