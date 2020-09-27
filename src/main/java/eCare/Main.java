package eCare;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eCare.dao.impl.OptionDaoImpl;
import eCare.dao.impl.TariffDaoImpl;
import eCare.model.enitity.Option;
import eCare.model.enitity.Tariff;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        TariffDaoImpl tariffDao = new TariffDaoImpl();
//
//        Tariff tariff1 = new Tariff();
//        tariff1.setName("Стандартный");
//        tariff1.setPrice(100);
//        tariff1.setShortDiscription("Описание тарифа стандартного");
//        tariff1.setActive(true);
//
//        Tariff tariff2 = new Tariff();
//        tariff2.setName("Базовый");
//        tariff2.setPrice(50);
//        tariff2.setShortDiscription("Описание тарифа базового");
//        tariff2.setActive(true);
//
//        Tariff tariff3 = new Tariff();
//        tariff3.setName("Расширенный");
//        tariff3.setPrice(200);
//        tariff3.setShortDiscription("Описание тарифа расширенного");
//        tariff3.setActive(true);
//
//        Tariff tariff4 = new Tariff();
//        tariff4.setName("СуперРасширенный");
//        tariff4.setPrice(100);
//        tariff4.setShortDiscription("Описание тарифа стандартного");
//        tariff4.setActive(false);
//
        Option option = new Option();
        option.setConnectionCost(100);
        option.setPrice(50);
        option.setName("option1");

        Option option1 = new Option();
        option1.setConnectionCost(22);
        option1.setPrice(33);
        option1.setName("option1");

        Option option2 = new Option();
        option2.setConnectionCost(44);
        option2.setPrice(11);
        option2.setName("option2");

        Option option3 = new Option();
        option3.setConnectionCost(88);
        option3.setPrice(55);
        option3.setName("option3");

        Option option4 = new Option();
        option4.setConnectionCost(5);
        option4.setPrice(11);
        option4.setName("option4");
//
//        tariff1.addOption(option1);
//        tariff1.addOption(option2);
//        tariff2.addOption(option3);
//        tariff3.addOption(option4);
//        tariff3.addOption(option2);
//
//        tariffDao.save(tariff1);
//        tariffDao.save(tariff2);
//        tariffDao.save(tariff3);
//        tariffDao.save(tariff4);

        OptionDaoImpl optionDao = new OptionDaoImpl();
        TariffDaoImpl tariffDao = new TariffDaoImpl();

        List<Option> optionList = tariffDao.getTariffByTariffName("Standart").get(0).getListOfOptions();
        tariffDao.getActiveTariffs();

        List<String> optionListNames = new ArrayList<>();

        for (Option entity: optionList
             ) {
            optionListNames.add(entity.getName());
        }
        //        optionList.add(option1);
//        optionList.add(option2);
//        optionList.add(option3);
//        optionList.add(option4);
        System.out.println(optionList.size());

//        Type optionType = new TypeToken<Option>(){}.getType();
//        Gson gson1 = new Gson();
//        String jsonOptions = gson1.toJson(optionList, optionType);

//        System.out.println("-------------------------------");
//        System.out.println("-------------------------------");
//        System.out.println("-------------------------------");
//        System.out.println(jsonOptions);
        System.out.println(new Gson().toJson(optionListNames));
    }
}
