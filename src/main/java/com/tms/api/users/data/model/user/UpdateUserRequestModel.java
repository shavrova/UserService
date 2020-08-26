package com.tms.api.users.data.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateUserRequestModel {

    @NotNull
    private String userId;

    @Size(min = 2, max = 50, message = "{first-name.size}")
    private String firstName;

    @Size(min = 2, max = 50, message = "{last-name.size}")
    private String lastName;

    @Email
    private String email;

}

