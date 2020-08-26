package com.tms.api.users.config.security.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private int status;
    private String message;
    @Setter(AccessLevel.NONE)
    private Date datetime;
    private String token;


    public TokenResponse(String token) {
        this.status = HttpStatus.OK.value();
        this.message = "Token generated successfully";
        this.datetime = new Date();
        this.token = token;
    }
}
