package com.toptier.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) -> auth
                            .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico", "/bootstrap-5.2.3/**", "/files/**", "/symbol_toptier.ico", "/.well-known/**").permitAll() // static
                            .requestMatchers("/","/introduce/**", "/menu/**", "/shop/**", "/franchies/**", "/notice/**", "/event/**").permitAll() // front view
                            .requestMatchers("/api/user/**").permitAll() // api
                            .requestMatchers("/manage/login").permitAll() // 관리자 페이지에 관한 권한 설정
                            .requestMatchers("/manage/user/**").hasRole("ADMIN") // 관리자 페이지에 관한 권한 설정
                            .requestMatchers("/manage/**").hasAnyRole("USER", "ADMIN") // 관리 페이지에 관한 권한 설정
                            .anyRequest().authenticated()
                    )
                    .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                    .formLogin(formLogin -> formLogin
                            .loginPage("/manage/login")
                            .usernameParameter("userId") // HTML form의 name 속성과 일치시킴
                            .defaultSuccessUrl("/manage/main")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutRequestMatcher(request -> request.getRequestURI().equals("/manage/logout"))
                            .logoutSuccessUrl("/manage/login")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID")
                    );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
