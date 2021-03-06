package ecare.model.dto;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"login"})
public class UserDTO implements Serializable {
    @Expose
    private Long user_id;
    @Expose
    private String login;
    @Expose
    private String firstname;
    @Expose
    private String secondname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Expose
    private Date dateOfBirth;

    @Expose
    private String passportInfo;

    @Expose
    private String address;

    @Expose
    private String email;

    private String password;
    private String confirmPassword;

    @Expose
    private boolean isActive = true;
    private Set<RoleDTO> roles = new HashSet<>();
    private Set<ContractDTO> listOfContracts = new HashSet<>();

    public void addContractDTO(ContractDTO contractDTO){
        listOfContracts.add(contractDTO);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", roles=" + roles +
                '}';
    }

    public Set<ContractDTO> getListOfActiveContracts() {
        Set<ContractDTO> setOfActiveContracts = new HashSet<>();

        for (ContractDTO contractDTO: listOfContracts) {
            if(contractDTO.isActive()){
                setOfActiveContracts.add(contractDTO);
            }
        }
        return setOfActiveContracts;
    }
}
