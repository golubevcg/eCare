package eCare.database.entities;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private int price;

    @Column(name="shortdiscription")
    private String shortDiscription;

    @OneToMany(targetEntity = Contract.class,
            mappedBy = "tariff",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Contract> listOfContracts  = new ArrayList<>();

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name= "tariffs_options",
            joinColumns = { @JoinColumn(name="tariff_id") },
            inverseJoinColumns = { @JoinColumn(name= "option_id") })
    private List<Option> listOfOptions = new ArrayList<>();

    public Tariff(String name, int price, String discription) {
        this.name = name;
        this.price = price;
        this.shortDiscription = discription;
    }

    public void addContract(Contract contract){
        listOfContracts.add(contract);
    }

    public void addOption(Option option){
        listOfOptions.add(option);
    }

}
