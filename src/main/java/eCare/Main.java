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
//        option.setName("100min");
//        option.setPrice(5);
//        option.setShortDiscription("100 min to all phone calls");
//
//        Option option9 = optionDao.getOptionByName("200min").get(0);
//
//        Option option2 = new Option();
//        option2.setName("300min");
//        option2.setPrice(15);
//        option2.setShortDiscription("300 min to all phone calls");
//
//        optionDao.save(option);
//        optionDao.save(option2);
//
//        Option option1 = new Option();
//        option1.setName("Unlimited calls");
//        option1.setPrice(200);
//        option1.setShortDiscription("Unlimited calls inside your country");
//
//        Set<Option> incompatibleToOption1Set = new HashSet<>();
//        incompatibleToOption1Set.add(option2);
//        incompatibleToOption1Set.add(option);
//        incompatibleToOption1Set.add(option9);
//        option1.setIncompatibleOptionsList(incompatibleToOption1Set);
//
//        optionDao.save(option1);
//
//        Option option3 = new Option();
//        option3.setName("2Gb");
//        option3.setPrice(2);
//        option3.setShortDiscription("2Gb internet pack");
//        optionDao.save(option3);
//
//        Option option8 = optionDao.getOptionByName("5Gb").get(0);
//
//        Option option4 = new Option();
//        option4.setName("Unlimited Internet");
//        option4.setPrice(150);
//        option4.setShortDiscription("Unlimited traffic to all Network");
//
//        Set<Option> incompatibleToOption4Set = new HashSet<>();
//        incompatibleToOption4Set.add(option8);
//        incompatibleToOption4Set.add(option3);
//        option4.setIncompatibleOptionsList(incompatibleToOption4Set);
//        optionDao.save(option4);
//
//        Option option5 = new Option();
//        option5.setName("100 Messages");
//        option5.setPrice(2);
//        option5.setShortDiscription("Adds 100 Messages");
//        optionDao.save(option5);
//
//        Option option7 = optionDao.getOptionByName("200Messages").get(0);
//
//        Option option6 = new Option();
//        option6.setName("Unlimited Messages");
//        option6.setPrice(200);
//        option6.setShortDiscription("Unlimited messages");
//
//        Set<Option> incompatibleToOption6Set = new HashSet<>();
//        incompatibleToOption6Set.add(option5);
//        incompatibleToOption6Set.add(option7);
//        option6.setIncompatibleOptionsList(incompatibleToOption6Set);
//
//        Option option10 = optionDao.getOptionByName("Movies and TV series").get(0);
//        Option option11 = optionDao.getOptionByName("Unlimited calls worldwide").get(0);
//        Option option12 = optionDao.getOptionByName("Unlimited messengers").get(0);
//
//        optionDao.save(option6);
//
        TariffDaoImpl tariffDao= new TariffDaoImpl();
//        Tariff tariffBase = tariffDao.getTariffByTariffName("Base").get(0);
//        tariffBase.addOption(option);
//        tariffBase.addOption(option5);
//        tariffBase.addOption(option3);
//
//        Tariff tariffStandart = tariffDao.getTariffByTariffName("Standart").get(0);
//        tariffStandart.addOption(option9);
//        tariffStandart.addOption(option5);
//        tariffStandart.addOption(option3);
//
//        Tariff tariffDaily = tariffDao.getTariffByTariffName("Daily").get(0);
//        tariffDaily.addOption(option2);
//        tariffDaily.addOption(option8);
//        tariffDaily.addOption(option7);
//        tariffDaily.addOption(option10);
//
//
//        Tariff tariffExtended = tariffDao.getTariffByTariffName("Extended").get(0);
//        tariffExtended.addOption(option4);
//        tariffExtended.addOption(option7);
//        tariffExtended.addOption(option10);
//        tariffExtended.addOption(option1);
//        tariffExtended.addOption(option12);
//
//
//        Tariff tariffBussiness = tariffDao.getTariffByTariffName("Bussiness").get(0);
//        tariffBussiness.addOption(option1);
//        tariffBussiness.addOption(option6);
//        tariffBussiness.addOption(option10);
//        tariffBussiness.addOption(option11);
//        tariffBussiness.addOption(option12);
//        tariffBussiness.addOption(option4);
//
//        tariffDao.update(tariffStandart);
//        tariffDao.update(tariffBase);
//        tariffDao.update(tariffDaily);
//        tariffDao.update(tariffExtended);
//        tariffDao.update(tariffBussiness);

//         Option option6 = new Option();
//         option6.setName("Obligatory Option1");
//         option6.setPrice(50);
//         option6.setShortDiscription("Obligatory Option1");



    }
}
