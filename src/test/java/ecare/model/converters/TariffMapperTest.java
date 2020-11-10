package ecare.model.converters;

import ecare.model.dto.TariffDTO;
import ecare.model.entity.Tariff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TariffMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TariffMapper tariffMapper;

    Tariff tariff = new Tariff();
    TariffDTO tariffDTO = new TariffDTO();

    @Test
    public void toDTOTest(){
        tariffMapper.toDTO(tariff);
        verify(modelMapper, atLeastOnce() ).map(tariff, TariffDTO.class);
    }

    @Test
    public void toEntityTest(){
        tariffMapper.toEntity(tariffDTO);
        verify(modelMapper, atLeastOnce() ).map(tariffDTO, Tariff.class);
    }
}

