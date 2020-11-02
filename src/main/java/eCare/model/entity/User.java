package eCare.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message="Login cannot be null!")
    @Size(min = 6, max = 30, message="login must be between 5 and 30 characters")
    private String login;

    @Column
    @NotNull(message="Firstname cannot be null!")
    @Size(min = 2, max = 20, message="firstname must be between 2 and 20 characters")

    private String firstname;

    @Column
    @NotNull(message="Secondname cannot be null!")
    @Size(min = 2, max = 20, message="secondname must be between 2 and 20 characters")
    private String secondname;

    @Column(name="dateofbirth")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message="dateOfBirth cannot be null!")
    private Date dateOfBirth;

    @Column(name="passportinfo")
    @NotNull(message="passportInfo cannot be null!")
    @Size(min = 10, max = 10, message="passportInfo must be 10 characters")
    private String passportInfo;

    @Column
    @NotNull(message="address cannot be null!")
    @Size(min = 5, message="adress cannot be lesser than 5 symbols")
    private String address;

    @Column
    @NotNull(message="email cannot be null!")
    @Email
    private String email;

    @Column
    @NotNull(message="password cannot be null!")
    @Size(min = 6, max = 30, message="password must be between 6 and 30 characters")
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
