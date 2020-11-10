//package ecare.services.impl;
//
//import ecare.model.dto.ContractDTO;
//import ecare.model.dto.OptionDTO;
//import ecare.model.dto.TariffDTO;
//import ecare.services.api.ContractService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicBoolean;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class CartServiceImplTest {
//
//    @Mock
//    ContractService contractService;
//
//    @InjectMocks
//    CartServiceImpl cartServiceImpl;
//
//    Set<OptionDTO> optionDTOSetFromCart = new HashSet<>();
//    Set<OptionDTO> optionDTOSetFromDB = new HashSet<>();
//    ContractDTO contractDTOFromCart = new ContractDTO();
//    ContractDTO contractDTOFromDB = new ContractDTO();
//    List<ContractDTO> onlyContractChanges = new ArrayList<>();
//    AtomicBoolean showBlockedOnPage = new AtomicBoolean(false);
//    Map<String, Map<String, String>> mapOfOptionsEnabledDisabled = new HashMap<>();
//
//
//    @Before
//    public void before(){
//        OptionDTO optionDTO = new OptionDTO();
//        optionDTO.setName("name");
//        OptionDTO optionDTO1 = new OptionDTO();
//        optionDTO1.setName("name1");
//        OptionDTO optionDTO2 = new OptionDTO();
//        optionDTO2.setName("name2");
//
//        optionDTOSetFromDB.add(optionDTO);
//        optionDTOSetFromDB.add(optionDTO1);
//
//        optionDTOSetFromCart.add(optionDTO);
//        optionDTOSetFromCart.add(optionDTO2);
//
//        contractDTOFromCart.setContractNumber("0000000000");
//        contractDTOFromDB.setContractNumber("0000000000");
//        contractDTOFromDB.setBlocked(false);
//        contractDTOFromCart.setBlocked(true);
//
//        TariffDTO tariffDTO = new TariffDTO();
//        tariffDTO.setName("testTariff");
//
//        TariffDTO tariffDTO1 = new TariffDTO();
//        tariffDTO1.setName("testTariff1");
//
//        contractDTOFromCart.setTariff(tariffDTO);
//        contractDTOFromDB.setTariff(tariffDTO1);
//
//        when(contractService
//                .getContractDTOByNumberOrNull("0000000000")).thenReturn(contractDTOFromDB);
//
//    }
//
//    @Test
//    public void compareContractInDBWithContractInSessionTest(){
//        cartServiceImpl.compareContractInDBWithContractInSession(contractDTOFromCart, onlyContractChanges,
//                                                                showBlockedOnPage, mapOfOptionsEnabledDisabled);
//        verify(contractService, atLeastOnce()).getContractDTOByNumberOrNull("0000000000");
//        assertFalse(mapOfOptionsEnabledDisabled.isEmpty());
//        assertTrue(showBlockedOnPage.get());
//        assertFalse(onlyContractChanges.isEmpty());
//    }
//
//    @Test
//    public void compareContractInDBWithContractInSessionTariffsEqualsTest(){
//        TariffDTO tariffDTO = new TariffDTO();
//        tariffDTO.setName("testTariff");
//        contractDTOFromCart.setTariff(tariffDTO);
//        contractDTOFromDB.setTariff(tariffDTO);
//
//        cartServiceImpl.compareContractInDBWithContractInSession(contractDTOFromCart, onlyContractChanges,
//                showBlockedOnPage, mapOfOptionsEnabledDisabled);
//        verify(contractService, atLeastOnce()).getContractDTOByNumberOrNull("0000000000");
//        assertFalse(mapOfOptionsEnabledDisabled.isEmpty());
//        assertTrue(showBlockedOnPage.get());
//        assertFalse(onlyContractChanges.isEmpty());
//    }
//
//    @Test
//    public void compareContractInDBWithContractInSessionIsBlockedEqualsTest(){
//        contractDTOFromCart.setBlocked(false);
//        contractDTOFromDB.setBlocked(false);
//
//        cartServiceImpl.compareContractInDBWithContractInSession(contractDTOFromCart, onlyContractChanges,
//                showBlockedOnPage, mapOfOptionsEnabledDisabled);
//        verify(contractService, atLeastOnce()).getContractDTOByNumberOrNull("0000000000");
//        assertFalse(mapOfOptionsEnabledDisabled.isEmpty());
//        assertFalse(showBlockedOnPage.get());
//        assertFalse(onlyContractChanges.isEmpty());
//    }
//
//    @Test
//    public void compareContractInDBWithContractInSessionNotChangedTest(){
//        contractDTOFromCart.setBlocked(false);
//        contractDTOFromDB.setBlocked(false);
//
//        TariffDTO tariffDTO = new TariffDTO();
//        tariffDTO.setName("testTariff");
//        contractDTOFromCart.setTariff(tariffDTO);
//        contractDTOFromDB.setTariff(tariffDTO);
//
//        cartServiceImpl.compareContractInDBWithContractInSession(contractDTOFromCart, onlyContractChanges,
//                showBlockedOnPage, mapOfOptionsEnabledDisabled);
//        verify(contractService, atLeastOnce()).getContractDTOByNumberOrNull("0000000000");
//        assertFalse(mapOfOptionsEnabledDisabled.isEmpty());
//        assertFalse(showBlockedOnPage.get());
//        assertTrue(onlyContractChanges.isEmpty());
//    }
//
//    @Test
//    public void compareContractInDBWithContractInSessionOptionsSameTest(){
//
//        OptionDTO optionDTO = new OptionDTO();
//        optionDTO.setName("name");
//
//        optionDTOSetFromDB.clear();
//        optionDTOSetFromCart.clear();
//        optionDTOSetFromDB.add(optionDTO);
//        optionDTOSetFromCart.add(optionDTO);
//
//        cartServiceImpl.compareContractInDBWithContractInSession(contractDTOFromCart, onlyContractChanges,
//                showBlockedOnPage, mapOfOptionsEnabledDisabled);
//        verify(contractService, atLeastOnce()).getContractDTOByNumberOrNull("0000000000");
//        assertFalse(mapOfOptionsEnabledDisabled.isEmpty());
//    }
//
//}
