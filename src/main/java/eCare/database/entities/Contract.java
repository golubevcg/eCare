package eCare.database.entities;

import javax.persistence.*;

@Entity
@Table(name="contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="contractnumber")
    private String contractNumber;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "tarif_id")
    private Tarif tarif;

    @Column(name="isblocked")
    private boolean isBlocked;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
