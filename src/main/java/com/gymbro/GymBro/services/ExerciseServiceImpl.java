package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.repositories.ExerciseRepository;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final UserService userService;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, UserService userService) {
        this.exerciseRepository = exerciseRepository;
        this.userService = userService;
    }

    @Override
    public void saveExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setUser(userService.findUserById(exerciseDto.getUserId()));

        exerciseRepository.save(exercise);
    }

    @Override
    public Exercise findExerciseById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);

        return exerciseOptional.orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    @Override
    public List<ExerciseDto> findAllExercisesOfUser(UserEntity user) {
        List<Exercise> exercises = exerciseRepository.findByUser(user);

        if (exercises.isEmpty()) {
            return Collections.emptyList();
        }

        return exercises.stream()
                .map(this::mapToExerciseDto)
                .collect(Collectors.toList());
    }

    private ExerciseDto mapToExerciseDto(Exercise exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setName(exerciseDto.getName());
        exerciseDto.setDescription(exerciseDto.getDescription());
        exerciseDto.setUserId(exercise.getUser().getId());

        return exerciseDto;
    }
}
