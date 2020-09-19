package eCare.database.entities;

import eCare.database.HibernateSessionFactoryUtil;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Role role;

    @OneToMany(targetEntity = Contract.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Contract> listOfContracts = new ArrayList<>();

     public User(String login, String password, Role role){
        this.login = login;
        this.password = password;
        this.checkRoleInDBAndReturnIfFounded(role);
    }

    public void addContract(Contract contract){
        this.listOfContracts.add(contract);
    }

    public void checkRoleInDBAndReturnIfFounded(Role role){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Set<Role> allRolesSet = new HashSet<>(session.createQuery("select r from Role r", Role.class)
                    .getResultList());

            if (allRolesSet.isEmpty()) {
                this.role = role;
                session.save(role);
            }else {

                List<Role> found = session.createQuery("select r from Role r where r.rolename = :roleName", Role.class)
                        .setParameter("roleName", role.getRolename()).getResultList();
                if (found.isEmpty()) {
                    this.role = role;
                    session.save(role);
                }else{
                    this.role = found.get(0);
                }

            }
            transaction.commit();
        } finally {
            session.close();
        }
    }

}
