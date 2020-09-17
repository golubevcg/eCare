package eCare.database;


import eCare.database.entities.*;
import eCare.database.services.OptionService;
import eCare.database.services.TarifService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Option option1 = new Option();
        option1.setName("opt1");
        option1.setShortDiscription("erfe");

        Option option2 = new Option();
        option2.setName("opt2");
        option2.setShortDiscription("sdfsfd");


        Option option3 = new Option();
        option3.setName("opt3");
        option3.setShortDiscription("hngd");

        Option option4 = new Option();
        option4.setName("opt4");
        option4.setShortDiscription("xcva");

        OptionService optionService = new OptionService();
        optionService.save(option1);

        Option resultOption = optionService.getOptionByName("opt1").get(0);

        System.out.println(resultOption.getIncompatibleOptionsList().toString());

        option1.addObligatoryOption(option4);
        option1.addObligatoryOption(option3);

        optionService.update(option1);
        System.out.println(null==option1.getObligatoryOptionsList().get(0));
        System.out.println(option1.getObligatoryOptionsList().get(0).getName());
        System.out.println(option1.getObligatoryOptionsList().get(1).getName());

    }
}
