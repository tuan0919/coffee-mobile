package nlu.hcmuaf.android_coffee_app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

  /**
   * takes a JWT token as input and extracts the subject
   *
   * @param token
   * @return username
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * extracts the expiration date from the JWT token’s claims
   *
   * @param token
   * @return expiration date
   */
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * used to extract a specific claim from the JWT token’s claims
   *
   * @param token
   * @param claimsResolver
   * @param <T>
   * @return extracted claim
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * parses the JWT token and extracts all of its claims
   *
   * @param token
   * @return Claims
   */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * checks whether a JWT token has expired by comparing the token’s expiration date
   *
   * @param token
   * @return true | false
   */
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * It first extracts the username from the token and then checks whether it matches the username
   * of the provided `UserDetails` object. Additionally, it verifies whether the token has expired.
   *
   * @param token
   * @param userDetails
   * @return true | false
   */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * used to generate a JWT token. It takes a username as input, creates a set of claims
   *
   * @param userDetails
   * @return token
   */
  public String generateToken(CustomUserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      CustomUserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails);
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      CustomUserDetails userCustomDetails
  ) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userCustomDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 86400000))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * used to obtain the signing key for JWT token creation and validation.
   *
   * @return
   */
  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
