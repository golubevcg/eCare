package ecare.model.converters;

import ecare.model.dto.OptionDTO;
import ecare.model.entity.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OptionMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OptionMapper optionMapper;

    Option option = new Option();
    OptionDTO optionDTO = new OptionDTO();

    @Test
    public void toDTOTest(){
        optionMapper.toDTO(option);
        verify(modelMapper, atLeastOnce() ).map(option, OptionDTO.class);
    }

    @Test
    public void toEntityTest(){
        optionMapper.toEntity(optionDTO);
        verify(modelMapper, atLeastOnce() ).map(optionDTO, Option.class);
    }
}

