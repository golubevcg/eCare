package eCare.model.entity;

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
@EqualsAndHashCode(of = {"contractNumber"})
@Entity
@Table(name="contracts")
public class Contract implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contract_id;

    @Column(name="contractnumber")
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
    public int compareTo(Object o) {
        if (this == o) return 0;
        Contract that = (Contract) o;
        if(this.getContract_id()>that.getContract_id()){
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
