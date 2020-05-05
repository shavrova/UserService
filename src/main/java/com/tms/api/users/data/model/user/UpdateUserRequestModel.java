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

    @Size(min = 2, max = 50, message = "First name size must been between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name size must been between 2 and 50 characters")
    private String lastName;

    @Email
    private String email;

}

