package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.repositories.ExerciseRepository;
import com.gymbro.GymBro.web.DTO.ExerciseDto;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
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
    public void deleteExerciseById(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateExerciseById(Long id, ExerciseDto exerciseDto) {
        exerciseRepository.updateExerciseById(exerciseDto.getName(), exerciseDto.getDescription(),
                exerciseDto.getId());
    }

    @Override
    public String getExerciseNameById(Long id) {
        return findExerciseById(id).getName();
    }

    @Override
    public String getExerciseDescriptionById(Long id) {
        return findExerciseById(id).getDescription();
    }

    @Override
    public Exercise findExerciseById(Long id) {
        Optional<Exercise> exerciseOptional = exerciseRepository.findById(id);

        return exerciseOptional.orElseThrow(() -> new NoSuchElementException("Exercise not found with id: " + id));
    }

    @Override
    public List<ExerciseDto> findAllExercisesOfUser(User user) {
        List<Exercise> exercises = exerciseRepository.findByUser(
                userService.findUserByName(user.getUsername()));

        return exercises.stream()
                .map(this::mapToExerciseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDto mapToExerciseDto(Exercise exercise) {
        ExerciseDto exerciseDto = new ExerciseDto();
        exerciseDto.setId(exercise.getId());
        exerciseDto.setName(exercise.getName());
        exerciseDto.setDescription(exercise.getDescription());
        exerciseDto.setUserId(exercise.getUser().getId());

        return exerciseDto;
    }

    @Override
    public Exercise mapToExercise(ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setDescription(exercise.getDescription());
        exercise.setUser(userService.findUserById(exerciseDto.getUserId()));
        exercise.setId(exerciseDto.getId());

        return exercise;
    }
}
