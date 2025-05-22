package com.th.req;

import lombok.Data;

@Data
public class CoursesUpdateReq {

    private String coursesName;

    private String description;

    private Integer duration;

    private Integer numberOfLessons;

    private String instructor;
}
