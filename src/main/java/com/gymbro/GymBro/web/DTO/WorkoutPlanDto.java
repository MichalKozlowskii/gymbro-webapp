package com.gymbro.GymBro.web.DTO;

import java.util.List;
import java.util.Set;

public class WorkoutPlanDto {
    private Long id;
    private String name;
    private Set<Long> exercisesIds;
    private List<Integer> sets;
    private List<Integer> reps;
    private Long userId;

    public WorkoutPlanDto(Long id, String name, Set<Long> exercisesIds, List<Integer> sets, List<Integer> reps, Long userId) {
        this.id = id;
        this.name = name;
        this.exercisesIds = exercisesIds;
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

    public Set<Long> getExercisesIds() {
        return exercisesIds;
    }

    public void setExercisesIds(Set<Long> exercisesIds) {
        this.exercisesIds = exercisesIds;
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
                ", exercisesIds=" + exercisesIds +
                ", sets=" + sets +
                ", reps=" + reps +
                ", userId=" + userId +
                '}';
    }
}