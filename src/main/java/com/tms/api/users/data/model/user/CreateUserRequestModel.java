package com.tms.api.users.data.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CreateUserRequestModel {

    @Size(min = 2, max = 50, message = "First name size must been between 2 and 50 characters")
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name size must been between 2 and 50 characters")
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 2, max = 50, message = "Password size must been between 2 and 50 characters")
    private String password;
}
