package eCare.model.dto;

import eCare.model.entity.Tariff;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class AdDTO {
    private long ad_id;
    private String name;
    private Set<TariffDTO> setOfTariffs;
    private boolean isActive = true;

    public void addTariff(TariffDTO tariffDTO) {
        setOfTariffs.add(tariffDTO);
    }
}
