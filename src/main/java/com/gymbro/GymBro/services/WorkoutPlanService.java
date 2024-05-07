package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;

import java.util.List;

public interface WorkoutPlanService {
    void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto);
    WorkoutPlan findWorkoutPlanById(Long id);
    List<WorkoutPlanDto> findAllWorkoutPlansOfUser(UserEntity user);
    WorkoutPlanDto mapToWorkoutPlanDto(WorkoutPlan workoutPlan);

}
