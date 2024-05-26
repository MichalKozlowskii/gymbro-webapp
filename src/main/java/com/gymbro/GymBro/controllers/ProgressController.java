package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.services.ProgressService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProgressController {
    private final WorkoutService workoutService;
    private final WorkoutPlanService workoutPlanService;
    private final ProgressService progressService;

    public ProgressController(WorkoutService workoutService,WorkoutPlanService workoutPlanService, ProgressService progressService) {
        this.workoutService = workoutService;
        this.progressService = progressService;
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/progress")
    public String getProgress(Model model, @AuthenticationPrincipal User user) {
        List<WorkoutPlanDto> workoutPlans = workoutPlanService.findAllWorkoutPlansDtoOfUser(user);
        Map<WorkoutPlanDto, Pair<WorkoutDto, WorkoutDto>> workoutPlan2LastWorkouts = new HashMap<>();
        Map<WorkoutPlanDto, Map<ExerciseDto, String>> comparison = new HashMap<>();

        for (WorkoutPlanDto workoutPlan : workoutPlans) {
            Pair<WorkoutDto, WorkoutDto> lastWorkoutsPair = progressService.findTwoLastWorkoutsOfWorkoutPlanById(workoutPlan.getId());

            if (lastWorkoutsPair.key() != null || lastWorkoutsPair.value() != null) {
                workoutPlan2LastWorkouts.put(workoutPlan, lastWorkoutsPair);

                comparison.put(workoutPlan, progressService.getTwoWorkoutsComparison(lastWorkoutsPair.key(),
                        lastWorkoutsPair.value()));
            }
        }

        model.addAttribute("workoutPlan2LastWorkoutsMap", workoutPlan2LastWorkouts);
        model.addAttribute("comparison", comparison);


        return "progress";
    }
}
