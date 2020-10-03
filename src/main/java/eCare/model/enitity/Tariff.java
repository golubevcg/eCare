package eCare.model.enitity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
@Table(name= "tariffs")
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tariff_id;

    @Column
    private String name;

    @Column
    private Integer price;

    @Column(name= "shortdescription")
    private String shortDiscription;

    @OneToMany(targetEntity = Contract.class,
            mappedBy = "tariff",
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Contract> setOfContracts = new HashSet<>();

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "tariffs_options",
            joinColumns = { @JoinColumn(name="tariff_id") },
            inverseJoinColumns = { @JoinColumn(name= "option_id") })
    private Set<Option> setOfOptions = new HashSet<>();

    public Tariff(String name, int price, String discription) {
        this.name = name;
        this.price = price;
        this.shortDiscription = discription;
    }

    public void addContract(Contract contract){
        setOfContracts.add(contract);
    }

    public void addOption(Option option){
        setOfOptions.add(option);
    }

}
