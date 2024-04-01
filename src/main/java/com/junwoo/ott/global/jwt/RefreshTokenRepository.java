package com.junwoo.ott.global.jwt;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RefreshTokenRepository {

  private static final String TOKEN_PREFIX = "token_";
  private final RedisTemplate<String, String> redisTemplate;
  @Resource(name = "redisTemplate")
  private ValueOperations<String, String> valueOperations;

  public void delete(String username) {
    redisTemplate.delete(
        TOKEN_PREFIX + username);
  }

  public void save(String username, String refreshToken) {
    valueOperations.set(TOKEN_PREFIX + username, refreshToken);
    redisTemplate.expire(TOKEN_PREFIX + username, 7L, TimeUnit.DAYS);
  }

  public boolean existsByKey(String key) {
    return Boolean.TRUE.equals(valueOperations.getOperations().hasKey(TOKEN_PREFIX + key));
  }

}
