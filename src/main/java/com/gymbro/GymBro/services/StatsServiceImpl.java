package com.gymbro.GymBro.services;

import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatsServiceImpl implements StatsService {
    private final ExerciseService exerciseService;
    private final SetService setService;

    public StatsServiceImpl(ExerciseService exerciseService, SetService setService) {
        this.exerciseService = exerciseService;
        this.setService = setService;
    }

    @Override
    public Map<ExerciseDto, List<SetDto>> findBestSetsOfUsersExercises(User user) {
        Map<ExerciseDto, List<SetDto>> exerciseBestSetsMap = new HashMap<>();

        List<ExerciseDto> exercises = exerciseService.findAllExercisesOfUser(user);

        for (ExerciseDto exerciseDto : exercises) {
            List<SetDto> setDtos = setService.findSetsOfExerciseById(exerciseDto.getId());

            SetDto mostWeight = new SetDto();
            SetDto mostReps = new SetDto();

            for (SetDto setDto : setDtos) {
                if (setDto.getWeight() > mostWeight.getWeight()) {
                    mostWeight = setDto;
                }
                else if (setDto.getWeight() == mostWeight.getWeight() && setDto.getReps() > mostWeight.getReps()) {
                    mostWeight = setDto;
                }

                if (setDto.getReps() > mostReps.getReps()) {
                    mostReps = setDto;
                }
                else if (setDto.getReps() == mostReps.getReps() && setDto.getWeight() > mostReps.getWeight()) {
                    mostReps = setDto;
                }
            }

            List<SetDto> bestSets = Arrays.asList(mostWeight, mostReps);
            exerciseBestSetsMap.put(exerciseDto, bestSets);
        }

        return exerciseBestSetsMap;
    }
}
