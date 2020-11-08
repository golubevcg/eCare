package ecare.model.dto;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class TariffDTO implements Comparable<TariffDTO>, Serializable {

    private Long tariff_id;

    @Expose
    private String name;

    @Expose
    private Integer price;

    @Expose
    private String shortDiscription;

    private Set<ContractDTO> setOfContracts = new HashSet<>();

//    @Expose
    private boolean isActive = true;

    @Expose
    private Set<OptionDTO> setOfOptions = new HashSet<>();

    public void addOptionDTO(OptionDTO optionDTO){
        setOfOptions.add(optionDTO);
    }

    @Override
    public int compareTo(TariffDTO o) {
        if (this == o) return 0;
        if(this.getTariff_id()> o.getTariff_id()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "TariffDTO{" +
                "tariff_id=" + tariff_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", shortDiscription='" + shortDiscription + '\'' +
                ", isActive=" + isActive +
                ", setOfOptions=" + setOfOptions +
                '}';
    }
}