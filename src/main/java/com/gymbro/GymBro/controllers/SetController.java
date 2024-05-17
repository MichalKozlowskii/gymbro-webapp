package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.services.WorkoutService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SetController {

    private final WorkoutService workoutService;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public SetController(WorkoutService workoutService, ExerciseService exerciseService, UserService userService) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.userService = userService;
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
        setDto.setExerciseId(exerciseId);
        setDto.setWorkoutId(workoutId);

        model.addAttribute("setDto", setDto);
        model.addAttribute("exerciseName", exerciseName);

        return "addset";
    }
}
