package eCare.database.entities;

import eCare.database.services.RoleService;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column
    private String rolename;

    @OneToMany(targetEntity = User.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User> user;

}
