package eCare.database.entities.connectionEntities;

import eCare.database.entities.Option;
import eCare.database.entities.Tarif;

import javax.persistence.*;

@Entity
@Table(name="tarifsoptions")
public class TarifsOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="tarif_id")
    private Tarif tarif;

    @ManyToOne
    @JoinColumn(name="option_id")
    private Option option;
}
