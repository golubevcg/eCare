package eCare;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.dao.impl.ContractDaoImpl;
import eCare.dao.impl.OptionDaoImpl;
import eCare.dao.impl.TariffDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.converters.ContractMapper;
import eCare.model.converters.OptionMapper;
import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Contract;
import eCare.model.enitity.Option;
import eCare.model.enitity.Tariff;
import eCare.model.enitity.User;
import eCare.services.impl.ContractServiceImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserByLogin("agol").get(0);

        Set<Contract> contractsSet = user.getListOfContracts();
        ArrayList<Contract> contractList = new ArrayList<>();
        contractList.addAll(contractsSet);

        for (int i = 0; i < contractList.size(); i++) {
            System.out.println(contractList.get(i).getContract_id());
        }
        System.out.println("\n");


        for (Contract contract2 : contractList) {
            System.out.println(contract2.getContract_id());
        }

        Collections.sort(contractList);
        System.out.println("++++++++++++++++++++");

        for (int i = 0; i < contractList.size(); i++) {
            System.out.println(contractList.get(i).getContract_id());
        }

        System.out.println("\n");
        for (Contract contractDTO1 : contractList) {
            System.out.println(contractDTO1.getContract_id());
        }
    }
}
