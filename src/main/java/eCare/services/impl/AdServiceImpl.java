package eCare.services.impl;

import eCare.controllers.EntrancePageController;
import eCare.dao.api.AdDao;
import eCare.model.converters.AdMapper;
import eCare.model.dto.AdDTO;
import eCare.model.entity.Ad;
import eCare.services.api.AdService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AdServiceImpl implements AdService {

    static final Logger log = Logger.getLogger(EntrancePageController.class);

    private final AdDao adDaoImpl;

    private final AdMapper adMapper;

    public AdServiceImpl(AdDao adDaoImpl, AdMapper adMapper) {
        this.adDaoImpl = adDaoImpl;
        this.adMapper = adMapper;
    }

    @Override
    public void save(Ad ad) {
        adDaoImpl.save(ad);
        log.info("Ad(name=" + ad.getName() + ") was successfully saved!");
    }

    @Override
    public void update(Ad ad) {
        adDaoImpl.update(ad);
        log.info("Ad(name=" + ad.getName() + ") was successfully updated!");
    }

    @Override
    public void delete(Ad ad) {
        adDaoImpl.delete(ad);
        log.info("Ad(name=" + ad.getName() + ") was successfully deleted!");
    }

    @Override
    public Ad getAdByNameOrNull(String name) {
        return adDaoImpl.getAdByNameOrNull(name);
    }

    @Override
    public void convertToEntityAndSave(AdDTO adDto) {
        adDaoImpl.save( adMapper.toEntity(adDto) );
        log.info("Ad(name=" + adDto.getName() + ") was successfully converted and saved!");
    }

    @Override
    public void convertToEntityAndUpdate(AdDTO adDto) {
        adDaoImpl.update( adMapper.toEntity(adDto) );
        log.info("Ad(name=" + adDto.getName() + ") was successfully converted and updated!");
    }

    @Override
    public void convertToEntityAndDelete(AdDTO adDto) {
        adDaoImpl.delete( adMapper.toEntity(adDto) );
        log.info("Ad(name=" + adDto.getName() + ") was successfully converted and deleted!");
    }

    @Override
    public AdDTO getAdDTOByNameOrNull(String name) {
        return adMapper.toDTO( adDaoImpl.getAdByNameOrNull(name) );
    }



}
