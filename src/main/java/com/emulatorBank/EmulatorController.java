package com.emulatorBank;

import com.emulatorBank.requestresponse.EmulatorRequest;
import com.emulatorBank.requestresponse.Crutch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class EmulatorController {

    private EmulatorRequest emulatorRequest;

    @GetMapping
    public String main(Map<String, Object> model) {
        return "main";
    }

    @GetMapping("/send")
    public String sendMess(Map<String, Object> model) {
        model.put("sendMess", emulatorRequest.phone + " " + emulatorRequest.notif);
        return "send";
    }

    @PostMapping
    public String addNotif(@RequestParam String phone, @RequestParam String notif, Map<String, Object> model) {
        emulatorRequest = new EmulatorRequest();
        emulatorRequest.phone = phone.substring(2);
        emulatorRequest.notif = notif;
        Crutch.er = emulatorRequest;
        return "main";
    }
}
