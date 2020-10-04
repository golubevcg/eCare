package eCare;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.dao.impl.ContractDaoImpl;
import eCare.dao.impl.OptionDaoImpl;
import eCare.dao.impl.TariffDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.converters.ContractMapper;
import eCare.model.converters.OptionMapper;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Contract;
import eCare.model.enitity.Option;
import eCare.model.enitity.Tariff;
import eCare.services.impl.ContractServiceImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        OptionDaoImpl optionDao = new OptionDaoImpl();
//
//        Option option = new Option();
//        option.setName("TestOption1");
//        option.setPrice(100);
//        option.setShortDiscription("Incompatible with UnlimitedCalls");
//
//        Option option1 = new Option();
//        option1.setName("TestOption2");
//        option1.setPrice(100);
//        option1.setShortDiscription("Obligatory with 300min");
//
//        Option unlimCallsOption = optionDao.getOptionByName("Unlimited calls").get(0);
//        Option ThreeHundreedMinOption = optionDao.getOptionByName("300min").get(0);
//
//        HashSet<Option> optionHashSet = new HashSet<Option>();
//        optionHashSet.add(unlimCallsOption);
//        option.setIncompatibleOptionsSet(optionHashSet);
//
//        HashSet<Option> optionHashSet1 = new HashSet<Option>();
//        optionHashSet1.add(ThreeHundreedMinOption);
//        option1.setObligatoryOptionsSet(optionHashSet1);
//
//        TariffDaoImpl tariffDao = new TariffDaoImpl();
//        Tariff tariffBussiness = tariffDao.getTariffByTariffName("Business").get(0);
//        tariffBussiness.addOption(option);
//        tariffBussiness.addOption(option1);
//
//        optionDao.save(option);
//        optionDao.save(option1);
//        tariffDao.update(tariffBussiness);
    }
}
