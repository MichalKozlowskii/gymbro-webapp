package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.repositories.WorkoutPlanRepository;
import com.gymbro.GymBro.web.DTO.WorkoutPlanDto;
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
        workoutPlan.setName(workoutPlan.getName());
        workoutPlan.setExercises(workoutPlanDto.getExercises().stream()
                .map(exerciseService::mapToExercise)
                .collect(Collectors.toSet()));
        workoutPlan.setSets(workoutPlanDto.getSets());
        workoutPlan.setReps(workoutPlanDto.getReps());
        workoutPlan.setUser(userService.findUserById(workoutPlanDto.getUserId()));

        workoutPlanRepository.save(workoutPlan);
    }

    @Override
    public WorkoutPlan findWorkoutPlanById(Long id) {
        Optional<WorkoutPlan> workoutPlanOptional = workoutPlanRepository.findById(id);

        return workoutPlanOptional.orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    @Override
    public List<WorkoutPlanDto> findAllWorkoutPlansOfUser(UserEntity user) {
        List<WorkoutPlan> workoutPlans = workoutPlanRepository.findByUser(user);

        return workoutPlans.stream()
                .map(this::mapToWorkoutPlanDto)
                .toList();
    }

    @Override
    public WorkoutPlanDto mapToWorkoutPlanDto(WorkoutPlan workoutPlan) {
        WorkoutPlanDto workoutPlanDto = new WorkoutPlanDto();
        workoutPlanDto.setId(workoutPlan.getId());
        workoutPlanDto.setName(workoutPlan.getName());
        workoutPlanDto.setExercises(workoutPlan.getExercises().stream()
                .map(exerciseService::mapToExerciseDto)
                .collect(Collectors.toSet()));
        workoutPlanDto.setSets(workoutPlan.getSets());
        workoutPlanDto.setReps(workoutPlan.getReps());
        workoutPlanDto.setUserId(workoutPlan.getUser().getId());

        return workoutPlanDto;
    }
}
