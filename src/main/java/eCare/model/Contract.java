package eCare.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"contractNumber"})
@Entity
@Table(name="contracts")
public class Contract {

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

    @ManyToOne(cascade = CascadeType.ALL,
               fetch = FetchType.EAGER)
    @JoinColumn(name="tarif_id")
    private Tariff tariff;

    @Column(name="isactive")
    private boolean isActive = true;

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Contract(User user, String contractNumber) {
        this.contractNumber = contractNumber;
        this.user = user;
    }
}
