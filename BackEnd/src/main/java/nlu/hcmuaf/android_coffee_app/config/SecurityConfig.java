package nlu.hcmuaf.android_coffee_app.config;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.ERole;
import nlu.hcmuaf.android_coffee_app.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;
  private final UserDetailsService userDetailsService;

  @Bean
  @Order(2)  // Đảm bảo rằng filter chain cho Private APIs chạy sau
  public SecurityFilterChain privateFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .securityMatcher("/api/v2/**")
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("/api/v2/gio-hang/**")
                      .hasAnyAuthority(ERole.ROLE_USER.name(), ERole.ROLE_ADMIN.name())
                  .requestMatchers(HttpMethod.POST, "/api/v2/dat-hang")
                      .hasAnyAuthority(ERole.ROLE_USER.name(), ERole.ROLE_ADMIN.name())
                  .requestMatchers(HttpMethod.GET,"/api/v2/don-hang/**")
                      .hasAnyAuthority(ERole.ROLE_USER.name(), ERole.ROLE_ADMIN.name())
                  .requestMatchers("/api/v2/yeu-thich/**")
                      .hasAnyAuthority(ERole.ROLE_USER.name(), ERole.ROLE_ADMIN.name())
                  .anyRequest()
                      .denyAll();
            })
            .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(daoAuthenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  @Order(1)  // Đảm bảo rằng filter chain cho Public APIs chạy trước
  public SecurityFilterChain publicFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .securityMatcher("/api/v1/**")
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("/api/v1/san-pham/**")
                        .permitAll()
                      .requestMatchers("/api/v1/dang-nhap")
                        .permitAll()
                      .requestMatchers("/api/v1/dang-ky")
                        .permitAll()
                      .requestMatchers("/api/v1/xac-minh")
                        .permitAll()
                      .anyRequest()
                        .denyAll();  // Chỉ cho phép các request đã định nghĩa ở trên
            });
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationProvider(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

}
