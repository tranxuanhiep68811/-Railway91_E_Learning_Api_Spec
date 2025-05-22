package com.th.DTO;

import lombok.Data;

@Data
public class CoursesDTO {

    private Integer id;

    private String coursesName;

    private String description;

    private Integer duration;

    private Integer numberOfLessons;

    private String instructor;

    public CoursesDTO(Integer id, String coursesName, String description, Integer duration, Integer numberOfLessons, String instructor) {
        this.id = id;
        this.coursesName = coursesName;
        this.description = description;
        this.duration = duration;
        this.numberOfLessons = numberOfLessons;
        this.instructor = instructor;
    }

    public CoursesDTO() {
        // Default constructor
    }
}
