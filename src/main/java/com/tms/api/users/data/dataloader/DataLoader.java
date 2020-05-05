package com.tms.api.users.data.dataloader;

import com.tms.api.users.data.entity.Role;
import com.tms.api.users.data.entity.User;
import com.tms.api.users.data.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void run(ApplicationArguments args) {
        User user = User.builder()
                .userId(UUID.randomUUID().toString())
                .firstName("Karl the Admin")
                .lastName("Admin the Karl")
                .email("admin@gmail.com")
                .encryptedPassword(bCryptPasswordEncoder.encode("12345678"))
                .createdAt(new Date())
                .updatedAt(new Date())
                .roles(Collections.singletonList(new Role("ADMIN")))
                .build();
        try {
            repository.save(user);
        }catch (Exception ex){}
    }
}