package com.gymbro.GymBro.repositories;

import com.gymbro.GymBro.models.Exercise;
import com.gymbro.GymBro.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByUser(UserEntity user);
    @Modifying
    @Query("update Exercise e set e.name = ?1, e.description = ?2 where e.id = ?3")
    void updateExerciseById(String name, String description, Long id);
}
