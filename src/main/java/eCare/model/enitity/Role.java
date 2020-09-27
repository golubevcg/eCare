package eCare.model.enitity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"rolename"})
@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column
    private String rolename;

    @ManyToMany(targetEntity = User.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User> user;

    public Role(String rolename) {
        this.rolename = rolename;
    }


}
