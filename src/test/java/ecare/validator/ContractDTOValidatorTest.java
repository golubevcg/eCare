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
    private ContractDTO contractDTO = mock(ContractDTO.class);

    @Mock
    private Contract contract = mock(Contract.class);

    @Mock
    private ContractService contractService = mock(ContractService.class);

    @Mock
    private UserService userService = mock(UserService.class);

    @Mock
    private Errors errors = mock(Errors.class);

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
