package com.gymbro.GymBro.web.DTO;

import java.time.LocalDateTime;

public class SetDto {
    private Long id;
    private Long workoutId;
    private ExerciseDto exerciseDto;
    private int reps = 0;
    private double weight = 0.0;
    private LocalDateTime dateTime;

    public SetDto(Long id, Long workoutId, ExerciseDto exerciseDto, int reps, double weight, LocalDateTime dateTime) {
        this.id = id;
        this.workoutId = workoutId;
        this.exerciseDto = exerciseDto;
        this.reps = reps;
        this.weight = weight;
        this.dateTime = dateTime;
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

    public ExerciseDto getExerciseDto() {
        return exerciseDto;
    }

    public void setExerciseDto(ExerciseDto exerciseDto) {
        this.exerciseDto = exerciseDto;
    }
}
