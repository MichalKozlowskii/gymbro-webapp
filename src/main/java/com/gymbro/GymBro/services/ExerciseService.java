package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ExerciseService {
    void saveExercise(ExerciseDto exerciseDto);
    void deleteExerciseById(Long id);
    void updateExerciseById(Long id, ExerciseDto exerciseDto);
    String getExerciseNameById(Long id);
    String getExerciseDescriptionById(Long id);
    Exercise findExerciseById(Long id);
    List<ExerciseDto> findAllExercisesOfUser(User user);
    ExerciseDto mapToExerciseDto(Exercise exercise);
    Exercise mapToExercise(ExerciseDto exerciseDto);
}
