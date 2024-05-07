package com.gymbro.GymBro.repositories;

import com.gymbro.GymBro.models.UserEntity;
import com.gymbro.GymBro.models.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUser(UserEntity userEntity);
}
