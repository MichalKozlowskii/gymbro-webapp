package com.gymbro.GymBro.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WorkoutsController {

    @GetMapping("/workouts")
    public String getWorkouts(Model model, @AuthenticationPrincipal User user) {
        return "workouts";
    }
}
