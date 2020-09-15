package eCare.database;

import eCare.database.entities.*;
import eCare.database.services.ContractService;
import eCare.database.services.RoleService;
import eCare.database.services.UserService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Role role = new Role();
        role.setRole("user");

        Role role1 = new Role();
        role.setRole("employee");

        User user = new User();
        user.setRole(role1);
        user.setFirstname("Andrew");
        user.setSecondname("Golubev");
        user.setPassword("123");
        user.setDateOfBirth(LocalDate.of(1995,05,11));

        RoleService roleService = new RoleService();
        roleService.save(role);
        roleService.save(role1);

        UserService userService = new UserService();
        userService.save(user);

        Contract contract = new Contract();
        contract.setContractNumber("+7-911-786-99-26");
        contract.setBlocked(false);
        Tarif tarif = new Tarif();
        tarif.setDiscription("пакет интернета 20 гигабайт");
        tarif.setPrice(100);
        contract.setTarif(tarif);


        Option option = new Option();
        option.setConnectionCost(50);
        option.setPrice(100);
        option.setDiscription("Опция 100 минут на месяц");

        ContractService contractService = new ContractService();
        contractService.save(contract);

    }
}
