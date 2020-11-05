package ecare.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class AdDTO implements Serializable {
    private long ad_id;
    private String name;
    private Set<TariffDTO> setOfTariffs;
    private boolean isActive = true;

    public void addTariff(TariffDTO tariffDTO) {
        setOfTariffs.add(tariffDTO);
    }
}
