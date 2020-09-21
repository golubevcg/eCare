package eCare.model;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
            fetch = FetchType.LAZY)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User> user;

    public Role(String rolename) {
        this.rolename = rolename;
    }


}
