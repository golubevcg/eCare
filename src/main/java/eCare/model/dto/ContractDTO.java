package eCare.model.dto;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"contractNumber"})
public class ContractDTO implements Comparable{

    @Expose
    private Long contract_id;
    @Expose
    private String contractNumber;
    @Expose
    private boolean isBlocked;
    @Expose
    private UserDTO user;
    @Expose
    private TariffDTO tariff;
    @Expose
    private boolean isActive = true;

    private Set<OptionDTO> setOfOptions = new HashSet<>();

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public void addOption(OptionDTO optionDTO){
        setOfOptions.add(optionDTO);
    }

    public void removeOption(OptionDTO optionDTO) {
            setOfOptions.remove(optionDTO);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        ContractDTO that = (ContractDTO) o;
        if(this.getContract_id()>that.getContract_id()){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
                "contract_id=" + contract_id +
                ", contractNumber='" + contractNumber + '\'' +
                ", isBlocked=" + isBlocked +
                ", user=" + user.getLogin() + " " + user.getUser_id() +
                ", isActive=" + isActive +
                '}';
    }

}
