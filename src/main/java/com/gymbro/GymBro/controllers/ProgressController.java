package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.services.ProgressService;
import com.gymbro.GymBro.services.UserService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ProgressController {
    private final UserService userService;
    private final WorkoutPlanService workoutPlanService;
    private final ProgressService progressService;
    private final WorkoutService workoutService;

    public ProgressController(UserService userService, WorkoutPlanService workoutPlanService,
                              ProgressService progressService, WorkoutService workoutService) {
        this.userService = userService;
        this.workoutPlanService = workoutPlanService;
        this.progressService = progressService;
        this.workoutService = workoutService;
    }

    @GetMapping("/progress")
    public String getProgress(Model model, @AuthenticationPrincipal User user) {
        List<WorkoutPlanDto> workoutPlans = workoutPlanService.findAllWorkoutPlansDtoOfUser(user);
        Map<WorkoutPlanDto, Pair<WorkoutDto, WorkoutDto>> workoutPlan2LastWorkouts = new HashMap<>();
        Map<WorkoutPlanDto, Map<ExerciseDto, String>> comparison = new HashMap<>();

        for (WorkoutPlanDto workoutPlan : workoutPlans) {
            Pair<WorkoutDto, WorkoutDto> lastWorkoutsPair = progressService.findTwoLastWorkoutsOfWorkoutPlanById(workoutPlan.getId());

            if (lastWorkoutsPair != null) {
                workoutPlan2LastWorkouts.put(workoutPlan, lastWorkoutsPair);

                comparison.put(workoutPlan, progressService.getTwoWorkoutsComparison(lastWorkoutsPair.key(),
                        lastWorkoutsPair.value()));
            }
        }

        model.addAttribute("workoutPlan2LastWorkoutsMap", workoutPlan2LastWorkouts);
        model.addAttribute("comparison", comparison);


        return "progress";
    }

    @GetMapping("/progress/{workoutPlanId}")
    public String getProgressOfWorkoutPlan(@PathVariable("workoutPlanId") Long workoutPlanId,
                                           @RequestParam("compareworkout") Long workoutId,
                                           Model model,
                                           @AuthenticationPrincipal User user) {

        WorkoutPlanDto workoutPlanDto = workoutPlanService.mapToWorkoutPlanDto(
                workoutPlanService.findWorkoutPlanById(workoutPlanId)
        );

        Long userId = userService.findUserByName(user.getUsername()).getId();
        if (!userId.equals(workoutPlanDto.getUserId())) {
            return "redirect:/progress";
        }

        List<WorkoutDto> workouts = workoutService.findByWorkoutPlan(workoutPlanService.findWorkoutPlanById(workoutPlanId)).stream()
                .sorted(Comparator.comparing(Workout::getDateTime).reversed())
                .map(workoutService::mapToWorkoutDto)
                .toList();

        WorkoutDto lastWorkout = workouts.get(0);
        WorkoutDto workout2 = workoutService.mapToWorkoutDto(workoutService.findWorkoutById(workoutId));

        Map<ExerciseDto, String> comparison = progressService.getTwoWorkoutsComparison(lastWorkout, workout2);

        List<Pair<LocalDateTime, Double>> progressPlotData = progressService.getWorkoutsScore(workouts);
        Map<ExerciseDto, List<Pair<LocalDateTime, Double>>> exercisesWeightPlotData = progressService.getExercisesWeightPlotData(workouts);

        model.addAttribute("workouts", workouts.subList(1, workouts.size()));
        model.addAttribute("workoutPlan", workoutPlanDto);
        model.addAttribute("lastWorkout", lastWorkout);
        model.addAttribute("workout2", workout2);
        model.addAttribute("comparison", comparison);
        model.addAttribute("progressPlotData", progressPlotData);
        model.addAttribute("exercisesWeightPlotData", exercisesWeightPlotData);

        return "workoutplanprogress";
    }
}
