package nlu.hcmuaf.android_coffee_app.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_coffee_app.entities.Users;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import nlu.hcmuaf.android_coffee_app.exceptions.UserInfoException;
import nlu.hcmuaf.android_coffee_app.repositories.UserRepository;
import nlu.hcmuaf.android_coffee_app.service.impl.UserDetailsServiceImpl;
import nlu.hcmuaf.android_coffee_app.service.templates.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends HttpFilter {

  private final JwtService jwtService;
  private final UserDetailsServiceImpl userDetailsService;
  private static final Logger logger = (Logger) LoggerFactory.getLogger(
          JwtAuthFilter.class);
  @Qualifier("handlerExceptionResolver")
  private final HandlerExceptionResolver resolver;

  @Override
  protected void doFilter(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    logger.info("JwtAuthFilter is applied on URI: " + request.getRequestURI());

    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);
      }
      else {
        throw new CustomException("You need to have valid token to access this url.", HttpStatus.UNAUTHORIZED.value());
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          logger.info("JwtAuthFilter: set Authentication context for user: " + username);
          var user = ((CustomUserDetails) userDetails).getUser();
          request.setAttribute("user", user);
        } else if (jwtService.isTokenExpired(token))
          throw new CustomException("Your account was logged out or your token was expired, please re-login.",
                  HttpStatus.UNAUTHORIZED.value());
      }
      request.setAttribute("username", username);
      filterChain.doFilter(request, response);
    } catch (CustomException e) {
      resolver.resolveException(request, response, null, e);
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
}
