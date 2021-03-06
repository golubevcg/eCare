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
@EqualsAndHashCode(of = {"contractNumber"})
@Entity
@Table(name="contracts")
public class Contract implements Comparable<Contract>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contract_id;

    @Column(name="contractnumber")
    @NotNull
    @Size(min=10, max=15, message="Phone number can be between 10 to 15 digits.")
    private String contractNumber;

    @Column(name="isblocked")
    private boolean isBlocked;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE,
               fetch = FetchType.EAGER)
    @JoinColumn(name="tarif_id")
    private Tariff tariff;

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name= "contracts_options",
            joinColumns = { @JoinColumn(name= "contract_id") },
            inverseJoinColumns = { @JoinColumn(name="option_id") })
    private Set<Option> setOfOptions = new HashSet<>();

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Contract(String contractNumber, User user) {
        this.contractNumber = contractNumber;
        this.user = user;
    }

    public void addOption(Option option){
        setOfOptions.add(option);
    }

    public void removeOption(Option option){
        setOfOptions.remove(option);
    }

    @Override
    public int compareTo(Contract o) {
        if (this == o) return 0;
        if(this.getContract_id()> o.getContract_id()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contract_id=" + contract_id +
                ", contractNumber='" + contractNumber + '\'' +
                ", isBlocked=" + isBlocked +
                ", user=" + user +
                ", tariff=" + tariff +
                ", isActive=" + isActive +
                ", setOfOptions=" + setOfOptions +
                ", setOfBlockedOptions=" + '}';
    }
}
