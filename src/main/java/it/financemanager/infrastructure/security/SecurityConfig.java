package it.financemanager.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.financemanager.role.BaseRole;
import it.financemanager.role.Role;
import it.financemanager.role.RoleStore;
import it.financemanager.user.UserStore;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(UserStore repository,
                                          RoleStore roleRepository) {
        Role userRole = roleRepository.findByCode(BaseRole.ROLE_USER.getRole())
                .orElse(new Role(BaseRole.ROLE_USER.getRole(), ""));
        return email
                -> repository.findByEmailIgnoreCase(email)
                .map(user
                        -> User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .roles(userRole.getCode())
                        .build())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Invalid credentials"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager
    authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService users,
                                                     PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(users);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtAuthenticationFilter filter,
                                            ObjectMapper mapper)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                })
                .sessionManagement(
                        session
                                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        auth
                                -> auth.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**",
                                        "/swagger-ui/**", "/swagger-ui.html")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(
                        errors
                                -> errors
                                .authenticationEntryPoint(
                                        (request, response, ex)
                                                -> writeProblem(response, mapper, 401,
                                                "Unauthorized",
                                                "Authentication is required"))
                                .accessDeniedHandler(
                                        (request, response, ex)
                                                -> writeProblem(response, mapper, 403, "Forbidden",
                                                "Access is denied")))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private static void writeProblem(HttpServletResponse response,
                                     ObjectMapper mapper, int status,
                                     String title, String detail)
            throws java.io.IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(HttpStatus.valueOf(status), detail);
        problem.setTitle(title);
        mapper.writeValue(response.getOutputStream(), problem);
    }
}
