package com.th.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name", unique = true, nullable = false, columnDefinition = "text")
    private String coursesName;

    @Column(name = "Description", unique = true, nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @Column(name = "Number Of Lessons", nullable = false)
    private Integer numberOfLessons;

    @Column(name = "Instructor", unique = true, nullable = false, columnDefinition = "text")
    private String instructor;

    public Courses(Integer id, String coursesName, String description, Integer duration, Integer numberOfLessons, String instructor) {
        this.id = id;
        this.coursesName = coursesName;
        this.description = description;
        this.duration = duration;
        this.numberOfLessons = numberOfLessons;
        this.instructor = instructor;
    }

    public Courses() {
        // Default constructor
    }

}
