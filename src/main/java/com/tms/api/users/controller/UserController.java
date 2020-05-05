package com.tms.api.users.controller;


import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.service.mapper.UserMapper;
import com.tms.api.users.data.model.user.CreateUserRequestModel;
import com.tms.api.users.data.model.user.UpdateUserRequestModel;
import com.tms.api.users.data.model.user.UserResponseModel;
import com.tms.api.users.service.user.UserService;
import com.tms.api.users.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private Environment env;


    @GetMapping("/status")
    public String status() {
        return "Secret: " + env.getProperty("jwt.secret") + "\ntest.prop = " + env.getProperty("test.prop");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.createResponseFromDto(service.getUser(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel createdUser) {
        UserDto dto = service.createUser(mapper.createRequestToDto(createdUser));//map request to dto
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.createResponseFromDto(dto));
    }

    @GetMapping
    ResponseEntity<Page<UserResponseModel>> getAllUsers(Pageable page) {
        Page<UserDto> userDtos = service.findPage(page);
        return ResponseEntity.ok(userDtos.map(dto -> mapper.createResponseFromDto(dto)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UpdateUserRequestModel userRequestModel) {
        UserDto dto = service.updateUser(mapper.updateRequestToDto(userRequestModel));
        return ResponseEntity.ok(mapper.createResponseFromDto(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
