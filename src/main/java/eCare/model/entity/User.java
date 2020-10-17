package eCare.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;

    @Column(name="passportinfo")
    private String passportInfo;

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

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(targetEntity = Contract.class,
            mappedBy = "user",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER)
    private Set<Contract> listOfContracts = new HashSet<>();

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

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }
}
