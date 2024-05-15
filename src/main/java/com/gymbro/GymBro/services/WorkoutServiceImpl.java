package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.repositories.WorkoutRepository;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final SetService setService;
    private final WorkoutPlanService workoutPlanService;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserService userService,
                              SetService setService, WorkoutPlanService workoutPlanService) {
        this.workoutRepository = workoutRepository;
        this.userService = userService;
        this.setService = setService;
        this.workoutPlanService = workoutPlanService;
    }

    @Override
    public void saveWorkout(WorkoutDto workoutDto) {
        Workout workout = new Workout();
        workout.setUser(userService.findUserById(workoutDto.getUserId()));
        workout.setWorkoutPlan(workoutPlanService.findWorkoutPlanById(workoutDto.getWorkoutPlanId()));
        workout.setDateTime(workoutDto.getDateTime());

        workoutRepository.save(workout);
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
        workoutDto.setWorkoutPlanId(workout.getWorkoutPlan().getId());
        workoutDto.setSets(workout.getSets().stream()
                .map(setService::mapToSetDto)
                .toList());
        workoutDto.setDateTime(workout.getDateTime());

        return workoutDto;
    }
}
