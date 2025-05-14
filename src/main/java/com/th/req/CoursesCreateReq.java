package com.th.req;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CoursesCreateReq {

    private String coursesName;

    private String description;

    private Integer duration;

    private Integer numberOfLessons;

    private String instructor;
}
