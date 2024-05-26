package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ExerciseService exerciseService;
    private final SetService setService;
    private final WorkoutService workoutService;
    private final WorkoutPlanService workoutPlanService;

    public ProgressServiceImpl(ExerciseService exerciseService, SetService setService,
                               WorkoutService workoutService, WorkoutPlanService workoutPlanService) {
        this.exerciseService = exerciseService;
        this.setService = setService;
        this.workoutService = workoutService;
        this.workoutPlanService = workoutPlanService;
    }

    @Override
    public Pair<WorkoutDto, WorkoutDto> findTwoLastWorkoutsOfWorkoutPlanById(Long id) {
        List<WorkoutDto> workouts = workoutService.findByWorkoutPlan(workoutPlanService.findWorkoutPlanById(id)).stream()
                .sorted(Comparator.comparing(Workout::getDateTime).reversed())
                .map(workoutService::mapToWorkoutDto)
                .toList();

        return new Pair<WorkoutDto, WorkoutDto>(workouts.get(0), workouts.get(1));
    }
}
