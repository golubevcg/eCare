package eCare.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eCare.model.dto.OptionDTO;
import eCare.mq.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdPageController {

    final
    MessageSender messageSender;

    @Autowired
    JmsTemplate jmsTemplate;

    public AdPageController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @GetMapping("/adPage")
    public String getAdTextPage(Model model){
        model.addAttribute("adText", new String());
        return "adPage";
    }

    @PostMapping("/adPage/submit")
    public @ResponseBody
    String postAdTextPageUpdateAd(@RequestBody String adText){
        JsonObject json = new Gson().fromJson(adText, JsonObject.class);
        String parsedAdText = json.get("adText").getAsString();

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setName(parsedAdText);
        optionDTO.setPrice(123);
        optionDTO.setConnectionCost(11);

        messageSender.sendOptionDTO(optionDTO);

        return "true";
    }
}
