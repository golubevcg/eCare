package ecare.services.impl;

import ecare.dao.api.AdDao;
import ecare.model.converters.AdMapper;
import ecare.model.dto.AdDTO;
import ecare.model.entity.Ad;
import ecare.services.api.AdService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AdServiceImpl implements AdService {

    static final Logger log = Logger.getLogger(AdServiceImpl.class);

    private final AdDao adDaoImpl;

    private final AdMapper adMapper;

    private String adNameEquals = "Ad(name=";

    public AdServiceImpl(AdDao adDaoImpl, AdMapper adMapper) {
        this.adDaoImpl = adDaoImpl;
        this.adMapper = adMapper;
    }

    @Override
    public void save(Ad ad) {
        adDaoImpl.save(ad);
        log.info(adNameEquals + ad.getName() + ") was successfully saved!");
    }

    @Override
    public void update(Ad ad) {
        adDaoImpl.update(ad);
        log.info(adNameEquals + ad.getName() + ") was successfully updated!");
    }

    @Override
    public void delete(Ad ad) {
        adDaoImpl.delete(ad);
        log.info(adNameEquals + ad.getName() + ") was successfully deleted!");
    }

    @Override
    public Ad getAdByNameOrNull(String name) {
        return adDaoImpl.getAdByNameOrNull(name);
    }

    @Override
    public void convertToEntityAndSave(AdDTO adDto) {
        adDaoImpl.save( adMapper.toEntity(adDto) );
        log.info(adNameEquals + adDto.getName() + ") was successfully converted and saved!");
    }

    @Override
    public void convertToEntityAndUpdate(AdDTO adDto) {
        adDaoImpl.update( adMapper.toEntity(adDto) );
        log.info(adNameEquals + adDto.getName() + ") was successfully converted and updated!");
    }

    @Override
    public void convertToEntityAndDelete(AdDTO adDto) {
        adDaoImpl.delete( adMapper.toEntity(adDto) );
        log.info(adNameEquals + adDto.getName() + ") was successfully converted and deleted!");
    }

    @Override
    public AdDTO getAdDTOByNameOrNull(String name) {
        return adMapper.toDTO( adDaoImpl.getAdByNameOrNull(name) );
    }

}
