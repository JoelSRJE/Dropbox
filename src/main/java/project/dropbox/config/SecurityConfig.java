package project.dropbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.dropbox.repositories.user.UserRepository;
import project.dropbox.utils.AuthenticationFilter;
import project.dropbox.utils.JWTService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JWTService jwtService,
            UserRepository userRepository
    ) {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth
                            // Ska bara finnas dessa i slutet.
                            .requestMatchers("/user/register").permitAll()
                            .requestMatchers("/user/login").permitAll()
                            // För att testa att det går igenom.
                            .requestMatchers("/file/create").permitAll()
                            .requestMatchers("/file/files").permitAll()
                            .requestMatchers("/file/delete/**").permitAll()
                            .requestMatchers("/file/folder/**").permitAll()
                            .requestMatchers("/file/update/**").permitAll()

                            .anyRequest().authenticated();
                })
                .addFilterBefore(
                        new AuthenticationFilter(jwtService, userRepository),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}