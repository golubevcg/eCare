package ecare.services.api;

import ecare.model.dto.AdDTO;
import ecare.model.entity.Ad;

public interface AdService {
    void save(Ad ad);
    void update(Ad ad);
    void delete(Ad ad);
    Ad getAdByNameOrNull(String name);

    void convertToEntityAndSave(AdDTO adDto);
    void convertToEntityAndUpdate(AdDTO adDto);
    void convertToEntityAndDelete(AdDTO adDto);
    AdDTO getAdDTOByNameOrNull(String name);
}