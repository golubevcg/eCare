package eCare.database.entities;

import eCare.database.entities.connectionEntities.IncompatibleOptions;
import eCare.database.entities.connectionEntities.ObligatoryOptions;
import eCare.database.entities.connectionEntities.TarifsOptions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int price;

    @Column(name="connectioncost")
    private int connectionCost;

    @Column
    private String discription;

    @OneToMany(mappedBy="options")
    private List<TarifsOptions> tarifsOptionsList = new ArrayList<>();

    @OneToMany(mappedBy="obligatoryoptions")
    private List<ObligatoryOptions> obligatoryOptionsList = new ArrayList<>();

    @OneToMany(mappedBy="incompatibleoptions")
    private List<IncompatibleOptions> incompatibleOptionsList = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getConnectionCost() {
        return connectionCost;
    }

    public void setConnectionCost(int connectionCost) {
        this.connectionCost = connectionCost;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
