package eCare.model.dto;

import eCare.model.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long role_id;
    private String rolename;
    private Set<UserDTO> user;

    public void addUser(UserDTO userDTO){ this.user.add(userDTO);}

}