package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.StatsService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        List<ExerciseDto> exercisesSortedByWeight = exerciseBestSetsMap.keySet().stream()
                        .sorted(Comparator.comparing(exerciseDto ->
                                exerciseBestSetsMap.get(exerciseDto).get(0).getWeight(),
                                Comparator.reverseOrder()
                        ))
                        .toList();
        model.addAttribute("exercises", exercisesSortedByWeight);

        return "stats";
    }

    @GetMapping("/stats/sorted")
    public String sortExercises(@RequestParam("sortOptions") int value,
                                Model model,
                                @AuthenticationPrincipal User user) {

        Map<ExerciseDto, List<SetDto>> exerciseBestSetsMap = statsService.findBestSetsOfUsersExercises(user);
        model.addAttribute("exerciseBestSetsMap", exerciseBestSetsMap);

        List<ExerciseDto> exercisesSorted = new ArrayList<>();

        // 1 - Weight: Descending
        // 2 - Weight: Ascending
        // 3 - Reps: Descending
        // 4 - Reps: Ascending
        switch (value) {
            case 1: {
                exercisesSorted = exerciseBestSetsMap.keySet().stream()
                        .sorted(Comparator.comparing(exerciseDto ->
                                        exerciseBestSetsMap.get(exerciseDto).get(0).getWeight(),
                                Comparator.reverseOrder()
                        ))
                        .toList();
                break;
            }

            case 2: {
                exercisesSorted = exerciseBestSetsMap.keySet().stream()
                        .sorted(Comparator.comparing(exerciseDto ->
                                        exerciseBestSetsMap.get(exerciseDto).get(0).getWeight()
                        ))
                        .toList();
                break;
            }

            case 3: {
                exercisesSorted = exerciseBestSetsMap.keySet().stream()
                        .sorted(Comparator.comparing(exerciseDto ->
                                        exerciseBestSetsMap.get(exerciseDto).get(1).getReps(),
                                Comparator.reverseOrder()
                        ))
                        .toList();
                break;
            }

            case 4: {
                exercisesSorted = exerciseBestSetsMap.keySet().stream()
                        .sorted(Comparator.comparing(exerciseDto ->
                                        exerciseBestSetsMap.get(exerciseDto).get(1).getReps()
                        ))
                        .toList();
                break;
            }
        }
        model.addAttribute("exercises", exercisesSorted);

        return "stats";
    }
}
