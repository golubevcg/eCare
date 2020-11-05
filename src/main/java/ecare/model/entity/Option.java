package ecare.model.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
@Table(name="options")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long option_id;

    @Column
    @NotNull
    @Size(min=5, message = "Option name cannot be lesser than 5 symbols.")
    private String name;

    @Column
    private Integer price;

    @Column(name="connectioncost")
    private Integer connectionCost;

    @Column(name= "shortdescription")
    private String shortDescription;

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinTable(name= "tariffs_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="tariff_id") })
    private Set<Tariff> tariffsOptions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "contracts_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="contract_id") })
    private Set<Contract> contractsOptions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "incompatible_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="incompatibleoption_id") })
    private Set<Option> incompatibleOptionsSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "obligatory_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="obligatoryoption_id") })
    private Set<Option> obligatoryOptionsSet = new HashSet<>();

    public void addTariff(Tariff tariff){
        tariffsOptions.add(tariff);
    }

    public void addIncompatibleOption(Option option){
        incompatibleOptionsSet.add(option);
    }

    public void addObligatoryOption(Option option){
        obligatoryOptionsSet.add(option);
    }

}
