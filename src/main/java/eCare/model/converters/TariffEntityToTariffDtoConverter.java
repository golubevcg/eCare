package eCare.model.converters;

import eCare.model.dto.TariffDTO;
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
        tariffDTO.setActive(tariff.isActive());
        tariffDTO.setListOfOptions(tariff.getListOfOptions());
        return tariffDTO;
    }

    public Tariff convertDTOtoEntity(TariffDTO tariffDTO){
        Tariff tariff = tariffServiceImpl.getTarifByTarifName(tariffDTO.getName()).get(0);
        tariff.setName(tariffDTO.getName());
        tariff.setPrice(tariffDTO.getPrice());
        tariff.setShortDiscription(tariffDTO.getShortDiscription());
        tariff.setListOfContracts(tariffDTO.getListOfContracts());
        tariff.setActive(tariffDTO.isActive());
        tariff.setListOfOptions(tariffDTO.getListOfOptions());
        return tariff;
    }
}
