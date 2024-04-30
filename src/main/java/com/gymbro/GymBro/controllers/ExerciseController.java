package com.gymbro.GymBro.controllers;

import com.gymbro.GymBro.web.DTO.ExerciseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExerciseController {

    @GetMapping("/addexercise")
    public String showAddExerciseForm(Model model) {
        ExerciseDto exerciseDto = new ExerciseDto();
        model.addAttribute("name");
        model.addAttribute("description");

        return "addexercise";
    }
}
