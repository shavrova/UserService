package com.tms.api.users.controller;


import com.tms.api.users.dto.UserDto;
import com.tms.api.users.mapper.UserMapper;
import com.tms.api.users.model.user.UserRequestModel;
import com.tms.api.users.model.user.UserResponseModel;
import com.tms.api.users.userService.UserService;
import com.tms.api.users.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(Constant.PATH_USERS)
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserMapper mapper;



    @GetMapping("/status")
    public String status(){
        return "Working!";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel createdUser) {
        UserDto dto = service.createUser(mapper.requestToDto(createdUser));//map request to dto
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.createResponseFromDto(dto));
    }
}
