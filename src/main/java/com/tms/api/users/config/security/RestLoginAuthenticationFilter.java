package com.tms.api.users.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.api.users.config.security.model.LoginRequestModel;
import com.tms.api.users.util.Constant;
import com.tms.api.users.util.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create authentication object
 */
@Slf4j
public class RestLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public RestLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(Constant.PATH_LOGIN, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream requestInputStream = request.getInputStream()) {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(requestInputStream, LoginRequestModel.class);
            authRequest = new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword());
        } catch (IOException ex) {
            throw new BadRequestException("Can't deserialize request.");
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
