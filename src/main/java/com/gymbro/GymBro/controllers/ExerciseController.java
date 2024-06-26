package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutPlanService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final UserService userService;
    private final WorkoutPlanService workoutPlanService;

    public ExerciseController(ExerciseService exerciseService, UserService userService, WorkoutPlanService workoutPlanService) {
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.workoutPlanService = workoutPlanService;
    }

    @GetMapping("/exercises")
    public String getExercises(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("exercises",
                exerciseService.findAllExercisesOfUser(user));
        return "exercises";
    }

    @GetMapping("/addexercise")
    public String showAddExerciseForm(Model model) {
        ExerciseDto exerciseDto = new ExerciseDto();
        model.addAttribute("exercise", exerciseDto);

        return "addexercise";
    }

    @PostMapping("/addexercise/save")
    public String addExercise(@ModelAttribute("exercise") ExerciseDto exerciseDto,
                              BindingResult result,
                              Model model,
                              @AuthenticationPrincipal User user) {

        if (exerciseDto.getName().isEmpty()) {
            result.rejectValue("name", null, "Name field can't be blank!");
        }

        if (result.hasErrors()) {
            model.addAttribute(exerciseDto);
            return "addexercise";
        }

        Long userId = userService.findUserByName(user.getUsername()).getId();
        exerciseDto.setUserId(userId);

        exerciseService.saveExercise(exerciseDto);
        return "redirect:/exercises?addsuccess";
    }

    @DeleteMapping("/exercises/delete/{id}")
    public String deleteExercise(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Long userId = userService.findUserByName(user.getUsername()).getId();

        Exercise exercise = exerciseService.findExerciseById(id);
        Long exerciseUserId = exercise.getUser().getId();

        if (!Objects.equals(userId, exerciseUserId)) {
            return "redirect:/exercises";
        }

        List<WorkoutPlan> workoutPlans = workoutPlanService.findByExercisesContaining(exercise);
        if (!workoutPlans.isEmpty()) {
            return "redirect:/exercises?deletefail";
        }

        exerciseService.deleteExerciseById(id);

        return "redirect:/exercises?deletesuccess";
    }

    @GetMapping("exercises/edit/{id}")
    public String showEditExerciseForm(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        Exercise exercise = exerciseService.findExerciseById(id);
        ExerciseDto exerciseDto = exerciseService.mapToExerciseDto(exercise);

        Long userId = userService.findUserByName(user.getUsername()).getId();
        if (!Objects.equals(userId, exerciseDto.getUserId())) {
            return "redirect:/exercises";
        }

        model.addAttribute("exercise", exerciseDto);

        return "editexercise";
    }

    @PutMapping("exercises/edit/{id}")
    public String editExercise(@PathVariable Long id,
                               @ModelAttribute("exercise") ExerciseDto exerciseDto,
                               BindingResult result,
                               Model model,
                               @AuthenticationPrincipal User user) {

        Long userId = userService.findUserByName(user.getUsername()).getId();
        Long exerciseUserId = exerciseService.findExerciseById(id).getUser().getId();
        if (!Objects.equals(userId, exerciseDto.getUserId())) {
            return "redirect:/exercises";
        }

        if (exerciseDto.getName().isEmpty()) {
            result.rejectValue("name", null, "Name field can't be blank!");
        }

        if (result.hasErrors()) {
            model.addAttribute(exerciseDto);
            return "editexercise";
        }

        exerciseService.updateExerciseById(exerciseDto.getId(), exerciseDto);

        return "redirect:/exercises?editsuccess";
    }
}
