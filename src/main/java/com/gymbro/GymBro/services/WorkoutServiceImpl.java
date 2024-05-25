package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.repositories.SetRepository;
import com.gymbro.GymBro.repositories.WorkoutRepository;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;
    private final SetRepository setRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserService userService,
                              WorkoutPlanService workoutPlanService, ExerciseService exerciseService,
                              SetRepository setRepository) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
        this.setRepository = setRepository;
    }

    @Override
    public void saveWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setUser(userService.findUserById(workoutDto.getUserId()));
        workout.setWorkoutPlan(workoutPlanService.mapToWorkoutPlan(workoutDto.getWorkoutPlanDto()));
        workout.setDateTime(workoutDto.getDateTime());

        workoutRepository.save(workout);
    }

    @Override
    public void addSet(Workout workout, SetDto setDto) {
        Set set = new Set();
        set.setWorkout(findWorkoutById(setDto.getWorkoutId()));
        set.setExercise(exerciseService.findExerciseById(setDto.getExerciseId()));
        set.setReps(setDto.getReps());
        set.setWeight(setDto.getWeight());
        set.setDateTime(setDto.getDateTime());

        set = setRepository.save(set);

        List<Set> sets = workout.getSets();
        sets.add(set);
        workout.setSets(sets);

        workoutRepository.save(workout);
    }

    @Override
    public void deleteSet(Long workoutId, Long setId) {
        Workout workout = findWorkoutById(workoutId);

        List<Set> sets = workout.getSets();
        sets.removeIf(set -> Objects.equals(set.getId(), setId));

        workoutRepository.save(workout);
        setRepository.deleteById(setId);
    }

    @Override
    public void deleteWorkout(Long workoutId) {
        workoutRepository.deleteById(workoutId);
    }

    @Override
    public List<SetDto> findSetsOfExerciseInWorkout(WorkoutDto workoutDto, Long exerciseId) {
        List<SetDto> setsOfExercise = new ArrayList<>();

        for (SetDto setDto : workoutDto.getSets()) {
            if (setDto.getExerciseDto().getId().equals(exerciseId)) {
                setsOfExercise.add(setDto);
            }
        }

        return setsOfExercise;
    }

    @Override
    public List<Workout> findByWorkoutPlan(WorkoutPlan workoutPlan) {
        return workoutRepository.findByWorkoutPlan(workoutPlan);
    }

    @Override
    public Workout findWorkoutById(Long id) {
        Optional<Workout> workoutOptional = workoutRepository.findById(id);

        return workoutOptional.orElseThrow(() -> new NoSuchElementException("Workout not found with id: " + id));
    }

    @Override
    public List<WorkoutDto> findAllWorkoutsOfUser(User user) {
        List<Workout> workouts = workoutRepository.findByUser(userService.findUserByName(user.getUsername()));

        return workouts.stream()
                .map(this::mapToWorkoutDto)
                .toList();
    }

    @Override
    public WorkoutDto mapToWorkoutDto(Workout workout) {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setUserId(workout.getUser().getId());
        workoutDto.setWorkoutPlanDto(workoutPlanService.mapToWorkoutPlanDto(workout.getWorkoutPlan()));
        workoutDto.setSets(workout.getSets().stream()
                .map(this::mapToSetDto)
                .toList());
        workoutDto.setDateTime(workout.getDateTime());

        return workoutDto;
    }

    @Override
    public SetDto mapToSetDto(Set set) {
        SetDto setDto = new SetDto();
        setDto.setWorkoutDto(mapToWorkoutDto(set.getWorkout()));
        setDto.setExerciseDto(exerciseService.mapToExerciseDto(set.getExercise()));
        setDto.setReps(set.getReps());
        setDto.setWeight(set.getWeight());
        setDto.setDateTime(set.getDateTime());

        return setDto;
    }
}
