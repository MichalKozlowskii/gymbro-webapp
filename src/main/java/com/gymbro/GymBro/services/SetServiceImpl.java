package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.repositories.SetRepository;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SetServiceImpl implements SetService {
    private final SetRepository setRepository;
    private final ExerciseService exerciseService;

    public SetServiceImpl(SetRepository setRepository, ExerciseService exerciseService) {
        this.setRepository = setRepository;
        this.exerciseService = exerciseService;
    }

    @Override
    public List<SetDto> findSetsOfExerciseInWorkout(WorkoutDto workoutDto, Exercise exercise) {
        List<SetDto> exerciseSets = new ArrayList<>();

        for (SetDto set : workoutDto.getSets()) {
            if (Objects.equals(set.getExerciseDto().getId(), exercise.getId())) {
                exerciseSets.add(set);
            }
        }

        return exerciseSets;
    }

    @Override
    public List<SetDto> findSetsOfExerciseById(Long id) {
        return setRepository.findByExerciseId(id).stream()
                .map(this::mapToSetDto)
                .toList();
    }

    @Override
    public List<Set> findByWorkoutId(Long id) {
        return setRepository.findByWorkoutId(id);
    }

    @Override
    public SetDto mapToSetDto(Set set) {
        SetDto setDto = new SetDto();
        setDto.setId(set.getId());
        setDto.setWorkoutId(set.getWorkout().getId());
        setDto.setExerciseDto(exerciseService.mapToExerciseDto(set.getExercise()));
        setDto.setReps(set.getReps());
        setDto.setWeight(set.getWeight());
        setDto.setDateTime(set.getDateTime());

        return setDto;
    }
}
