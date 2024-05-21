package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgressController {

    @GetMapping("/progress")
    public String getProgress() {
        return "progress";
    }
}
