package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.repositories.WorkoutPlanRepository;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseService exerciseService;
    private final UserService userService;

    public WorkoutPlanServiceImpl(WorkoutPlanRepository workoutPlanRepository, ExerciseService exerciseService, UserService userService) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @Override
    public void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto) {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setName(workoutPlanDto.getName());
        workoutPlan.setExercises(workoutPlanDto.getExercisesIds().stream()
                .map(exerciseService::findExerciseById)
                .toList());
        workoutPlan.setSets(workoutPlanDto.getSets());
        workoutPlan.setReps(workoutPlanDto.getReps());
        workoutPlan.setUser(userService.findUserById(workoutPlanDto.getUserId()));

        workoutPlanRepository.save(workoutPlan);
    }

    @Override
    public void saveWorkoutPlan(WorkoutPlanDto workoutPlanDto, Long id) {
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setId(id);
        workoutPlan.setName(workoutPlanDto.getName());
        workoutPlan.setExercises(workoutPlanDto.getExercisesIds().stream()
                .map(exerciseService::findExerciseById)
                .toList());
        workoutPlan.setSets(workoutPlanDto.getSets());
        workoutPlan.setReps(workoutPlanDto.getReps());
        workoutPlan.setUser(userService.findUserById(workoutPlanDto.getUserId()));

        workoutPlanRepository.save(workoutPlan);
    }

    @Override
    public void deleteWorkoutPlanById(Long id) {
        workoutPlanRepository.deleteById(id);
    }

    @Override
    public List<WorkoutPlan> findByExercisesContaining(Exercise exercise) {
        return workoutPlanRepository.findByExercisesContaining(exercise);
    }

    @Override
    public WorkoutPlan findWorkoutPlanById(Long id) {
        Optional<WorkoutPlan> workoutPlanOptional = workoutPlanRepository.findById(id);

        return workoutPlanOptional.orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    @Override
    public List<WorkoutPlanDto> findAllWorkoutPlansOfUser(User user) {
        List<WorkoutPlan> workoutPlans = workoutPlanRepository.findByUser(
                userService.findUserByName(user.getUsername()));

        return workoutPlans.stream()
                .map(this::mapToWorkoutPlanDto)
                .toList();
    }

    @Override
    public WorkoutPlanDto mapToWorkoutPlanDto(WorkoutPlan workoutPlan) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();
        workoutPlanDto.setId(workoutPlan.getId());
        workoutPlanDto.setName(workoutPlan.getName());
        workoutPlanDto.setExercisesIds(workoutPlan.getExercises().stream()
                .map(Exercise::getId)
                .toList());
        workoutPlanDto.setSets(workoutPlan.getSets());
        workoutPlanDto.setReps(workoutPlan.getReps());
        workoutPlanDto.setUserId(workoutPlan.getUser().getId());

        return workoutPlanDto;
    }
}