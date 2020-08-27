package com.tms.api.users.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.api.users.config.security.model.TokenResponse;
import com.tms.api.users.config.security.utils.JwtTool;
import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Return token to user after successful authentication
 */
@Slf4j
@Component
@AllArgsConstructor
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    private final JwtTool jwtTool;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        log.info("authentication.getPrincipal() object : " + authentication.getPrincipal());
        UserDto userDto = userService.getUserDetailsByEmail(((User) authentication.getPrincipal()).getUsername());
        String token = jwtTool.generateToken(userDto);
        TokenResponse tokenResponse = new TokenResponse(token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
