package com.gymbro.GymBro.web.DTO;

import java.time.LocalDateTime;

public class SetDto {
    private Long id;
    private Long workoutId;
    private Long exerciseId;
    private int reps = 0;
    private double weight = 0.0;
    private LocalDateTime dateTime;

    public SetDto(Long id, Long workoutId, Long exerciseId, int reps, double weight, LocalDateTime localDateTime) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.reps = reps;
        this.weight = weight;
        this.dateTime = localDateTime;
    }

    public SetDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SetDto{" +
                "id=" + id +
                ", workoutId=" + workoutId +
                ", exerciseId=" + exerciseId +
                ", reps=" + reps +
                ", weight=" + weight +
                ", dateTime=" + dateTime +
                '}';
    }
}
