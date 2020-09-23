package eCare.model.dto;

import eCare.model.enitity.Contract;
import eCare.model.enitity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String login;
    private String firstname;
    private String secondname;
    private LocalDate dateOfBirth;
    private Long passportInfo;
    private String address;
    private String email;
    private String password;
    private boolean isActive = true;
    private Set<Role> roles = new HashSet<>();
    private List<Contract> listOfContracts = new ArrayList<>();

}
