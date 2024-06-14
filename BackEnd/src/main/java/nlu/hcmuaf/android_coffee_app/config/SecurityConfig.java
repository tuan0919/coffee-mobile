package nlu.hcmuaf.android_coffee_app.config;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.enums.ERole;
import nlu.hcmuaf.android_coffee_app.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth // ủy quyền
            .requestMatchers("/api/user/login").permitAll() // ai cũng có thể truy cập
            .requestMatchers("/api/v1/user/**").permitAll() // ai cũng có thể truy cập
            .requestMatchers("/api/v1/user/test")
            .hasAnyAuthority(ERole.USER.name(), ERole.ADMIN.name()) // Chỉ user
            .requestMatchers("/api/v1/product/fish")
            .hasAnyAuthority(ERole.ADMIN.name())// Chỉ ADMIN
            .requestMatchers("/api/v1/product/sion")
            .hasAnyAuthority(ERole.MANAGER.name())// Chỉ MANAGER
            .anyRequest()
            .authenticated())
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)) // quản lý các phiên
        .authenticationProvider(daoAuthenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationProvider(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

}
