package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;
    private final UserService userService;


    public WorkoutPlanController(WorkoutPlanService workoutPlanService, ExerciseService exerciseService, UserService userService) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @GetMapping("/workoutplans")
    public String getWorkoutPlans(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("workout_plans", workoutPlanService.findAllWorkoutPlansOfUser(user));

        return "workoutplans";
    }

    @GetMapping("/addworkoutplan")
    public String showAddWorkoutPlanForm(Model model, @AuthenticationPrincipal User user) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();

        model.addAttribute("workoutPlan", workoutPlanDto);
        model.addAttribute("exercisesDto",
                exerciseService.findAllExercisesOfUser(user));

        return "addworkoutplan";
    }

    @PostMapping("/addworkoutplan/save")
    public String saveWorkoutPlan(@ModelAttribute("workoutPlan") WorkoutPlanDto workoutPlanDto,
                                  BindingResult result,
                                  Model model,
                                  @AuthenticationPrincipal User user) {

        if (workoutPlanDto.getName() == null || Objects.equals(workoutPlanDto.getName(), "")) {
            result.rejectValue("name", null, "Name field can't be blank!");
        }

        List<Long> exerciseIds = workoutPlanDto.getExercisesIds();
        exerciseIds.removeIf(Objects::isNull);

        List<Integer> sets = workoutPlanDto.getSets();
        sets.removeIf(Objects::isNull);

        List<Integer> reps = workoutPlanDto.getReps();
        reps.removeIf(Objects::isNull);

        if (workoutPlanDto.getExercisesIds() == null || exerciseIds.isEmpty()) {
            result.rejectValue("name", null, "You have to select exercises!");
        }
        if (workoutPlanDto.getSets() == null || sets.isEmpty() ) {
            result.rejectValue("name", null,
                    "You have to specify amount of sets for each exercise!");
        }

        if (workoutPlanDto.getReps() == null || reps.isEmpty()) {
            result.rejectValue("name", null,
                    "You have to specify amount of reps for each exercise!");
        }

        if (sets.size() != reps.size() || sets.size() != exerciseIds.size()) {
            result.rejectValue("name", null,
                    "Each exercises' sets and reps need to be specified.");
        }

        if (result.hasErrors()) {
            model.addAttribute("workoutPlan", workoutPlanDto);
            model.addAttribute("exercisesDto",
                    exerciseService.findAllExercisesOfUser(user));

            return "addworkoutplan";
        }

        workoutPlanDto.setExercisesIds(exerciseIds);
        workoutPlanDto.setSets(sets);
        workoutPlanDto.setReps(reps);

        Long userId = userService.findUserByName(user.getUsername()).getId();
        workoutPlanDto.setUserId(userId);

        workoutPlanService.saveWorkoutPlan(workoutPlanDto);

        return "redirect:/workoutplans?addsuccess";
    }
}
