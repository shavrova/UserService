package com.tms.api.users.model.login;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestModel {
    private String email;
    private String password;
}
