package eCare.database.entities;

import javax.persistence.*;

@Entity
public class Tarif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private String discription;

}
