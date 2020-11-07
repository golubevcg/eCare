package ecare.validator;

import ecare.model.dto.TariffDTO;
import ecare.services.api.TariffService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TariffDTOValidatorTest {

    @Mock
    TariffService tariffService;

    @Mock
    Errors errors;

    @InjectMocks
    TariffDTOValidator tariffDTOValidator;

    @Mock
    TariffDTO tariffDTO;

    @Before
    public void before(){
        when(tariffDTO.getName()).thenReturn("name");
//        when(tariffDTO.getPrice()).thenReturn(11);
        when(tariffDTO.getShortDiscription()).thenReturn("shd");
        when(tariffService.getTariffDTOByTariffNameOrNull(any())).thenReturn(new TariffDTO());
    }


    @Test
    public void testEmptyFields(){
        tariffDTOValidator.validate(tariffDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("name", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("price", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("shortDiscription", "Required", null, null);
    }

    @Test
    public void duplicatedTariff(){
        tariffDTOValidator.validate(tariffDTO, errors);
        verify(errors,atLeastOnce()).rejectValue("name", "Duplicate.tariffDTO.name");
    }

    @Test
    public void shortDescriptionLength(){
        tariffDTOValidator.validate(tariffDTO, errors);
        verify(errors,atLeastOnce()).rejectValue("shortDiscription", "Duplicate.tariffDTO.shortDescriptionLength");
    }
}
