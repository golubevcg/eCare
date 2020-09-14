package eCare.database.entities;

import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstname;

    @Column
    private String secondname;

    @Column
    private Date dateOfBirth;

    @Column
    private int passportInfo;

    @Column
    private String adress;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private int role_id;

}
