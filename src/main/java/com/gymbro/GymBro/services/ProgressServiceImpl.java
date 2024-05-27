package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Workout> workouts = workoutService.findByWorkoutPlan(workoutPlanService.findWorkoutPlanById(id)).stream()
                .sorted(Comparator.comparing(Workout::getDateTime).reversed())
                .toList();

        WorkoutDto workout1 = workoutService.mapToWorkoutDto(workouts.get(0));
        WorkoutDto workout2 = workoutService.mapToWorkoutDto(workouts.get(1));

        return new Pair<WorkoutDto, WorkoutDto>(workout1, workout2);
    }

    @Override
    public Map<ExerciseDto, String> getTwoWorkoutsComparison(WorkoutDto workout, WorkoutDto workoutToCompareTo) {
        Map<ExerciseDto, String> result = new HashMap<>();

        for (ExerciseDto exerciseDto : workout.getWorkoutPlanDto().getExercises()) {
            List<SetDto> setsOfWorkout1 = setService.findSetsOfExerciseInWorkout(workout, exerciseDto);
            List<SetDto> setsOfWorkout2 = setService.findSetsOfExerciseInWorkout(workoutToCompareTo, exerciseDto);

            int repsOfWorkout1 = 0;
            double weightOfWorkout1 = 0;
            for (SetDto setDto : setsOfWorkout1) {
                repsOfWorkout1 += setDto.getReps();
                weightOfWorkout1 += setDto.getWeight();
            }

            int repsOfWorkout2 = 0;
            double weightOfWorkout2 = 0;
            for (SetDto setDto : setsOfWorkout2) {
                repsOfWorkout2 += setDto.getReps();
                weightOfWorkout2 += setDto.getWeight();
            }

            int repDiff = repsOfWorkout1 - repsOfWorkout2;
            double weightAvgDiff = weightOfWorkout1 / setsOfWorkout1.size() - weightOfWorkout2 / setsOfWorkout2.size();

            String repsDiffString = repDiff + " reps ";
            if (repDiff >= 0) {
                repsDiffString = "+" + repsDiffString;
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String weightAvgDiffString = decimalFormat.format(weightAvgDiff) + " avg. weight";
            if (weightAvgDiff >= 0) {
                weightAvgDiffString = "+" + weightAvgDiffString;
            }

            String finalString = repsDiffString + weightAvgDiffString;

            result.put(exerciseDto, finalString);
        }

        return result;
    }

    @Override
    public WorkoutDto getLastWorkoutOfWorkoutPlanById(Long id) {
        List<Workout> workouts = workoutService.findByWorkoutPlan(workoutPlanService.findWorkoutPlanById(id)).stream()
                .sorted(Comparator.comparing(Workout::getDateTime).reversed())
                .toList();

        return workoutService.mapToWorkoutDto(workouts.get(0));
    }
}
