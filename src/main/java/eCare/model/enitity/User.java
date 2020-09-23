package eCare.model.enitity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Column(name="passportinfo")
    private Long passportInfo;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toSet());
    }

}
