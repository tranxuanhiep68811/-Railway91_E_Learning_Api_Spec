package com.th.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class ConfigSecurity {

     @Bean
     public PasswordEncoder passwordEncoder() {
//         return NoOpPasswordEncoder.getInstance();
         return new BCryptPasswordEncoder();
     }

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf().disable();
//            .authorizeHttpRequests()
//                .requestMatchers("/login").permitAll()
//                .requestMatchers("/h2-console/**").permitAll()
//                .and()
//            .headers().frameOptions().sameOrigin()
//            .and()
//        http
//            .authorizeRequests()
//                .requestMatchers("/products/**").hasAnyRole("USER");
//                .requestMatchers("/accounts/**").hasAnyRole("ADMIN");
//                .requestMatchers("/categories/**").permitAll();
//                .requestMatchers("/product").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//            .logout()
//                .permitAll();
        http
             .authorizeRequests()
                 .requestMatchers("/accounts/**").permitAll();
//        http
//             .authorizeRequests()
//                .requestMatchers("/categories/**").permitAll();
        return http.httpBasic().and().build();
    }

}
