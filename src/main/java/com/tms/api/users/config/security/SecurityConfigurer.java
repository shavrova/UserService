package com.tms.api.users.config.security;

import com.tms.api.users.data.model.user.enums.RoleEnum;
import com.tms.api.users.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Configuration
    @AllArgsConstructor
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final RestAuthenticationProvider restAuthenticationProvider;
        private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
        private final RestAuthenticationFailureHandler restAuthenticationFailureHandler;
        private final RestAccessDeniedHandler restAccessDeniedHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.headers().frameOptions().disable();
            http
                    .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(restAuthenticationProvider)
                    .authorizeRequests()
                    .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                    .antMatchers("/api/users/**").hasRole(RoleEnum.USER.getName())
                    .anyRequest().permitAll()
                    //.and().exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                    .and().formLogin().disable()
                    .csrf().disable()
                    .cors();
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
