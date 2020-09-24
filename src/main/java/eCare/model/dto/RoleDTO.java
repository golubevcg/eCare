package eCare.model.dto;

import eCare.model.enitity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private String rolename;
    private Set<User> user;

    public RoleDTO(String rolename) {
        this.rolename = rolename;
    }
}