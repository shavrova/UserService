package com.tms.api.users.dataloader;

import com.tms.api.users.entity.Role;
import com.tms.api.users.entity.User;
import com.tms.api.users.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void run(ApplicationArguments args) {

        User.builder()
                .firstName("Karl the Admin")
                .lastName("Admin the Karl")
                .email("admin@gmail.com")
                .encryptedPassword(bCryptPasswordEncoder.encode("12345678"))
                .createdAt(new Date())
                .updatedAt(new Date())
                .roles(Collections.singletonList(new Role("ROLE_ADMIN")))
                .build();

    }
}