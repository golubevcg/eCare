package eCare.database.entities;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private int connectionCost;

    @Column
    private String discription;
}
