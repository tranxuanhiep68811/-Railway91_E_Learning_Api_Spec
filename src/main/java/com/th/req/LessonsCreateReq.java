package com.th.req;

import lombok.Data;

@Data
public class LessonsCreateReq {

    private String lessonsName;

    private String description;

    private Integer duration;


}
