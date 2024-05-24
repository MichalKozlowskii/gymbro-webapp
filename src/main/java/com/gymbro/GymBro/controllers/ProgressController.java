package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.WorkoutService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProgressController {

    @GetMapping("/progress")
    public String getProgress(Model model, @AuthenticationPrincipal User user) {

        return "progress";
    }
}
