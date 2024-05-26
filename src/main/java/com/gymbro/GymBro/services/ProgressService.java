package com.gymbro.GymBro.services;

import com.gymbro.GymBro.models.Workout;
import com.gymbro.GymBro.models.WorkoutPlan;
import com.gymbro.GymBro.records.Pair;

public interface ProgressService {
    Pair<Workout, Workout> findTwoLastWorkoutsOfWorkoutPlan(WorkoutPlan workoutPlan);
}
