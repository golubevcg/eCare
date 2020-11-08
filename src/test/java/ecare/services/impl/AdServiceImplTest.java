package ecare.services.impl;

import ecare.dao.api.AdDao;
import ecare.model.converters.AdMapper;
import ecare.model.dto.AdDTO;
import ecare.model.entity.Ad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdServiceImplTest {

    @Mock
    private AdDao adDao = mock(AdDao.class);

    @Mock
    private AdMapper adMapper = mock(AdMapper.class);

    @InjectMocks
    private AdServiceImpl adServiceImpl;

    @Mock
    private Ad ad = mock(Ad.class);

    @Test
    public void saveTest(){
        adServiceImpl.save(ad);
        verify(adDao).save(ad);
    }

    @Test
    public void updateTest(){
        adServiceImpl.update(ad);
        verify(adDao).update(ad);
    }

    @Test
    public void deleteTest(){
        adServiceImpl.delete(ad);
        verify(adDao).delete(ad);
    }

    @Test
    public void getAdByNameOrNull() {
        adServiceImpl.getAdByNameOrNull("name");
        verify(adDao).getAdByNameOrNull("name");
    }

    @Test
    public void convertToEntityAndSave() {
        AdDTO adDto = new AdDTO();
        adServiceImpl.convertToEntityAndSave(adDto);
        verify(adDao).save(any());
        verify(adMapper).toEntity(adDto);
    }

    @Test
    public void convertToEntityAndUpdate() {
        AdDTO adDto = new AdDTO();
        adServiceImpl.convertToEntityAndUpdate(adDto);
        verify(adDao).update(any());
        verify(adMapper).toEntity(adDto);
    }

    @Test
    public void convertToEntityAndDelete() {
        AdDTO adDto = new AdDTO();
        adServiceImpl.convertToEntityAndDelete(adDto);
        verify(adDao).delete(any());
        verify(adMapper).toEntity(adDto);
    }

    @Test
    public void getAdDTOByNameOrNull() {
        when(adDao.getAdByNameOrNull("name")).thenReturn(new Ad());
        adServiceImpl.getAdDTOByNameOrNull("name");
        verify(adDao).getAdByNameOrNull(any());
        verify(adMapper).toDTO(any());
    }


}
