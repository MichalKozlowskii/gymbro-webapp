package com.gymbro.GymBro.web.DTO;

import java.util.List;
import java.util.Set;

public class WorkoutPlanDto {
    private Long id;
    private String name;
    private Set<ExerciseDto> exercises;
    private List<Integer> sets;
    private List<Integer> reps;
    private Long userId;

    public WorkoutPlanDto(Long id, String name, Set<ExerciseDto> exercises, List<Integer> sets, List<Integer> reps, Long userId) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.sets = sets;
        this.reps = reps;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(Set<ExerciseDto> exercises) {
        this.exercises = exercises;
    }

    public List<Integer> getSets() {
        return sets;
    }

    public void setSets(List<Integer> sets) {
        this.sets = sets;
    }

    public List<Integer> getReps() {
        return reps;
    }

    public void setReps(List<Integer> reps) {
        this.reps = reps;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
