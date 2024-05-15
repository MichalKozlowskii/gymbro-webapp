package com.gymbro.GymBro.repositories;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.Set;
import com.gymbro.GymBro.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetRepository extends JpaRepository<Set, Long> {
    List<Set> findByWorkout(Workout workout);
    List<Set> findByExercise(Exercise exercise);
}
