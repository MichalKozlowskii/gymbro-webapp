package com.gymbro.GymBro.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "excersises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    String description;

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Exercise() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise excersise = (Exercise) o;

        if (!Objects.equals(id, excersise.id)) return false;
        if (!Objects.equals(name, excersise.name)) return false;
        return Objects.equals(description, excersise.description);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
