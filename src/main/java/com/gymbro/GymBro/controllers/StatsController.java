package com.gymbro.GymBro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatsController {

    @RequestMapping("/stats")
    public String getWorkouts() {
        return "stats";
    }
}
