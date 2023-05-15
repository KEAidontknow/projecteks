package com.example.projecteks.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // we must use deprecated encoder to support their encoding
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
        ;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/"))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(("/css/**")).permitAll()//TODO:move all css into a css folder
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/createUser").permitAll()
                        .requestMatchers("users/createUser").permitAll()
                        .requestMatchers("/showUser").permitAll()
                        .requestMatchers("/images/2.png").permitAll()
                        .requestMatchers("/Topnav.css").permitAll()
                        .requestMatchers("/Stylesheet.css").permitAll()
                        .requestMatchers("/showProjects").permitAll()//azure didn't work without this??
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin()//TODO: create a custom login site instead of using default form
                .defaultSuccessUrl("/showProject", true)
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/logOutSuccess")
                .and()
                .build();
    }

}
