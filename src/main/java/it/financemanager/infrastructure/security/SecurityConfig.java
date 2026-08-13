package it.financemanager.infrastructure.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.financemanager.application.port.out.UserPort;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {
    @Bean
    UserDetailsService userDetailsService(UserPort users) {
        return email
            -> users.findByEmail(email)
                   .map(u -> User.withUsername(u.email()).password(u.passwordHash()).roles(u.role()).build())
                   .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain chain(HttpSecurity h, JwtAuthenticationFilter f, ObjectMapper m) throws Exception {
        h.csrf(x -> x.disable())
            .cors(x -> {})
            .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(x
                -> x.requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/actuator/health/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
            .exceptionHandling(x
                -> x.authenticationEntryPoint(
                        (request, response, exception)
                            -> writeProblem(response, m, HttpStatus.UNAUTHORIZED, "Authentication is required"))
                    .accessDeniedHandler(
                        (request, response,
                            exception) -> writeProblem(response, m, HttpStatus.FORBIDDEN, "Access is denied")))
            .addFilterBefore(f, UsernamePasswordAuthenticationFilter.class);
        return h.build();
    }

    private static void writeProblem(jakarta.servlet.http.HttpServletResponse response, ObjectMapper mapper,
        HttpStatus status, String detail) throws java.io.IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), ProblemDetail.forStatusAndDetail(status, detail));
    }
}
