package com.gymbro.GymBro.web.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutDto {
    private Long id;
    private Long userId;
    private Long workoutPlanId;
    private List<SetDto> sets;
    private LocalDateTime dateTime;

    public WorkoutDto(Long id, Long userId, Long workoutPlanId, List<SetDto> sets, LocalDateTime dateTime) {
        this.id = id;
        this.userId = userId;
        this.workoutPlanId = workoutPlanId;
        this.sets = sets;
        this.dateTime = dateTime;
    }

    public WorkoutDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorkoutPlanId() {
        return workoutPlanId;
    }

    public void setWorkoutPlanId(Long workoutPlanId) {
        this.workoutPlanId = workoutPlanId;
    }

    public List<SetDto> getSets() {
        return sets;
    }

    public void setSets(List<SetDto> sets) {
        this.sets = sets;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
