package com.tms.api.users.config.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestModel {

    @JsonProperty(required = true)
    private String email;

    @JsonProperty(required = true)
    private String password;
}
