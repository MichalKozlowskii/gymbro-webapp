package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.services.SetService;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class WorkoutsController {

    private final WorkoutService workoutService;
    private final WorkoutPlanService workoutPlanService;
    private final UserService userService;
    private final SetService setService;

    public WorkoutsController(WorkoutService workoutService, WorkoutPlanService workoutPlanService,
                              UserService userService, SetService setService) {
        this.workoutService = workoutService;
        this.workoutPlanService = workoutPlanService;
        this.userService = userService;
        this.setService = setService;
    }

    @GetMapping("/workouts")
    public String getWorkouts(Model model, @AuthenticationPrincipal User user) {
        List<WorkoutDto> workouts = workoutService.findAllWorkoutsOfUser(user);

        Map<WorkoutDto, WorkoutPlan> workoutDtoWorkoutPlanMap = new HashMap<>();
        Map<WorkoutDto, List<Set>> workoutDtoSetMap = new HashMap<>();

        for (WorkoutDto workoutDto : workouts) {
            WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(workoutDto.getWorkoutPlanId());

            workoutDtoWorkoutPlanMap.put(workoutDto, workoutPlan);
            workoutDtoSetMap.put(workoutDto, setService.findByWorkoutId(workoutDto.getId()));
        }

        model.addAttribute("workouts", workouts);
        model.addAttribute("workoutDtoWorkoutPlanMap", workoutDtoWorkoutPlanMap);
        model.addAttribute("workoutDtoSetMap", workoutDtoSetMap);
        model.addAttribute("setService", setService);

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

    @DeleteMapping("/workouts/delete/{id}")
    public String deleteWorkout(@PathVariable("id") Long id,
                                @AuthenticationPrincipal User user) {

        Workout workout = workoutService.findWorkoutById(id);
        if (!Objects.equals(workout.getUser().getName(), user.getUsername())) {
            return "redirect:/workouts";
        }

        workoutService.deleteWorkout(id);

        return "redirect:/workouts?deletesuccess";
    }
}
