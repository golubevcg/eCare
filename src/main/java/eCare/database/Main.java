package eCare.database;


import eCare.database.entities.*;
import eCare.database.services.RoleService;
import eCare.database.services.UserService;

public class Main {
    public static void main(String[] args) {

//        Role role = new Role();
//        role.setRolename("user");
//
//        Role role1 = new Role();
//        role1.setRolename("employee");
//
//        Role role2 = new Role();
//        role2.setRolename("admin");
//
//        RoleService roleService = new RoleService();
//        roleService.save(role1);
//        roleService.save(role2);
//        roleService.save(role);
//
//        User user2 = new User();
//        user2.setLogin("log");
//        user2.setPassword("sdfsf");
//
//        user2.setRole(role);
//
//        Contract contract = new Contract();
//        contract.setContractNumber("+4444");
//
//        user2.addContract(contract);
//
//        Tariff tariff = new Tariff();
//        tariff.setName("tariff");
//
//        contract.setTariff(tariff);
//
//        Option option11 = new Option();
//        option11.setName("option11");
//
//        tariff.addOption(option11);
//
//        Option option12 = new Option();
//        option12.setName("option12");
//
//        Option option13 = new Option();
//        option13.setName("option13");
//        OptionService optionService = new OptionService();
//
//        System.out.println(option11.getObligatoryOptionsList().size());
//        option11.addObligatoryOption(option12);
//        System.out.println(option11.getObligatoryOptionsList().size());
//        option11.addIncompatibleOption(option12);
//
//        UserService userService = new UserService();
//        userService.save(user2);

        Role role = new Role();
        role.setRolename("user");

        Role role1 = new Role();
        role1.setRolename("employee");

        RoleService roleService = new RoleService();
        roleService.save(role);

//        User user1 = new User();
//        user1.setLogin("testingRolesLogin");
//        user1.setPassword("pwd");
//        user1.checkRoleInDBAndReturnIfFounded(role);
//
//        UserService userService = new UserService();
//        userService.save(user1);

    }
}
