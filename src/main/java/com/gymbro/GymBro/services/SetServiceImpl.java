package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.stereotype.Service;

@Service
public class SetServiceImpl implements SetService {
    @Override
    public SetDto mapToSetDto(Set set) {
        SetDto setDto = new SetDto();
        setDto.setId(set.getId());
        setDto.setWorkoutId(set.getWorkout().getId());
        setDto.setExerciseId(set.getExercise().getId());
        setDto.setReps(set.getReps());
        setDto.setWeight(set.getWeight());
        setDto.setDateTime(set.getDateTime());

        return setDto;
    }
}
