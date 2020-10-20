package eCare.controllers;

import com.google.gson.*;
import eCare.model.dto.ContractDTO;
import eCare.model.dto.OptionDTO;
import eCare.model.dto.TariffDTO;
import eCare.model.dto.UserDTO;
import eCare.model.entity.Option;
import eCare.services.api.ContractService;
import eCare.services.api.OptionService;
import eCare.services.api.TariffService;
import eCare.services.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

/**
 * Controller for page, in which cart finalisation occur.
 */

@Controller
public class CartPageController {

    @GetMapping(value = "/cartPage")
    public String getCartPage(Model model, HttpSession httpSession){

        Map<String, Map<String, String>> map = new HashMap<>();
        String num = "+79118903345";
        Map<String, String> mapOpt1 = new HashMap<>();
        String opt1 = "option1";
        String opt2 = "option2";
        mapOpt1.put(opt1, "true");
        mapOpt1.put(opt2, "false");

        map.put(num, mapOpt1);

        String num1 = "+79113423345";
        Map<String, String> mapOpt2 = new HashMap<>();
        String opt3 = "option3";
        String opt4 = "option4";
        String opt5 = "option5";
        mapOpt2.put(opt3, "false");
        mapOpt2.put(opt4, "false");
        mapOpt2.put(opt5, "true");

        map.put(num1,mapOpt2);

        model.addAttribute("contractsOptions", map);

        return "cartPage";
    }

}
