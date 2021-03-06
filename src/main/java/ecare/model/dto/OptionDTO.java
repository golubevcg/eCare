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
public class OptionDTO implements Comparable<OptionDTO>, Serializable {
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
    private Set<OptionDTO> incompatibleOptionsSet = new HashSet<>();
    private Set<OptionDTO> obligatoryOptionsSet = new HashSet<>();

    @Override
    public int compareTo(OptionDTO o) {
        if (this == o) return 0;
        if(this.getOption_id()> o.getOption_id()){
            return 1;
        }else{
            return -1;
        }
    }

}