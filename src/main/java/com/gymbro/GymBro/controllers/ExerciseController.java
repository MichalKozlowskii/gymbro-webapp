package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.services.ExerciseService;
import com.gymbro.GymBro.services.UserService;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final UserService userService;

    public ExerciseController(ExerciseService exerciseService, UserService userService) {
        this.exerciseService = exerciseService;
        this.userService = userService;
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

        if (result.hasErrors()) {
            model.addAttribute(exerciseDto);
            return "addexercise";
        }

        Long userId = userService.findUserByName(user.getUsername()).getId();
        exerciseDto.setUserId(userId);

        exerciseService.saveExercise(exerciseDto);
        return "redirect:/addexercise?succes";
    }
}
