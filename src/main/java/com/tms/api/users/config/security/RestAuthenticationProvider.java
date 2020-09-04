package com.tms.api.users.config.security;


import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.data.model.user.enums.RoleEnum;
import com.tms.api.users.data.model.user.enums.UserStatusEnum;
import com.tms.api.users.service.user.UserService;
import com.tms.api.users.util.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Validate user credentials
 */
@Slf4j
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RestAuthenticationProvider(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();//email from request
        String password = authentication.getCredentials().toString();//password from request (not encoded)

        if (email == null || email.trim().isEmpty()) {
            throw new UsernameNotFoundException("Email is required");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new UsernameNotFoundException("Password is required");
        }
        UserDto userDto = null;
        try {
            userDto = userService.getUserDetailsByEmail(email);
        } catch (ResourceNotFoundException ex) {
            log.error("email: " + email);
            throw new UsernameNotFoundException("Email is invalid");
        }

        if (password == null || !bCryptPasswordEncoder.matches(password, userDto.getEncryptedPassword())) {
            throw new BadCredentialsException("Password is invalid");
        }
        if (UserStatusEnum.fromCode(userDto.getStatus()) == UserStatusEnum.Disabled) {
            throw new LockedException("Account disabled");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(userDto.getRole()).getRoleName()));

        User authUser = new User(userDto.getEmail(), userDto.getEncryptedPassword(), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
