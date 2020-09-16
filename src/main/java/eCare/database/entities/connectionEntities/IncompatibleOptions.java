package eCare.database.entities.connectionEntities;

import eCare.database.entities.Option;

import javax.persistence.*;

@Entity
@Table(name="incompatibleoptions")
public class IncompatibleOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="option_id")
    private Option option;


    @ManyToOne
    @JoinColumn(name="incompatibleOption_id")
    private Option incompatibleOption;
}
