//package com.tms.api.users.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tms.api.users.data.dto.UserDto;
//import com.tms.api.users.config.security.model.LoginRequestModel;
//import com.tms.api.users.service.user.UserService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//
//@Slf4j
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private UserService userService;
//    private Environment environment;
//
//
//    public AuthenticationFilter(UserService usersService,
//                                Environment environment,
//                                AuthenticationManager authenticationManager) {
//        this.userService = usersService;
//        this.environment = environment;
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest req,
//                                                HttpServletResponse res) throws AuthenticationException {
//        try {
//
//            LoginRequestModel creds = new ObjectMapper()
//                    .readValue(req.getInputStream(), LoginRequestModel.class);
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getEmail(),
//                            creds.getPassword(),
//                            new ArrayList<>())
//            );
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) {
//        String userName = ((User) auth.getPrincipal()).getUsername();
//        UserDto userDto = userService.getUserDetailsByEmail(userName);
//
//
//        //TODO: use TokenProvider class here
//
//        String token = Jwts.builder()
//                .setSubject(userDto.getUserId())
//                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("jwt.expiration-time"))))
//                .signWith(SignatureAlgorithm.HS512, environment.getProperty("jwt.secret"))
//                .compact();
//
//        res.addHeader("token", token);
//        res.addHeader("userId", userDto.getUserId());
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        throw new BadCredentialsException("Bad credentials");
//    }
//
//
//}
