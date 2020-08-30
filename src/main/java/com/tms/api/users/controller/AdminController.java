package com.tms.api.users.controller;

import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.data.model.user.UpdateUserRequestModel;
import com.tms.api.users.data.model.user.UserResponseModel;
import com.tms.api.users.service.mapper.UserMapper;
import com.tms.api.users.service.user.UserService;
import com.tms.api.users.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(Constant.PATH_USERS)
public class AdminController {

    @Autowired
    UserService service;

    @Autowired
    UserMapper mapper;

    @DeleteMapping(Constant.PATH_VARIABLE_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    ResponseEntity<Page<UserResponseModel>> getAllUsers(Pageable page) {
        Page<UserDto> userDtos = service.findPage(page);
        return ResponseEntity.ok(userDtos.map(dto -> mapper.createResponseFromDto(dto)));
    }


    @GetMapping(Constant.PATH_VARIABLE_ID)
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(mapper.createResponseFromDto(service.getUser(id)));
    }

    @PutMapping
    public ResponseEntity<UserResponseModel> updateUser(@Valid @RequestBody UpdateUserRequestModel userRequestModel) {
        UserDto dto = service.updateUser(mapper.updateRequestToDto(userRequestModel));
        return ResponseEntity.ok(mapper.createResponseFromDto(dto));
    }
}
