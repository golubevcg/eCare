package ecare.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"rolename"})
public class RoleDTO implements Serializable {
    private Long role_id;
    private String rolename;
    private Set<UserDTO> user;

    public void addUser(UserDTO userDTO){ this.user.add(userDTO);}

}