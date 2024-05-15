package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WorkoutsController {

    private final WorkoutService workoutService;

    public WorkoutsController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/workouts")
    public String getWorkouts(Model model, @AuthenticationPrincipal User user) {
        List<WorkoutDto> workouts = workoutService.findAllWorkoutsOfUser(user);

        model.addAttribute("workouts", workouts);

        return "workouts";
    }
}
