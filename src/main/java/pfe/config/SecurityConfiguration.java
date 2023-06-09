package pfe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static pfe.role.Permission.*;
import static pfe.role.Role.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final LogoutHandler logoutHandler;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors()
            .and()
            .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(
                "/api/v1/auth/**",
                "/api/v1/forgot/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html"
        )
          .permitAll()
        .requestMatchers("api/v1/shared/**").hasAnyRole(ADMIN.name(), AGENCE.name(), ASSURE.name(),EXPERT.name())
        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), AGENCE.name())
        .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), AGENCE_READ.name())
        .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), AGENCE_CREATE.name())
        .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), AGENCE_UPDATE.name())
        .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), AGENCE_DELETE.name())
        .requestMatchers(GET, "/api/v1/user/**").hasAuthority(ASSURE_READ.name())
        .requestMatchers(POST, "/api/v1/user/**").hasAuthority(ASSURE_CREATE.name())
        .requestMatchers(PUT, "/api/v1/user/**").hasAuthority(ASSURE_UPDATE.name())
        .requestMatchers(DELETE, "/api/v1/user/**").hasAuthority(ASSURE_DELETE.name())


       /* .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())

        .requestMatchers(GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
        .requestMatchers(POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
        .requestMatchers(PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
        .requestMatchers(DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())*/


        .anyRequest()
          .authenticated()
        .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .logout()
        .logoutUrl("/api/v1/auth/logout")
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
    ;

    return http.build();
  }
}
