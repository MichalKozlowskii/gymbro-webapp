package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutService {
    void saveWorkout(WorkoutDto workoutDto);
    void addSet(Workout workout, SetDto setDto);
    void deleteSet(Long workoutId, Long SetId);
    void deleteWorkout(Long workoutId);
    List<SetDto> findSetsOfExerciseInWorkout(WorkoutDto workoutDto, Long exerciseId);
    List<Workout> findByWorkoutPlan(WorkoutPlan workoutPlan);
    Workout findWorkoutById(Long id);
    List<WorkoutDto> findAllWorkoutsOfUser(User user);
    WorkoutDto mapToWorkoutDto(Workout workout);
    SetDto mapToSetDto(Set set);
}
