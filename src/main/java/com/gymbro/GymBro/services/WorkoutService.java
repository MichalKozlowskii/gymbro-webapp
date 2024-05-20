package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutService {
    void saveWorkout(WorkoutDto workoutDto);
    void addSet(Workout workout, SetDto setDto);
    Workout findWorkoutById(Long id);
    List<WorkoutDto> findAllWorkoutsOfUser(User user);
    WorkoutDto mapToWorkoutDto(Workout workout);
}
