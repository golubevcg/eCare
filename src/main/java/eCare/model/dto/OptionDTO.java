package eCare.model.dto;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
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
    private String shortDescription;
    private boolean isActive = true;
    private Set<TariffDTO> tariffsOptions = new HashSet<>();
    private Set<ContractDTO> contractsOptions = new HashSet<>();
    private Set<ContractDTO> setOfContractsInWhichOptionsBlocked = new HashSet<>();
    private Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
    private Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        OptionDTO that = (OptionDTO) o;
        if(this.getOption_id()>that.getOption_id()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "option_id=" + option_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                ", shortDiscription='" + shortDescription + '\'' +
                ", isActive=" + isActive +
                ", tariffsOptions=" + tariffsOptions +
                ", incompatibleOptionsList=" + incompatibleOptionsSet +
                ", obligatoryOptionsList=" + obligatoryOptionsSet +
                '}';
    }
}