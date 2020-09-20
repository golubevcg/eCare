package eCare.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login"})
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column
    private String login;

    @Column
    private String firstname;

    @Column
    private String secondname;

    @Column(name="dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name="passportinfo")
    private int passportInfo;

    @Column
    private String address;

    @Column
    private String email;

    @Column
    private String password;

    @Transient
    private String confirmPassword;

    @Column(name="isactive")
    private boolean isActive = true;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(targetEntity = Contract.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Contract> listOfContracts = new ArrayList<>();

     public User(String login, String password, Role role){
        this.login = login;
        this.password = password;
        this.addRole(role);
    }

    public void addContract(Contract contract){
        this.listOfContracts.add(contract);
    }

    public void addRole(Role role){
        roles.add(role);
    }

}
