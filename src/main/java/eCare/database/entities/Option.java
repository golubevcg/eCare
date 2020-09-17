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
@Table(name="options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long option_id;

    @Column
    private String name;

    @Column
    private int price;

    @Column(name="connectioncost")
    private int connectionCost;

    @Column(name= "shortdiscription")
    private String shortDiscription;

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(name= "tariffs_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="tariff_id") })
    private List<Tariff> tariffsOptions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name= "incompatible_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="incompatibleoption_id") })
    private List<Option> incompatibleOptionsList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name= "obligatory_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="obligatoryoption_id") })
    private List<Option> obligatoryOptionsList = new ArrayList<>();

    public void addTariff(Tariff tariff){
        tariffsOptions.add(tariff);
    }

    public void addIncompatibleOption(Option option){
        incompatibleOptionsList.add(option);
    }

    public void addObligatoryOption(Option option){
        obligatoryOptionsList.add(option);
    }

}
