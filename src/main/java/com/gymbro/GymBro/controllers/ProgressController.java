package com.gymbro.GymBro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgressController {

    @RequestMapping("/progress")
    public String getProgress() {
        return "progress";
    }
}
