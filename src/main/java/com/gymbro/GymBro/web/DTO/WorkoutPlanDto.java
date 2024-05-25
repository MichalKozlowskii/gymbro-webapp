package com.gymbro.GymBro.web.DTO;

import java.util.*;

public class WorkoutPlanDto {
    private Long id;
    private String name;
    private List<ExerciseDto> exercises = new ArrayList<>();
    private List<Integer> sets = new ArrayList<>();
    private List<Integer> reps = new ArrayList<>();
    private Long userId;

    public WorkoutPlanDto(Long id, String name, List<ExerciseDto> exercises, List<Integer> sets, List<Integer> reps, Long userId) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
        this.sets = sets;
        this.reps = reps;
        this.userId = userId;
    }

    public WorkoutPlanDto() {
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

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDto> exercises) {
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

    @Override
    public String toString() {
        return "WorkoutPlanDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exercises=" + exercises +
                ", sets=" + sets +
                ", reps=" + reps +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutPlanDto that = (WorkoutPlanDto) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(exercises, that.exercises)) return false;
        if (!Objects.equals(sets, that.sets)) return false;
        if (!Objects.equals(reps, that.reps)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (exercises != null ? exercises.hashCode() : 0);
        result = 31 * result + (sets != null ? sets.hashCode() : 0);
        result = 31 * result + (reps != null ? reps.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}