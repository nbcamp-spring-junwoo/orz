package com.junwoo.ott.global.jwt.filter;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.TokenNotValidException;
import com.junwoo.ott.global.jwt.JwtUtil;
import com.junwoo.ott.global.jwt.RefreshTokenRepository;
import com.junwoo.ott.global.jwt.TokenState;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtUtil jwtUtil;

  public JwtAuthorizationFilter(
      final JwtUtil jwtUtil,
      final RefreshTokenRepository refreshTokenRepository
  ) {
    this.jwtUtil = jwtUtil;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest req, final HttpServletResponse res,
      final FilterChain filterChain
  ) throws ServletException, IOException {

    String tokenValue = jwtUtil.getJwtFromHeader(req);

    if (StringUtils.hasText(tokenValue)) {
      TokenState tokenState = jwtUtil.validateToken(tokenValue);

      if (tokenState == TokenState.INVALID) {
        log.error("Token Error");
        throw new TokenNotValidException("올바르지 않는 토큰입니다.");
      } else if (tokenState == TokenState.EXPIRED) {
        try {
          Claims info = jwtUtil.getExpiredTokenClaims(tokenValue);

          if (refreshTokenRepository.existsByKey(info.getSubject())) {
            Long userId = info.get("userId", Long.class);
            String username = info.getSubject();
            AuthorityType authorityType = AuthorityType.fromString(
                (String) info.get("authorityType"));
            MembershipType membershipType = MembershipType.fromString(
                (String) info.get("membershipType"));

            String newToken = jwtUtil.regenerateAccessToken(userId, username, authorityType,
                membershipType);
            res.addHeader(JwtUtil.AUTHORIZATION_HEADER, newToken);
            res.setStatus(HttpServletResponse.SC_OK);

            log.info("새로운 Acces Token이 발급되었습니다.");
          } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("Access Token과 Refresh Token이 모두 만료되었습니다.");
            refreshTokenRepository.delete(info.getSubject());
          }
        } catch (Exception e) {
          log.error(e.getMessage());
        }
      } else {
        Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
        Long userId = info.get("userId", Long.class);
        String username = info.getSubject();
        AuthorityType authorityType = AuthorityType.fromString((String) info.get("authorityType"));
        MembershipType membershipType = MembershipType.fromString(
            (String) info.get("membershipType"));

        setAuthentication(userId, username, authorityType, membershipType);
      }
    }

    filterChain.doFilter(req, res);
  }

  public void setAuthentication(
      final Long userId,
      final String username,
      final AuthorityType authorityType,
      final MembershipType membershipType
  ) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(userId, username, authorityType,
        membershipType);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  private Authentication createAuthentication(
      final Long userId,
      final String username,
      final AuthorityType authorityType,
      final MembershipType membershipType
  ) {

    User user = new User(userId, username, authorityType, membershipType);
    UserDetails userDetails = new UserDetailsImpl(user);

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

}
