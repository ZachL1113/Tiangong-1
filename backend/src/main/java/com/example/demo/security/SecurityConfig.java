package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and().csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/index.html","/frontpage.html","/login.html","/css/**","/js/**","/image/**","/players/**","/games/**","/leaderboard**","/scores/**","/history/**","/state/**","/profile/**","/ranking/**","/huarongdao/**").permitAll()
                .anyRequest().authenticated())
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                .defaultSuccessUrl("/frontpage.html", true));
        return http.build();
    }
}
