package com.th.req;

import lombok.Data;

@Data
public class LessonsUpdateReq {

    private Integer id;

    private String lessonsName;

    private String description;

    private Integer duration;

}
