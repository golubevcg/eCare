package eCare.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContractDTO {
    private Long user_id;
    private String login;
    private String firstname;
    private String secondname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateOfBirth;
    private Long passportInfo;
    private String address;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean isActive = true;
    private Set<RoleDTO> roles = new HashSet<>();
    private Set<ContractDTO> listOfContracts = new HashSet<>();

    public void addContractDTO(ContractDTO contractDTO){
        listOfContracts.add(contractDTO);
    }

    public UserDTO getUserDTO(){
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);
        userDTO.setFirstname(firstname);
        userDTO.setSecondname(secondname);
        userDTO.setDateOfBirth(dateOfBirth);
        userDTO.setPassportInfo(passportInfo);
        userDTO.setAddress(address);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setConfirmPassword(confirmPassword);

        userDTO.setActive(isActive);
        userDTO.setRoles(roles);
        userDTO.setListOfContracts(listOfContracts);

        return userDTO;
    }

    private String contractNumber;
    private boolean isBlocked;
    private UserDTO user;
    private TariffDTO tariff;

    public ContractDTO getContractDTO(){
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(contractNumber);
        contractDTO.setBlocked(isBlocked);
        contractDTO.setUser(user);
        contractDTO.setTariff(tariff);

        return contractDTO;
    }



}
