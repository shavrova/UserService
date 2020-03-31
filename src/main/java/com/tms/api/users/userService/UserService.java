package com.tms.api.users.userService;

import com.tms.api.users.dto.UserDto;
import com.tms.api.users.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    UserDto createUser(UserDto userDto);

    UserDto getUserDetailsByEmail(String email);


}
