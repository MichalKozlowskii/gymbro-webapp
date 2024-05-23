package com.gymbro.GymBro.services;

import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressServiceImpl implements ProgressService {
    private final ExerciseService exerciseService;
    private final SetService setService;

    public ProgressServiceImpl(ExerciseService exerciseService, SetService setService) {
        this.exerciseService = exerciseService;
        this.setService = setService;
    }

    @Override
    public Map<ExerciseDto, List<SetDto>> findSetsOfUsersExercises(User user) {
        Map<ExerciseDto, List<SetDto>> exerciseSetMap = new HashMap<>();
        List<ExerciseDto> exercises = exerciseService.findAllExercisesOfUser(user);

        for (ExerciseDto exerciseDto : exercises) {
            List<SetDto> sets = setService.findSetsOfExerciseById(exerciseDto.getId());
            exerciseSetMap.put(exerciseDto, sets);
        }

        return exerciseSetMap;
    }
}
