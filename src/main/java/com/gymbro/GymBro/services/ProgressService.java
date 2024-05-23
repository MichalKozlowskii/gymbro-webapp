package com.gymbro.GymBro.services;

import com.gymbro.GymBro.web.DTO.ExerciseDto;
import com.gymbro.GymBro.web.DTO.SetDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Map;

public interface ProgressService {
    Map<ExerciseDto, List<SetDto>> findSetsOfUsersExercises(User user);
}
