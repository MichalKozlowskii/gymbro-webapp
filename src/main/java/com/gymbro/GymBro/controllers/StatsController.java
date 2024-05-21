package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.StatsService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public String getStats(Model model, @AuthenticationPrincipal User user) {
        Map<ExerciseDto, List<SetDto>> exerciseBestSetsMap = statsService.findBestSetsOfUsersExercises(user);

        model.addAttribute("exerciseBestSetsMap", exerciseBestSetsMap);

        return "stats";
    }
}
