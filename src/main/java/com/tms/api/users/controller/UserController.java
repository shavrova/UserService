package com.tms.api.users.controller;


import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.data.model.user.CreateUserRequestModel;
import com.tms.api.users.data.model.user.UpdateUserRequestModel;
import com.tms.api.users.data.model.user.UserResponseModel;
import com.tms.api.users.service.mapper.UserMapper;
import com.tms.api.users.service.user.UserService;
import com.tms.api.users.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserMapper mapper;


    @GetMapping(Constant.PATH_USER_DETAILS)
    public ResponseEntity<UserResponseModel> getUserDetails(@RequestHeader("user-id") String userId) {
        UserDto userDto = service.getUser(userId);
        return ResponseEntity.ok(mapper.createResponseFromDto(userDto));

    }

    @PostMapping(Constant.PATH_REGISTER)
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel createdUser) {
        UserDto dto = service.createUser(mapper.createRequestToDto(createdUser));//map request to dto
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.createResponseFromDto(dto));
    }

    //TODO
    @PutMapping(Constant.PATH_USER_DETAILS)
    public ResponseEntity<UserResponseModel> updateUserDetails(@Valid @RequestBody UpdateUserRequestModel createdUser) {
        throw new NotImplementedException("PUT /api/me is not implemented");
    }
}
