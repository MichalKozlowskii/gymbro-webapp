package com.gymbro.GymBro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatsController {

    @GetMapping("/stats")
    public String getStats() {
        return "stats";
    }
}
