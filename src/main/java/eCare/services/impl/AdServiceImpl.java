package eCare.services.impl;

import eCare.dao.api.AdDao;
import eCare.model.converters.AdMapper;
import eCare.model.dto.AdDTO;
import eCare.model.entity.Ad;
import eCare.services.api.AdService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AdServiceImpl implements AdService {

    private final AdDao adDaoImpl;

    private final AdMapper adMapper;

    public AdServiceImpl(AdDao adDaoImpl, AdMapper adMapper) {
        this.adDaoImpl = adDaoImpl;
        this.adMapper = adMapper;
    }

    @Override
    public void save(Ad ad) {
        adDaoImpl.save(ad);
    }

    @Override
    public void update(Ad ad) {
        adDaoImpl.update(ad);
    }

    @Override
    public void delete(Ad ad) {
        adDaoImpl.delete(ad);
    }

    @Override
    public Ad getAdByNameOrNull(String name) {
        return adDaoImpl.getAdByNameOrNull(name);
    }

    @Override
    public void convertToEntityAndSave(AdDTO adDto) {
        adDaoImpl.save( adMapper.toEntity(adDto) );
    }

    @Override
    public void convertToEntityAndUpdate(AdDTO adDto) {
        adDaoImpl.update( adMapper.toEntity(adDto) );
    }

    @Override
    public void convertToEntityAndDelete(AdDTO adDto) {
        adDaoImpl.delete( adMapper.toEntity(adDto) );
    }

    @Override
    public AdDTO getAdDTOByNameOrNull(String name) {
        return adMapper.toDTO( adDaoImpl.getAdByNameOrNull(name) );
    }
}
