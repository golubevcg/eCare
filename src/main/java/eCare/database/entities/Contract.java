package eCare.database.entities;

import javax.persistence.*;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int contractNumber;

    @Column
//    @OneToOne
    private int tarif_id;

    @Column
    private boolean isBlocked;
}
