package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;

import java.util.Map;

public interface ProgressService {
    public Pair<WorkoutDto, WorkoutDto> findTwoLastWorkoutsOfWorkoutPlanById(Long id);
    public Map<ExerciseDto, String> getTwoWorkoutsComparison(WorkoutDto workout, WorkoutDto workoutToCompareTo);
}