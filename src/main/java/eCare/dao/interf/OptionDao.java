package eCare.dao.interf;

import eCare.model.Option;

import java.util.List;

public interface OptionDao {
    void save(Option option);
    void update(Option option);
    void delete(Option option);
    List<Option> getOptionByName(String name);
}
