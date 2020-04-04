package com.tms.api.users.dto;

import com.tms.api.users.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


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
    private Date createdAt;
    private Date updatedAt;
    private Collection<Role> roles;
}
