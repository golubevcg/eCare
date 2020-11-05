package ecare.dao.api;

import ecare.model.entity.Ad;

public interface AdDao {
    void save(Ad ad);
    void update(Ad ad);
    void delete(Ad ad);
    Ad getAdByNameOrNull(String name);
}