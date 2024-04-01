package com.junwoo.ott.global.jwt;

import static com.junwoo.ott.global.jwt.TokenState.EXPIRED;
import static com.junwoo.ott.global.jwt.TokenState.INVALID;
import static com.junwoo.ott.global.jwt.TokenState.VALID;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.admin.repository.AdminRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@RequiredArgsConstructor
@Component
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  private final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
  private final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;
  @Value("${admin.prefix}")
  private String adminPrefix;
  @Value("${jwt.secret.key}")
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final UserRepository userRepository;
  private final AdminRepository adminRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public String createAccessAndRefreshToken(String username) {

    User user = null;
    if (username.startsWith(adminPrefix)) {
      Admin admin = adminRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException("존재하지 않는 관리자입니다."));
      user = new User(admin);
    } else {
      user = userRepository.findByUsername(username)
          .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));

    }

    String accessToken = createAccessToken(user);
    createRefreshToken(user);
    return accessToken;
  }

  private String createAccessToken(User user) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getUserId())
            .claim("authorityType", user.getAuthorityType())
            .claim("membershipType", user.getMembershipType())
            .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  private void createRefreshToken(User user) {
    Date date = new Date();

    String refreshToken = BEARER_PREFIX +
        Jwts.builder()
            .setSubject(user.getUsername())
            .claim("userId", user.getUserId())
            .claim("authorityType", user.getAuthorityType())
            .claim("membershipType", user.getMembershipType())
            .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
            .setIssuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
    refreshTokenRepository.save(user.getUsername(), refreshToken);
  }

  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length());
    }
    return null;
  }

  public TokenState validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return VALID;
    } catch (SecurityException | MalformedJwtException | SignatureException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.");
      return EXPIRED;
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return INVALID;
  }

  public Claims getExpiredTokenClaims(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return null;
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String regenerateAccessToken(
      Long userId,
      String username,
      AuthorityType authorityType,
      MembershipType membershipType
  ) {
    User user = new User(userId, username, authorityType, membershipType);
    return createAccessToken(user);
  }

}
