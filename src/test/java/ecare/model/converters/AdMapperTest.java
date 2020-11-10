package ecare.model.converters;

import ecare.model.dto.AdDTO;
import ecare.model.entity.Ad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AdMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AdMapper adMapper;

    Ad ad = new Ad();
    AdDTO adDTO = new AdDTO();

    @Test
    public void toDTOTest(){
        adMapper.toDTO(ad);
        verify(modelMapper, atLeastOnce() ).map(ad, AdDTO.class);
    }

    @Test
    public void toEntityTest(){
        adMapper.toEntity(adDTO);
        verify(modelMapper, atLeastOnce() ).map(adDTO, Ad.class);
    }

}
