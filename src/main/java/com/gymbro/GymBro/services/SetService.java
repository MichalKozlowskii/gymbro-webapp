package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.web.DTO.SetDto;
import com.gymbro.GymBro.web.DTO.WorkoutDto;

import java.util.List;

public interface SetService {
    List<SetDto> findSetsOfExerciseInWorkout(WorkoutDto workoutDto, Exercise exercise);
    List<Set> findByWorkoutId(Long id);
    SetDto mapToSetDto(Set set);
}
