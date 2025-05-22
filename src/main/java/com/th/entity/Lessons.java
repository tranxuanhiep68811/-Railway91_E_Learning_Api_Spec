package com.th.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lessons")
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Lesson Name", unique = true, nullable = false, columnDefinition = "text")
    private String lessonsName;

    @Column(name = "Description", unique = true, nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "courses_id")
    private Courses courses;

    public Lessons(Integer id, String lessonsName, String description, Integer duration) {
        this.id = id;
        this.lessonsName = lessonsName;
        this.description = description;
        this.duration = duration;
    }


    public Lessons() {
        // Default constructor
    }

    @Override
    public String toString() {
        return "Lessons{" +
                "id=" + id +
                ", lessonsName='" + lessonsName + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
