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
import java.time.LocalDateTime;
import java.util.*;

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

        if (workouts.size() < 2) {
            return null;
        }

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

    @Override
    public List<Pair<LocalDateTime,Double>> getWorkoutsScore(List<WorkoutDto> workouts) {
        workouts = workouts.stream().sorted(Comparator.comparing(WorkoutDto::getDateTime)).toList();
        List<Pair<LocalDateTime, Double>> result = new ArrayList<>();

        for (WorkoutDto workoutDto : workouts) {
            double score = 0;
            int setsAmount = 0;

            for (SetDto setDto : workoutDto.getSets()) {
                score += setDto.getWeight() * setDto.getReps();
                setsAmount++;
            }

            result.add(new Pair<>(workoutDto.getDateTime(), score / 10));
        }

        return result;
    }

    @Override
    public Map<ExerciseDto, List<Pair<LocalDateTime, Double>>> getExercisesWeightPlotData(List<WorkoutDto> workouts) {
        workouts = workouts.stream().sorted(Comparator.comparing(WorkoutDto::getDateTime)).toList();
        Map<ExerciseDto, List<Pair<LocalDateTime, Double>>> result = new HashMap<>();

        for (WorkoutDto workoutDto : workouts) {
            for (ExerciseDto exerciseDto : workoutDto.getWorkoutPlanDto().getExercises()) {
                List<SetDto> setsOfExercise = setService.findSetsOfExerciseInWorkout(workoutDto, exerciseDto);

                double combinedWeight = 0;
                int setCounter = 0;
                for (SetDto setDto : setsOfExercise) {
                    combinedWeight += setDto.getWeight();
                    setCounter++;
                }

                if (result.get(exerciseDto) == null) {
                    List<Pair<LocalDateTime, Double>> pairList = new ArrayList<>();
                    pairList.add(new Pair<>(workoutDto.getDateTime(), combinedWeight / setCounter));
                    result.put(exerciseDto, pairList);
                }
                else {
                    List<Pair<LocalDateTime, Double>> pairsList = result.get(exerciseDto);
                    pairsList.add(new Pair<>(workoutDto.getDateTime(), combinedWeight / setCounter));
                    result.put(exerciseDto, pairsList);
                }
            }
        }

        return result;
    }
}
