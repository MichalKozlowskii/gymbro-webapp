package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ProgressService {
    List<Workout> findTwoLastWorkoutsOfUser(User user);
}
