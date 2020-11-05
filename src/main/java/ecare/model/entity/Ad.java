package ecare.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
@Table(name="ad")
public class Ad implements Serializable {

    @Id
    private long ad_id;

    @Column(name="isactive")
    private boolean isActive = true;

    @Column
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="ads_tariffs",
            joinColumns = { @JoinColumn(name="ad_id") },
            inverseJoinColumns = { @JoinColumn(name="tariff_id") })
    private Set<Tariff> setOfTariffs = new HashSet<>();
}
