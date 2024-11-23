package com.tacocloud.security;

import com.tacocloud.data.UserRepository;
import com.tacocloud.domain.TacoUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            TacoUser user = userRepository.findByUsername(username);
            if(user != null) return user;
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorization ->
                authorization
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/design", "/orders", "/").hasRole("USER")
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated());
        httpSecurity.formLogin(login -> login.loginPage("/login"));
        return httpSecurity.build();
    }
}
