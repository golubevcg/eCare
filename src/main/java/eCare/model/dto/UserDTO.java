package eCare.model.dto;

import eCare.model.enitity.Contract;
import eCare.model.enitity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String login;
    private String firstname;
    private String secondname;
    private Date dateOfBirth;
    private Long passportInfo;
    private String address;
    private String email;
    private String password;
    private boolean isActive = true;
    private Set<Role> roles = new HashSet<>();
    private List<Contract> listOfContracts = new ArrayList<>();

}
