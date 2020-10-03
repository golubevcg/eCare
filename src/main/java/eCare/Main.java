package eCare;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eCare.dao.impl.ContractDaoImpl;
import eCare.dao.impl.OptionDaoImpl;
import eCare.dao.impl.UserDaoImpl;
import eCare.model.converters.OptionMapper;
import eCare.model.dto.OptionDTO;
import eCare.model.enitity.Contract;
import eCare.model.enitity.Option;
import eCare.services.impl.ContractServiceImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

//        UserDaoImpl userDaoImpl = new UserDaoImpl();
//        User user = userDaoImpl.getUserByLogin("agol").get(0);
//
//        Contract contract = user.getListOfContracts().get(0);
//
//        Contract contract1 = new Contract();
//        contract1.setContractNumber("+79119876543");
//        contract1.setUser(user);
//
//        Contract contract2 = new Contract();
//        contract2.setContractNumber("+79115436784");
//        contract2.setUser(user);
//
//        Contract contract3 = new Contract();
//        contract3.setContractNumber("+79112659865");
//        contract3.setUser(user);
//
//        user.addContract(contract1);
//        user.addContract(contract2);
//        user.addContract(contract3);
//
//        TariffDaoImpl tariffDaoImpl = new TariffDaoImpl();
//        Tariff tariff1 = tariffDaoImpl.getTariffByTariffName("Standart").get(0);
//        Tariff tariff4 = tariffDaoImpl.getTariffByTariffName("Extended").get(0);
//        Tariff tariff5 = tariffDaoImpl.getTariffByTariffName("Bussiness").get(0);
//
//        contract1.setTariff(tariff1);
//        contract2.setTariff(tariff4);
//        contract3.setTariff(tariff5);

//        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
//        contractDaoImpl.save(contract1);
//        contractDaoImpl.save(contract2);
//        contractDaoImpl.save(contract3);
//        OptionDaoImpl optionDaoImpl = new OptionDaoImpl();
//
//        Option option = optionDaoImpl.getOptionByName("Unlimited messengers").get(0);
//
//        Contract contract = contractDaoImpl.getContractByNumber("+79117684563").get(0);
//
//        contract.addOption(option);
//
//        contractDaoImpl.update(contract);
//        ContractDaoImpl contractDaoImpl = new ContractDaoImpl();
//
//        OptionMapper optionMapper = new OptionMapper();
//
//        Set<Option> optionDTOHashSet = contractDaoImpl.getContractByNumber("+79117684563")
//                        .get(0)
//                        .getSetOfOptions();
//
//        Option option = new Option();
//        option.setName("erwer");
//        option.setPrice(100);
//
//        OptionDTO optionDTO = optionMapper.toDTO(option);
//        System.out.println(optionDTO.getName());
//
//
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//
//        System.out.println(gson.toJson(optionDTOHashSet));
    }
}
