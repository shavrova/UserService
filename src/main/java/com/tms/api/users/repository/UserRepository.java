package com.tms.api.users.repository;

import com.tms.api.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);//TODO: make return type Optional
    Optional<User> findByUserId(String userId);

}
