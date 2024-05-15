package com.gymbro.GymBro.repositories;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findbyUser(UserEntity user);
}
