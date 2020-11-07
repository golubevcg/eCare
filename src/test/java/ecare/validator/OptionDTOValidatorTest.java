package ecare.validator;

import ecare.model.dto.OptionDTO;
import ecare.model.entity.Option;
import ecare.services.api.OptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OptionDTOValidatorTest {

    @Mock
    Errors errors;

    @Mock
    OptionService optionService;

    @InjectMocks
    OptionDTOValidator optionDTOValidator;

    @Mock
    OptionDTO optionDTO;

    @Before
    public void before(){
        when(optionDTO.getName()).thenReturn("shrd");
        when(optionDTO.getShortDescription()).thenReturn("shrd");
    }

    @Test
    public void validateEmptyFields(){
        optionDTOValidator.validate(optionDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("name", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("price", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("connectionCost", "Required", null, null);
        verify(errors, atLeastOnce()).rejectValue("shortDescription", "Required", null, null);
    }

    @Test
    public void duplicatedOptionName(){
        ArrayList<Option> arrList = new ArrayList();
        arrList.add(new Option());
        when(optionService.getOptionByName(any())).thenReturn(arrList);
        optionDTOValidator.validate(optionDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("name", "Duplicate.optionDTO.name");
    }

    @Test
    public void shortDescriptionLength(){
        optionDTOValidator.validate(optionDTO, errors);
        verify(errors, atLeastOnce()).rejectValue("shortDescription", "Duplicate.optionDTO.shortDescriptionLength");
    }

}
