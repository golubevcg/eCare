package eCare.database.entities.connectionEntities;

import eCare.database.entities.Option;

import javax.persistence.*;

@Entity
@Table(name="obligatoryoptions")
public class ObligatoryOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="option_id")
    private Option option;


    @ManyToOne
    @JoinColumn(name="obligatoryOption_id")
    private Option obligatoryOption;
}
