package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutPlanService {
    void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto);
    void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto, Long id);
    void deleteWorkoutPlanById(Long id);
    List<WorkoutPlan> findByExercisesContaining(Exercise exercise);
    WorkoutPlan findWorkoutPlanById(Long id);
    List<WorkoutPlan> findAllWorkoutPlansOfUser(User user);
    List<WorkoutPlanDto> findAllWorkoutPlansDtoOfUser(User user);
    WorkoutPlanDto mapToWorkoutPlanDto(WorkoutPlan workoutPlan);
    WorkoutPlan mapToWorkoutPlan(WorkoutPlanDto workoutPlanDto);
}
