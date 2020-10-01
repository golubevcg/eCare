package eCare;


import eCare.dao.impl.UserDaoImpl;

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

        UserDaoImpl userDao = new UserDaoImpl();
        System.out.println(userDao.getUserByLogin("agol").get(0).getListOfContracts().size());

    }
}
