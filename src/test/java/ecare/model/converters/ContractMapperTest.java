package ecare.model.converters;

import ecare.model.dto.ContractDTO;
import ecare.model.entity.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContractMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractMapper contractMapper;

    Contract contract = new Contract();
    ContractDTO contractDTO = new ContractDTO();

    @Test
    public void toDTOTest(){
        contractMapper.toDTO(contract);
        verify(modelMapper, atLeastOnce() ).map(contract, ContractDTO.class);
    }

    @Test
    public void toEntityTest(){
        contractMapper.toEntity(contractDTO);
        verify(modelMapper, atLeastOnce() ).map(contractDTO, Contract.class);
    }
}

