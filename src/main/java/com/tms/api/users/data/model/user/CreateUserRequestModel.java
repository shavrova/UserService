package com.tms.api.users.data.model.user;

import com.tms.api.users.data.model.user.constrains.PasswordMatch;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@PasswordMatch.List({
        @PasswordMatch(field = "password", fieldMatch = "confirmPassword", message = "{password.confirm.error}"),
})
public class CreateUserRequestModel {

    @Size(min = 2, max = 50, message = "{first-name.size}")
    @NotNull(message = "{first-name.required}")
    private String firstName;

    @Size(min = 2, max = 50, message = "{last-name.size}")
    @NotNull(message = "{last-name.required}")
    private String lastName;

    @NotNull(message = "{email.empty}")
    @Email
    private String email;

    @NotNull(message = "{password.required}")
    @Size(min = 4, max = 20, message = "{password.length}")
    private String password;

    @NotEmpty(message = "{password.confirm}")
    private String confirmPassword;
}
