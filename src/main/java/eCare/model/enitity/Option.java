package eCare.model.enitity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    private Integer price;

    @Column(name="connectioncost")
    private Integer connectionCost;

    @Column(name= "shortdiscription")
    private String shortDiscription;

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinTable(name= "tariffs_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="tariff_id") })
    private List<Tariff> tariffsOptions = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "incompatible_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="incompatibleoption_id") })
    private List<Option> incompatibleOptionsList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "obligatory_options",
            joinColumns = { @JoinColumn(name= "option_id") },
            inverseJoinColumns = { @JoinColumn(name="obligatoryoption_id") })
    private List<Option> obligatoryOptionsList = new ArrayList<>();

    public void addTariff(Tariff tariff){
        tariffsOptions.add(tariff);
    }

    public void addIncompatibleOption(Option option){

        for (int i = 0; i < obligatoryOptionsList.size(); i++) {
            if(( obligatoryOptionsList.get(i).getName() ).equals(option.getName())){
                obligatoryOptionsList.remove(option);
            }
        }

        incompatibleOptionsList.add(option);

    }

    public void addObligatoryOption(Option option){


        for (int i = 0; i < incompatibleOptionsList.size(); i++) {
            if(( incompatibleOptionsList.get(i).getName() ).equals(option.getName())){
                incompatibleOptionsList.remove(option);
            }
        }

        obligatoryOptionsList.add(option);
    }

}
