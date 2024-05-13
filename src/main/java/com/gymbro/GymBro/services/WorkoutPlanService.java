package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface WorkoutPlanService {
    void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto);
    void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto, Long id);
    WorkoutPlan findWorkoutPlanById(Long id);
    List<WorkoutPlanDto> findAllWorkoutPlansOfUser(User user);
    WorkoutPlanDto mapToWorkoutPlanDto(WorkoutPlan workoutPlan);
}
