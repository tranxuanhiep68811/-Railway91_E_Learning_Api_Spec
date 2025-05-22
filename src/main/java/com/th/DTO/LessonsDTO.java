package com.th.DTO;

import lombok.Data;

@Data
public class LessonsDTO {

    private Integer id;

    private String lessonsName;

    private String description;

    private Integer duration;

    public LessonsDTO(Integer id, String lessonsName, String description, Integer duration) {
        this.id = id;
        this.lessonsName = lessonsName;
        this.description = description;
        this.duration = duration;
    }
    public LessonsDTO() {
        // Default constructor
    }
}
