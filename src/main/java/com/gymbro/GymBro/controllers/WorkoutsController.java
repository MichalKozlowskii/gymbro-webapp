package com.gymbro.GymBro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkoutsController {

    @RequestMapping("/workouts")
    public String getWorkouts() {
        return "workouts";
    }
}
