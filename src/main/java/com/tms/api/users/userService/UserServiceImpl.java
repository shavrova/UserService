package com.tms.api.users.userService;

import com.tms.api.users.dto.UserDto;
import com.tms.api.users.entity.User;
import com.tms.api.users.mapper.UserMapper;
import com.tms.api.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        User user = userMapper.dtoToEntity(userDto);//map dto to entity
        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Can't find user with email " + email);
        return userMapper.entityToDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Can't find user with email " + email);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getEncryptedPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());

    }
}
