package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutService {
    void saveWorkout(WorkoutDto workoutDto);
    Workout findWorkoutById(Long id);
    List<WorkoutDto> findAllWorkoutsOfUser(User user);
    WorkoutDto mapToWorkoutDto(Workout workout);
}
