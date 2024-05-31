package com.gymbro.GymBro.services;

import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ProgressService {
    public Pair<WorkoutDto, WorkoutDto> findTwoLastWorkoutsOfWorkoutPlanById(Long id);
    public Map<ExerciseDto, String> getTwoWorkoutsComparison(WorkoutDto workout, WorkoutDto workoutToCompareTo);
    public WorkoutDto getLastWorkoutOfWorkoutPlanById(Long id);
    public List<Pair<LocalDateTime,Double>> getWorkoutsScore(List<WorkoutDto> workouts);
}