package com.tms.api.users.data.dto;

import com.tms.api.users.data.model.test.TestResponseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
public class UserDto implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
    private Integer role;
    private Integer status;
    private Boolean deleted;
    private Date createdAt;
    private Date updatedAt;
    private List<TestResponseModel> userTests;
}
