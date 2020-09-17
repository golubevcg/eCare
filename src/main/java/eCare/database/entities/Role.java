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

    @ManyToMany(mappedBy = "roles",
                cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

}
