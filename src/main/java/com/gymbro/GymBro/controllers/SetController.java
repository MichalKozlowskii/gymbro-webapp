package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.SetService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SetController {

    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final UserService userService;
    private final SetService setService;

    public SetController(WorkoutService workoutService, ExerciseService exerciseService, UserService userService, SetService setService) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.setService = setService;
    }

    @GetMapping("/workouts/{workoutId}/addset/{exerciseId}")
    public String showAddSetForm(@PathVariable("workoutId") Long workoutId,
                                 @PathVariable("exerciseId") Long exerciseId,
                                 Model model,
                                 @AuthenticationPrincipal User user) {

        Workout workout = workoutService.findWorkoutById(workoutId);
        String exerciseName = exerciseService.findExerciseById(exerciseId).getName();

        UserEntity userEntity = userService.findUserByName(user.getUsername());

        if (!userEntity.equals(workout.getUser())) {
            return "redirect:/workouts";
        }

        SetDto setDto = new SetDto();
        model.addAttribute("setDto", setDto);
        model.addAttribute("exerciseName", exerciseName);
        model.addAttribute("workoutId", workoutId);
        model.addAttribute("exerciseId", exerciseId);

        return "addset";
    }

    @PostMapping("/workouts/{workoutId}/addset/{exerciseId}/save")
    public String saveSet(@PathVariable("workoutId") Long workoutId,
                          @PathVariable("exerciseId") Long exerciseId,
                          @ModelAttribute SetDto setDto,
                          BindingResult result,
                          Model model,
                          @AuthenticationPrincipal User user) {

        if (setDto.getReps() < 1) {
            result.rejectValue("reps", null, "Reps number must be greater than one!");
        }

        if (setDto.getWeight() < 0) {
            result.rejectValue("weight", null, "Weight number must be greater than 0!");
        }

        if (result.hasErrors()) {
            model.addAttribute("setDto", setDto);
            model.addAttribute("exerciseName", exerciseService.findExerciseById(exerciseId).getName());
            model.addAttribute("workoutId", workoutId);
            model.addAttribute("exerciseId", exerciseId);

            return "addset";
        }

        Workout workout = workoutService.findWorkoutById(workoutId);
        UserEntity userEntity = userService.findUserByName(user.getUsername());

        if (!userEntity.equals(workout.getUser())) {
            return "redirect:/workouts";
        }

        setDto.setExerciseId(exerciseId);
        setDto.setWorkoutId(workoutId);
        setDto.setDateTime(LocalDateTime.now());

        workoutService.addSet(workout, setDto);

        return "redirect:/workouts";
    }
}
