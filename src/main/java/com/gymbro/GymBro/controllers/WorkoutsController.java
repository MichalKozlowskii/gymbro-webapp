package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class WorkoutsController {

    private final WorkoutService workoutService;
    private final WorkoutPlanService workoutPlanService;
    private final UserService userService;

    public WorkoutsController(WorkoutService workoutService, WorkoutPlanService workoutPlanService, UserService userService) {
        this.workoutService = workoutService;
        this.workoutPlanService = workoutPlanService;
        this.userService = userService;
    }

    @GetMapping("/workouts")
    public String getWorkouts(Model model, @AuthenticationPrincipal User user) {
        List<WorkoutDto> workouts = workoutService.findAllWorkoutsOfUser(user);

        Map<WorkoutDto, WorkoutPlan> workoutDtoWorkoutPlanMap = new HashMap<>();

        for (WorkoutDto workoutDto : workouts) {
            WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(workoutDto.getWorkoutPlanId());

            workoutDtoWorkoutPlanMap.put(workoutDto, workoutPlan);
        }

        model.addAttribute("workouts", workouts);
        model.addAttribute("workoutDtoWorkoutPlanMap", workoutDtoWorkoutPlanMap);

        return "workouts";
    }

    @GetMapping("/addworkout")
    public String showAddWorkoutForm(Model model, @AuthenticationPrincipal User user) {
        WorkoutDto workoutDto = new WorkoutDto();
        List<WorkoutPlanDto> workoutPlansDto = workoutPlanService.findAllWorkoutPlansOfUser(user);

        model.addAttribute("workout", workoutDto);
        model.addAttribute("workoutplans", workoutPlansDto);

        return "addworkout";
    }

    @PostMapping("/addworkout/save")
    public String saveWorkout(@ModelAttribute("workout") WorkoutDto workoutDto,
                              BindingResult result,
                              @AuthenticationPrincipal User user) {

        if (workoutDto.getWorkoutPlanId() == null || workoutDto.getWorkoutPlanId() < 0) {
            result.rejectValue("workoutPlanId", null, "You have to choose a workout plan!");
        }

        Long userId = userService.findUserByName(user.getUsername()).getId();
        workoutDto.setUserId(userId);
        workoutDto.setDateTime(LocalDateTime.now());

        workoutService.saveWorkout(workoutDto);

        return "redirect:/workouts?addsuccess";
    }
}
