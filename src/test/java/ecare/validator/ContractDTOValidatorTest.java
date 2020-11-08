package ecare.validator;

import ecare.model.dto.ContractDTO;
import ecare.model.entity.Contract;
import ecare.services.api.ContractService;
import ecare.services.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContractDTOValidatorTest {

    @Mock
    private ContractDTO contractDTO;

    @Mock
    private Contract contract;

    @Mock
    private ContractService contractService;

    @Mock
    private UserService userService;

    @Mock
    private Errors errors;

    @InjectMocks
    ContractDTOValidator contractDTOValidator;


    @Test
    public void validateEmptyNumberTest(){
        contractDTOValidator.validate(contractDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("contractNumber","Required",null,null);
    }

    @Test
    public void validateNumberTest(){
        ArrayList<Contract> alist = new ArrayList<>();
        alist.add(contract);
        when(contractService.getContractByNumber(any())).thenReturn(alist);

        contractDTOValidator.validate(contractDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("contractNumber","Duplicate.contractDTO.contractNumber");
    }

}
