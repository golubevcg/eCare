package eCare.dao.api;

import eCare.model.entity.Ad;
import eCare.model.entity.User;
import org.hibernate.Session;

import java.util.List;

public interface AdDao {
    void save(Ad ad);
    void update(Ad ad);
    void delete(Ad ad);
    Ad getAdByNameOrNull(String name);
}