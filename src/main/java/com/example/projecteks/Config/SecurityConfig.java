package com.example.projecteks.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

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
                /*
                csrf disabled for landing page
                 */
                .csrf(csrf -> csrf.ignoringRequestMatchers("/"))
                .authorizeHttpRequests( auth -> auth
                        /*
                        sites which does not require authentication
                         */
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/createUser").permitAll()
                        .requestMatchers("users/createUser").permitAll()
                        .requestMatchers("/showUser").permitAll()
                        .requestMatchers("/images/2.png").permitAll()
                        .requestMatchers("/topNav.css").permitAll()
                        .requestMatchers("/styleSheet.css").permitAll()
                        .requestMatchers("/showProjects").permitAll()
                        .anyRequest().authenticated()// any request must be authenticated
                )
                .headers(headers -> headers.frameOptions().sameOrigin())//allow newer browsers to prevent clickjacking attacks
                .formLogin()
                .loginPage("/login").permitAll()//overwrite of default spring security login form with custom login form
                .defaultSuccessUrl("/userSite", true) //overwrite of default landing page which is / to custom landing page
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //changed to allow Get logout instead of the default post logout only
                .permitAll()
                .and()
                .build();
    }

}
