package eCare.database.entities;

import eCare.database.HibernateSessionFactoryUtil;
import eCare.database.services.RoleService;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        this.setRole(role);
    }

    public void removeRole(Role role){
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addContract(Contract contract){
        this.listOfContracts.add(contract);
    }

    public void setRole(Role role){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            Transaction transaction = session.beginTransaction();
            Set<Role> roles = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());

            List<String> roleNames = new ArrayList<String>(session.createQuery("select r.rolename from Role r",
                    String.class).getResultList());

            for (String roleName : roleNames) {
                List<Role> found = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                        .setParameter("roleName", roleName).getResultList();
                if (found.isEmpty()) {
                    Role newRole = new Role();
                    newRole.setRolename(roleName);
                    session.persist(newRole);
                    roles.add(role);
                } else {
                    roles.addAll(found);
                }
            }
            this.setRoles(roles);
            session.persist(this);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

}
