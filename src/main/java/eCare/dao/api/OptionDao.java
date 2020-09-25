package eCare.dao.api;

import eCare.model.enitity.Option;

import java.util.List;

public interface OptionDao {
    void save(Option option);
    void update(Option option);
    void delete(Option option);
    List<Option> getOptionByName(String name);
}