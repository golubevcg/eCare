package eCare.model.converters;

import eCare.model.dto.ContractDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.enitity.Tariff;
import eCare.services.impl.TariffServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class TariffEntityToTariffDtoConverter {

    @Autowired
    private TariffServiceImpl tariffServiceImpl;

    public TariffDTO convertToDto(Tariff tariff){
        TariffDTO tariffDTO = new TariffDTO();
        tariffDTO.setName(tariff.getName());
        tariffDTO.setPrice(tariff.getPrice());
        tariffDTO.setShortDiscription(tariff.getShortDiscription());

        tariffDTO.setListOfContracts(tariff.getListOfContracts());

        tariff.getListOfContracts()
                .stream()
                .map(contract -> new ContractDTO(contract.getContractNumber(),
                        contract.isBlocked(),

                        contract.getUser(),

                        contract.getTariff(),

                        contract.isActive()))


//        private String contractNumber;
//        private boolean isBlocked;
//        private UserDTO user;
//        private Tariff tariff;
//        private boolean isActive = true;

        tariffDTO.setActive(tariff.isActive());

        tariffDTO.setListOfOptions(tariff.getListOfOptions());

        return tariffDTO;
    }

    public Tariff convertDTOtoEntity(TariffDTO tariffDTO){
        Tariff tariff = tariffServiceImpl.getTariffByTariffName(tariffDTO.getName()).get(0);
        tariff.setName(tariffDTO.getName());
        tariff.setPrice(tariffDTO.getPrice());
        tariff.setShortDiscription(tariffDTO.getShortDiscription());
        tariff.setListOfContracts(tariffDTO.getListOfContracts());
        tariff.setActive(tariffDTO.isActive());
        tariff.setListOfOptions(tariffDTO.getListOfOptions());
        return tariff;
    }
}
