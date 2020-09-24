package eCare.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String confirmPassword;
    private boolean isActive = true;
    private Set<RoleDTO> roles = new HashSet<>();
    private List<ContractDTO> listOfContracts = new ArrayList<>();

    public void addContractDTO(ContractDTO contractDTO){
        listOfContracts.add(contractDTO);
    }

}
