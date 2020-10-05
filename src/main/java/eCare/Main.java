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
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String testSearch = "65";
        List<Contract> contractsList = session.createQuery(
                "select c " +
                        "from Contract c " +
                        "where c.contractNumber like:string", Contract.class)
                .setParameter("string", "%" + testSearch + "%").list();

        transaction.commit();
        session.close();

        System.out.println(contractsList.size());


    }
}
