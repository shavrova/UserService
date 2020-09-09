package com.tms.api.users.config.security;

import com.tms.api.users.config.security.oauth2.CustomOAuth2UserService;
import com.tms.api.users.config.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.tms.api.users.config.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.tms.api.users.config.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.tms.api.users.data.model.user.enums.RoleEnum;
import com.tms.api.users.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @AllArgsConstructor
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final LoginAuthenticationEntryPoint restAuthenticationEntryPoint;
        private final RestAuthenticationProvider restAuthenticationProvider;
        private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
        private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private final RestAccessDeniedHandler restAccessDeniedHandler;
        private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
        private final CustomOAuth2UserService customOAuth2UserService;
        private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
        private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().disable();
            http.formLogin().disable();
            http.csrf().disable();
            http
                    .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    //.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    .authenticationProvider(restAuthenticationProvider)
                    .authorizeRequests()
                    //.antMatchers("/**").hasIpAddress("192.168.1.50").anyRequest().permitAll()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/register").permitAll()
                    .antMatchers("/auth/**", "/oauth2/**")
                    .permitAll()
                    .and().oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize").authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)

                .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);

        }


        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setMaxAge(3600L);
            configuration.setAllowedOrigins(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/api/**", configuration);
            return source;
        }


        @Bean
        public RestLoginAuthenticationFilter authenticationFilter() throws Exception {
            RestLoginAuthenticationFilter authenticationFilter = new RestLoginAuthenticationFilter();
            authenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
            authenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
            authenticationFilter.setAuthenticationManager(authenticationManagerBean());
            return authenticationFilter;
        }
    }
}
