package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final WorkoutService workoutService;


    public WorkoutPlanController(WorkoutPlanService workoutPlanService, ExerciseService exerciseService, UserService userService, WorkoutService workoutService) {
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.workoutService = workoutService;
    }

    private boolean validateWorkoutPlan(WorkoutPlanDto workoutPlanDto, BindingResult result) {
        if (workoutPlanDto.getName() == null || Objects.equals(workoutPlanDto.getName(), "")) {
            result.rejectValue("name", null, "Name field can't be blank!");
        }

        List<Long> exerciseIds = workoutPlanDto.getExercisesIds();
        exerciseIds.removeIf(Objects::isNull);

        List<Integer> sets = workoutPlanDto.getSets();
        sets.removeIf(Objects::isNull);

        List<Integer> reps = workoutPlanDto.getReps();
        reps.removeIf(Objects::isNull);

        boolean hasErrors = false;

        if (workoutPlanDto.getExercisesIds() == null || exerciseIds.isEmpty()) {
            result.rejectValue("name", null, "You have to select exercises!");
            hasErrors = true;
        }
        if (workoutPlanDto.getSets() == null || sets.isEmpty()) {
            result.rejectValue("name", null, "You have to specify amount of sets for each exercise!");
            hasErrors = true;
        }
        if (workoutPlanDto.getReps() == null || reps.isEmpty()) {
            result.rejectValue("name", null, "You have to specify amount of reps for each exercise!");
            hasErrors = true;
        }
        if (sets.size() != reps.size() || sets.size() != exerciseIds.size()) {
            result.rejectValue("name", null, "Each exercises' sets and reps need to be specified.");
            hasErrors = true;
        }

        if (hasErrors) {
            return false;
        }

        workoutPlanDto.setExercisesIds(exerciseIds);
        workoutPlanDto.setSets(sets);
        workoutPlanDto.setReps(reps);

        return true;
    }

    @GetMapping("workoutplans")
    public String getWorkoutPlans(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("workout_plans", workoutPlanService.findAllWorkoutPlansOfUser(user));

        return "workoutplans";
    }

    @GetMapping("addworkoutplan")
    public String showAddWorkoutPlanForm(Model model, @AuthenticationPrincipal User user) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();

        model.addAttribute("workoutPlan", workoutPlanDto);
        model.addAttribute("exercisesDto",
                exerciseService.findAllExercisesOfUser(user));

        return "addworkoutplan";
    }

    @PostMapping("addworkoutplan/save")
    public String saveWorkoutPlan(@ModelAttribute("workoutPlan") WorkoutPlanDto workoutPlanDto,
                                  BindingResult result,
                                  Model model,
                                  @AuthenticationPrincipal User user) {

        if (!validateWorkoutPlan(workoutPlanDto, result)) {
            model.addAttribute("workoutPlan", workoutPlanDto);
            model.addAttribute("exercisesDto", exerciseService.findAllExercisesOfUser(user));
            return "addworkoutplan";
        }

        Long userId = userService.findUserByName(user.getUsername()).getId();
        workoutPlanDto.setUserId(userId);

        workoutPlanService.saveWorkoutPlan(workoutPlanDto);

        return "redirect:/workoutplans?addsuccess";
    }

    @GetMapping("workoutplans/edit/{id}")
    public String showEditWorkoutPlanForm(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(id);
        WorkoutPlanDto workoutPlanDto = workoutPlanService.mapToWorkoutPlanDto(workoutPlan);

        List<String> selectedExercisesNames = workoutPlan.getExercises().stream()
                .map(Exercise::getName)
                .toList();

        Long userId = userService.findUserByName(user.getUsername()).getId();
        if (!Objects.equals(userId, workoutPlanDto.getUserId())) {
            return "redirect:/workoutplans";
        }

        model.addAttribute("workoutPlan", workoutPlanDto);
        model.addAttribute("selectedExercisesNames", selectedExercisesNames);
        model.addAttribute("exercisesDto",
                exerciseService.findAllExercisesOfUser(user));

        return "editworkoutplan";
    }

    @PutMapping("workoutplans/edit/{id}")
    public String editWorkoutPlan(@PathVariable Long id,
                                  @ModelAttribute("workoutPlan") WorkoutPlanDto workoutPlanDto,
                                  BindingResult result,
                                  Model model,
                                  @AuthenticationPrincipal User user) {

        Long userId = userService.findUserByName(user.getUsername()).getId();
        Long workoutPlanUserId = workoutPlanService.findWorkoutPlanById(id).getUser().getId();

        if (!Objects.equals(userId, workoutPlanUserId)) {
            return "redirect:/workoutplans";
        }

        if (!validateWorkoutPlan(workoutPlanDto, result)) {
            model.addAttribute("workoutPlan", workoutPlanDto);
            model.addAttribute("selectedExercisesNames", workoutPlanDto.getExercisesIds().stream()
                    .map(exerciseService::findExerciseById)
                    .map(Exercise::getName)
                    .toList());
            model.addAttribute("exercisesDto", exerciseService.findAllExercisesOfUser(user));
            return "editworkoutplan";
        }

        System.out.println(workoutPlanDto.toString());

        workoutPlanDto.setUserId(userId);

        workoutPlanService.saveWorkoutPlan(workoutPlanDto, id);

        return "redirect:/workoutplans?editsuccess";
    }

    @DeleteMapping("workoutplans/delete/{id}")
    public String deleteWorkoutPlan(@PathVariable Long id, @AuthenticationPrincipal User user) {

        Long userId = userService.findUserByName(user.getUsername()).getId();

        WorkoutPlan workoutPlan = workoutPlanService.findWorkoutPlanById(id);
        Long workoutPlanUserId = workoutPlan.getUser().getId();

        if (!Objects.equals(userId, workoutPlanUserId)) {
            return "redirect:/workoutplans";
        }

        List<Workout> workouts = workoutService.findByWorkoutPlan(workoutPlan);
        if (!workouts.isEmpty()) {
            return "redirect:/workoutplans?deletefail";
        }

        workoutPlanService.deleteWorkoutPlanById(id);

        return "redirect:/workoutplans?deletesuccess";
    }
}
