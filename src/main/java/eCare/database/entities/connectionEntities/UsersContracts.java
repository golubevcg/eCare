package eCare.database.entities.connectionEntities;

import eCare.database.entities.Contract;
import eCare.database.entities.User;

import javax.persistence.*;

@Entity
@Table(name= "userscontracts")
public class UsersContracts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="contract_id")
    private Contract contract;



}
