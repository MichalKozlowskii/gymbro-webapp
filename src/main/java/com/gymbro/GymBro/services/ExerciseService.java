package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.web.DTO.ExerciseDto;

import java.util.List;

public interface ExerciseService {
    void saveExercise(ExerciseDto exerciseDto);
    Exercise findExerciseById(Long id);
    List<Exercise> findAllExercisesOfUser(UserEntity user);
}
