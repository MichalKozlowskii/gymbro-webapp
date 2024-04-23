package com.gymbro.GymBro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController {

    @RequestMapping("/settings")
    public String getSettings() {
        return "settings";
    }
}
