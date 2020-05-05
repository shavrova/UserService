package com.tms.api.users.data.model.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class TestResponseModel {

    private String testId;

    private String testName;

    private String testDescription;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date createdAt;

    private String userId;


}
